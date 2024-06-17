package persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "grupo_estudo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_ALUNO = "CREATE TABLE Aluno (" +
            "RA INTEGER PRIMARY KEY NOT NULL, " +
            "nome VARCHAR(100) NOT NULL, " +
            "idade INTEGER(10) NOT NULL)";

    private static final String CREATE_TABLE_DISCIPLINA = "CREATE TABLE Disciplina (" +
            "cdDisciplina INTEGER(10) NOT NULL PRIMARY KEY, " +
            "nomeDisciplina VARCHAR(100) NOT NULL)";

    private static final String CREATE_TABLE_GRUPO_ESTUDO = "CREATE TABLE GrupoEstudo (" +
            "cdGrupo INTEGER(10) NOT NULL PRIMARY KEY, " +
            "nomeGrupo VARCHAR(100) NOT NULL, " +
            "cdDisciplina INTEGER(10), " +
            "dataEncontro VARCHAR(10), " +
            "FOREIGN KEY (cdDisciplina) REFERENCES Disciplina(cdDisciplina))";

    private static final String CREATE_TABLE_GRUPO_ESTUDO_ONLINE = "CREATE TABLE GrupoEstudoOnline (" +
            "link VARCHAR(200) NOT NULL, " +
            "cdGrupo INTEGER(10), " +
            "FOREIGN KEY (cdGrupo) REFERENCES GrupoEstudo(cdGrupo) ON DELETE CASCADE)";

    private static final String CREATE_TABLE_GRUPO_ESTUDO_PRESENCIAL = "CREATE TABLE GrupoEstudoPresencial (" +
            "sala INTEGER(10), " +
            "cdGrupo INTEGER(10), " +
            "FOREIGN KEY (cdGrupo) REFERENCES GrupoEstudo(cdGrupo) ON DELETE CASCADE)";

    private static final String CREATE_TABLE_GRUPO_ESTUDO_ALUNO = "CREATE TABLE GrupoEstudoAluno (" +
            "cdAluno INTEGER(10), " +
            "cdGrupo INTEGER(10), " +
            "PRIMARY KEY (cdAluno, cdGrupo), " +
            "FOREIGN KEY (cdAluno) REFERENCES Aluno(RA) ON DELETE CASCADE, " +
            "FOREIGN KEY (cdGrupo) REFERENCES GrupoEstudo(cdGrupo) ON DELETE CASCADE)";
    public GenericDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALUNO);
        db.execSQL(CREATE_TABLE_DISCIPLINA);
        db.execSQL(CREATE_TABLE_GRUPO_ESTUDO);
        db.execSQL(CREATE_TABLE_GRUPO_ESTUDO_ONLINE);
        db.execSQL(CREATE_TABLE_GRUPO_ESTUDO_PRESENCIAL);
        db.execSQL(CREATE_TABLE_GRUPO_ESTUDO_ALUNO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS Aluno");
            db.execSQL("DROP TABLE IF EXISTS Disciplina");
            db.execSQL("DROP TABLE IF EXISTS GrupoEstudo");
            db.execSQL("DROP TABLE IF EXISTS GrupoEstudoOnline");
            db.execSQL("DROP TABLE IF EXISTS GrupoEstudoPresencial");
            db.execSQL("DROP TABLE IF EXISTS GrupoEstudoAluno");
            onCreate(db);
        }
    }
}
