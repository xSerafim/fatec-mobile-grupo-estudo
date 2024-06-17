package persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import model.GrupoEstudoAluno;

public class GrupoEstudoAlunoDAO implements IConnectionDB<GrupoEstudoAlunoDAO> {
    
    private final Context context;
    private GenericDAO genericDAO;
    private SQLiteDatabase db;

    public GrupoEstudoAlunoDAO(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(GrupoEstudoAluno grupoEstudo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("cdGrupo", grupoEstudo.getCdGrupo());
        contentValues.put("cdAluno", grupoEstudo.getCdAluno());

        return contentValues;
    }

    @Override
    public GrupoEstudoAlunoDAO open () {
        genericDAO = new GenericDAO(context);
        db = genericDAO.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");

        return this;
    }

    @Override
    public void close () {
        genericDAO.close();
    }

    public void create(int cdGrupo, int cdAluno){
        GrupoEstudoAluno grupoEstudo = new GrupoEstudoAluno(cdGrupo, cdAluno);
        db.insert("GrupoEstudoAluno", null, getContentValues(grupoEstudo));
    }

    public void delete (int cdGrupo, int cdAluno){
        db.delete("GrupoEstudoAluno",
                "cdAluno = " + cdAluno +
                        " AND cdGrupo = " + cdGrupo,
                null);
    }
}
