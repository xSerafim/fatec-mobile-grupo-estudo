package controller;

import java.sql.SQLException;
import java.util.List;

import model.Disciplina;
import persistence.DisciplinaDAO;

public class DisciplinaController implements IController<Disciplina> {
    private final DisciplinaDAO disciplinaDAO;

    public DisciplinaController(DisciplinaDAO disciplinaDAO) {
        this.disciplinaDAO = disciplinaDAO;
    }

    @Override
    public void create(Disciplina disciplina) {
        abrirConexao();
        disciplinaDAO.create(disciplina);
        disciplinaDAO.close();
    }

    @Override
    public void update(Disciplina disciplina) {
        abrirConexao();

        disciplinaDAO.update(disciplina);
        disciplinaDAO.close();
    }

    @Override
    public void delete(Disciplina disciplina) {
        abrirConexao();

        disciplinaDAO.delete(disciplina);
        disciplinaDAO.close();
    }

    @Override
    public Disciplina findOne(Disciplina disciplina) {
        abrirConexao();

        Disciplina disciplinaFound = disciplinaDAO.findOne(disciplina);
        disciplinaDAO.close();

        return disciplinaFound;
    }

    @Override
    public List<Disciplina> findAll() {
        abrirConexao();

        List<Disciplina> disciplinas = disciplinaDAO.findAll();
        disciplinaDAO.close();

        return disciplinas;
    }

    private void abrirConexao() {
        if (disciplinaDAO.open() == null) {
            disciplinaDAO.open();
        }
    }
}
