package persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Disciplina;
import model.GrupoEstudoPresencial;

public class GrupoEstudoPresencialDAO implements ICRUDDAO<GrupoEstudoPresencial>, IConnectionDB<GrupoEstudoPresencialDAO> {

    private final Context context;
    private GenericDAO genericDAO;
    private SQLiteDatabase db;

    public GrupoEstudoPresencialDAO(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(GrupoEstudoPresencial grupoEstudo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("cdGrupo", grupoEstudo.getCdGrupo());
        contentValues.put("sala", grupoEstudo.getSala());

        return contentValues;
    }

    @Override
    public GrupoEstudoPresencialDAO open () {
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
    public void create(GrupoEstudoPresencial grupoEstudo){
        //Inserindo o grupo de estudo
        GrupoEstudoDAO grupoEstudoDAO = new GrupoEstudoDAO(context);
        grupoEstudoDAO.open();
        grupoEstudoDAO.create(grupoEstudo);

        db.insert("GrupoEstudoPresencial", null, getContentValues(grupoEstudo));
    }

    @Override
    public void update (GrupoEstudoPresencial grupoEstudo){
        //Atualizando o grupo de estudo
        GrupoEstudoDAO grupoEstudoDAO = new GrupoEstudoDAO(context);
        grupoEstudoDAO.open();
        grupoEstudoDAO.update(grupoEstudo);

        db.update("GrupoEstudoPresencial", getContentValues(grupoEstudo), "cdGrupo = " + grupoEstudo.getCdGrupo(), null);
    }

    @Override
    public void delete (GrupoEstudoPresencial grupoEstudo){
        //Deletando o grupo de estudo
        GrupoEstudoDAO grupoEstudoDAO = new GrupoEstudoDAO(context);
        grupoEstudoDAO.open();
        grupoEstudoDAO.delete(grupoEstudo);
    }

    @SuppressLint("Range")
    @Override
    public GrupoEstudoPresencial findOne (GrupoEstudoPresencial grupoEstudo){
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(context);
        disciplinaDAO.open();
        Disciplina disciplina = new Disciplina();

        String querySQLGrupoEstudo = "SELECT " +
                "gp.cdGrupo, gp.sala, g.nomeGrupo, g.dataEncontro, g.cdDisciplina " +
                "FROM GrupoEstudoPresencial as gp " +
                "INNER JOIN GrupoEstudo as g ON gp.cdGrupo = g.cdGrupo " +
                "WHERE gp.cdGrupo = " + grupoEstudo.getCdGrupo();


        Cursor cursor = db.rawQuery(querySQLGrupoEstudo, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (!cursor.isAfterLast()) {
            grupoEstudo.setCdGrupo(cursor.getInt(cursor.getColumnIndex("cdGrupo")));
            grupoEstudo.setNomeGrupo(cursor.getString(cursor.getColumnIndex("nomeGrupo")));
            grupoEstudo.setDataEncontro(cursor.getString(cursor.getColumnIndex("dataEncontro")));
            grupoEstudo.setSala(cursor.getInt(cursor.getColumnIndex("sala")));

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
    public List<GrupoEstudoPresencial> findAll () {
        return new ArrayList<>();
    }
}
