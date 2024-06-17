package persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Aluno;
import model.Disciplina;
import model.GrupoEstudo;
import model.GrupoEstudoOnline;
import model.GrupoEstudoPresencial;

public class GrupoEstudoDAO implements ICRUDDAO<GrupoEstudo>, IConnectionDB<GrupoEstudoDAO> {

    private static final String QUERY_GRUPO_ESTUDO = "SELECT GE.cdGrupo, GE.dataEncontro, GE.nomeGrupo, GEO.link, GEP.sala, D.nomeDisciplina, D.cdDisciplina " +
            "FROM GrupoEstudo as GE " +
            "LEFT JOIN GrupoEstudoOnline as GEO ON GE.cdGrupo = GEO.cdGrupo " +
            "LEFT JOIN GrupoEstudoPresencial as GEP ON GE.cdGrupo = GEP.cdGrupo " +
            "LEFT JOIN Disciplina as D on GE.cdDisciplina = D.cdDisciplina";
    private final Context context;
    private GenericDAO genericDAO;
    private SQLiteDatabase db;

    public GrupoEstudoDAO(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(GrupoEstudo grupoEstudo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("cdGrupo", grupoEstudo.getCdGrupo());
        contentValues.put("nomeGrupo", grupoEstudo.getNomeGrupo());
        contentValues.put("dataEncontro", grupoEstudo.getDataEncontro());
        contentValues.put("cdDisciplina", grupoEstudo.getDisciplina().getCdDisciplina());

        return contentValues;
    }

    @Override
    public GrupoEstudoDAO open () {
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
    public void create(GrupoEstudo grupoEstudo){
        db.insert("GrupoEstudo", null, getContentValues(grupoEstudo));
    }

    @Override
    public void update (GrupoEstudo grupoEstudo){
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(context);
        disciplinaDAO.open();
        disciplinaDAO.update(grupoEstudo.getDisciplina());
        db.update("GrupoEstudo", getContentValues(grupoEstudo), "cdGrupo = " + grupoEstudo.getCdGrupo(), null);
    }

    @Override
    public void delete (GrupoEstudo grupoEstudo){
        db.delete("GrupoEstudo", "cdGrupo = " + grupoEstudo.getCdGrupo(), null);
    }

    @SuppressLint("Range")
    @Override
    public GrupoEstudo findOne (GrupoEstudo grupoEstudo){

        return grupoEstudo;
    }

    @SuppressLint("Range")
    @Override
    public List<GrupoEstudo> findAll () {
        List<GrupoEstudo> grupoEstudos = new ArrayList<>();

        Cursor cursor = db.rawQuery(QUERY_GRUPO_ESTUDO, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {
            Disciplina disciplina = new Disciplina();
            GrupoEstudo grupoEstudo;

            if(cursor.getString(cursor.getColumnIndex("link")) != null){
                grupoEstudo = new GrupoEstudoOnline();
                ((GrupoEstudoOnline) grupoEstudo).setLink(cursor.getString(cursor.getColumnIndex("link")));
            } else {
                grupoEstudo = new GrupoEstudoPresencial();
                ((GrupoEstudoPresencial) grupoEstudo).setSala(cursor.getInt(cursor.getColumnIndex("sala")));
            }

            grupoEstudo.setCdGrupo(cursor.getInt(cursor.getColumnIndex("cdGrupo")));
            grupoEstudo.setNomeGrupo(cursor.getString(cursor.getColumnIndex("nomeGrupo")));
            grupoEstudo.setDataEncontro(cursor.getString(cursor.getColumnIndex("dataEncontro")));

            //Recuperando e setando a disciplina
            disciplina.setCdDisciplina(cursor.getInt(cursor.getColumnIndex("cdDisciplina")));
            disciplina.setNomeDisciplina(cursor.getString(cursor.getColumnIndex("nomeDisciplina")));
            grupoEstudo.setDisciplina(disciplina);

            //Recuperando e setando os alunos
            List<Aluno> alunos = getAlunos(grupoEstudo.getCdGrupo());
            grupoEstudo.setAlunos(alunos);

            grupoEstudos.add(grupoEstudo);
            cursor.moveToNext();
        }

        cursor.close();

        return grupoEstudos;
    }

    @SuppressLint("Range")
    private List<Aluno> getAlunos(int cdGrupo){
        String querySQL = "SELECT A.RA, A.nome, A.idade " +
                "FROM GrupoEstudoAluno as GE " +
                "INNER JOIN Aluno as A ON GE.cdAluno = A.RA " +
                "WHERE GE.cdGrupo = " + cdGrupo +
                " ORDER BY A.nome";

        Cursor cursor = db.rawQuery(querySQL, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        List<Aluno> alunos = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {
            Aluno aluno = new Aluno();
            aluno.setRA(cursor.getInt(cursor.getColumnIndex("RA")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setIdade(cursor.getInt(cursor.getColumnIndex("idade")));
            alunos.add(aluno);
            cursor.moveToNext();
        }

        cursor.close();

        return alunos;
    }
}