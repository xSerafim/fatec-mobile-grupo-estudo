package persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Aluno;

public class AlunoDAO implements ICRUDDAO<Aluno>, IConnectionDB<AlunoDAO> {

    private final Context context;
    private GenericDAO genericDAO;
    private SQLiteDatabase db;

    public AlunoDAO(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(Aluno aluno) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("RA", aluno.getRA());
        contentValues.put("nome", aluno.getNome());
        contentValues.put("idade", aluno.getIdade());

        return contentValues;
    }

    @Override
    public AlunoDAO open() {
        genericDAO = new GenericDAO(context);
        db = genericDAO.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");

        return this;
    }

    @Override
    public void close() {
        genericDAO.close();
    }

    @Override
    public void create(Aluno aluno) {
        db.insert("Aluno", null,getContentValues(aluno));
    }

    @Override
    public void update(Aluno aluno) {
        db.update("Aluno", getContentValues(aluno), "RA = " + aluno.getRA(), null);
    }

    @Override
    public void delete(Aluno aluno) {
        db.delete("Aluno", "RA = " + aluno.getRA(), null);
    }

    @SuppressLint("Range")
    @Override
    public Aluno findOne(Aluno aluno) {
        String querySQL = "SELECT " +
                "ra, nome, idade " +
                "FROM Aluno " +
                "WHERE ra = " + aluno.getRA();

        Cursor cursor = db.rawQuery(querySQL, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (!cursor.isAfterLast()) {
            aluno.setRA(cursor.getInt(cursor.getColumnIndex("RA")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setIdade(cursor.getInt(cursor.getColumnIndex("idade")));
        }

        cursor.close();

        return aluno;
    }

    @SuppressLint("Range")
    @Override
    public List<Aluno> findAll() {
        List<Aluno> alunos = new ArrayList<>();
        String querySQL = "SELECT * FROM Aluno";
        Cursor cursor = db.rawQuery(querySQL, null);
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
