package persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Disciplina;

public class DisciplinaDAO implements ICRUDDAO<Disciplina>, IConnectionDB<DisciplinaDAO> {

    private final Context context;
    private GenericDAO genericDAO;
    private SQLiteDatabase db;

    public DisciplinaDAO(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(Disciplina disciplina) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cdDisciplina", disciplina.getCdDisciplina());
        contentValues.put("nomeDisciplina", disciplina.getNomeDisciplina());

        return contentValues;
    }

    @Override
    public DisciplinaDAO open() {
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
    public void create(Disciplina disciplina) {
        db.insert("Disciplina", null,getContentValues(disciplina));
    }

    @Override
    public void update(Disciplina disciplina) {
        db.update("Disciplina", getContentValues(disciplina), "cdDisciplina = " + disciplina.getCdDisciplina(), null);
    }

    @Override
    public void delete(Disciplina disciplina) {
        db.delete("Disciplina", "cdDisciplina = " + disciplina.getCdDisciplina(), null);
    }

    @SuppressLint("Range")
    @Override
    public Disciplina findOne(Disciplina disciplina) {
        String querySQL = "SELECT " +
                "cdDisciplina, nomeDisciplina " +
                "FROM Disciplina " +
                "WHERE cdDisciplina = " + disciplina.getCdDisciplina();

        Cursor cursor = db.rawQuery(querySQL, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (!cursor.isAfterLast()) {
            disciplina.setCdDisciplina(cursor.getInt(cursor.getColumnIndex("cdDisciplina")));
            disciplina.setNomeDisciplina(cursor.getString(cursor.getColumnIndex("nomeDisciplina")));
        }

        cursor.close();

        return disciplina;
    }

    @SuppressLint("Range")
    @Override
    public List<Disciplina> findAll() {
        List<Disciplina> disciplinas = new ArrayList<>();
        String querySQL = "SELECT * FROM Disciplina";
        Cursor cursor = db.rawQuery(querySQL, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {
            Disciplina disciplina = new Disciplina();
            disciplina.setCdDisciplina(cursor.getInt(cursor.getColumnIndex("cdDisciplina")));
            disciplina.setNomeDisciplina(cursor.getString(cursor.getColumnIndex("nomeDisciplina")));
            disciplinas.add(disciplina);
            cursor.moveToNext();
        }

        cursor.close();
        return disciplinas;
    }
}