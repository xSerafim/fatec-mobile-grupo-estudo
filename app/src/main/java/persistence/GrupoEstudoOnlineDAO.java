package persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Disciplina;
import model.GrupoEstudoOnline;

public class GrupoEstudoOnlineDAO implements ICRUDDAO<GrupoEstudoOnline>, IConnectionDB<GrupoEstudoOnlineDAO> {

    private final Context context;
    private GenericDAO genericDAO;
    private SQLiteDatabase db;

    public GrupoEstudoOnlineDAO(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(GrupoEstudoOnline grupoEstudo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("cdGrupo", grupoEstudo.getCdGrupo());
        contentValues.put("link", grupoEstudo.getLink());

        return contentValues;
    }

    @Override
    public GrupoEstudoOnlineDAO open () {
        genericDAO = new GenericDAO(context);
        db = genericDAO.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");

        return this;
    }

    @Override
    public void close () {
        genericDAO.close();
    }

    @Override
    public void create(GrupoEstudoOnline grupoEstudo){
        //Inserindo o grupo de estudo
        GrupoEstudoDAO grupoEstudoDAO = new GrupoEstudoDAO(context);
        grupoEstudoDAO.open();
        grupoEstudoDAO.create(grupoEstudo);

        db.insert("GrupoEstudoOnline", null, getContentValues(grupoEstudo));
    }

    @Override
    public void update (GrupoEstudoOnline grupoEstudo){
        //Atualizando o grupo de estudo
        GrupoEstudoDAO grupoEstudoDAO = new GrupoEstudoDAO(context);
        grupoEstudoDAO.open();
        grupoEstudoDAO.update(grupoEstudo);

        db.update("GrupoEstudoOnline", getContentValues(grupoEstudo), "cdGrupo = " + grupoEstudo.getCdGrupo(), null);
    }

    @Override
    public void delete (GrupoEstudoOnline grupoEstudo){
        //Deletando o grupo de estudo
        GrupoEstudoDAO grupoEstudoDAO = new GrupoEstudoDAO(context);
        grupoEstudoDAO.open();
        grupoEstudoDAO.delete(grupoEstudo);
    }

    @SuppressLint("Range")
    @Override
    public GrupoEstudoOnline findOne (GrupoEstudoOnline grupoEstudo){
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(context);
        disciplinaDAO.open();
        Disciplina disciplina = new Disciplina();

        String querySQLGrupoEstudo = "SELECT " +
                "ge.cdGrupo, ge.link, g.nomeGrupo, g.dataEncontro, g.cdDisciplina " +
                "FROM GrupoEstudoOnline as ge " +
                "INNER JOIN GrupoEstudo as g ON ge.cdGrupo = g.cdGrupo " +
                "WHERE ge.cdGrupo = " + grupoEstudo.getCdGrupo();


        Cursor cursor = db.rawQuery(querySQLGrupoEstudo, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (!cursor.isAfterLast()) {
            grupoEstudo.setCdGrupo(cursor.getInt(cursor.getColumnIndex("cdGrupo")));
            grupoEstudo.setNomeGrupo(cursor.getString(cursor.getColumnIndex("nomeGrupo")));
            grupoEstudo.setDataEncontro(cursor.getString(cursor.getColumnIndex("dataEncontro")));
            grupoEstudo.setLink(cursor.getString(cursor.getColumnIndex("link")));

            //Recuperando e setando a disciplina
            disciplina.setCdDisciplina(cursor.getInt(cursor.getColumnIndex("cdDisciplina")));
            grupoEstudo.setDisciplina(disciplinaDAO.findOne(disciplina));
        }

        grupoEstudo.setDisciplina(disciplinaDAO.findOne(disciplina));

        cursor.close();

        return grupoEstudo;
    }

    @SuppressLint("Range")
    @Override
    public List<GrupoEstudoOnline> findAll () {
        List<GrupoEstudoOnline> grupoEstudos = new ArrayList<>();
//        String querySQL = "SELECT * FROM GrupoEstudo";
//        Cursor cursor = db.rawQuery(querySQL, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//
//        while (!cursor.isAfterLast()) {
//            GrupoEstudo grupoEstudo = new GrupoEstudo();
//            grupoEstudo.setCdGrupoEstudo(cursor.getInt(cursor.getColumnIndex("cdGrupo")));
//            grupoEstudo.setNomeGrupoEstudo(cursor.getString(cursor.getColumnIndex("nomeGrupoEstudo")));
//            grupoEstudos.add(grupoEstudo);
//            cursor.moveToNext();
//        }
//
//        cursor.close();
        return grupoEstudos;
    }
}