package controller;

import java.sql.SQLException;
import java.util.List;

import model.Aluno;
import persistence.AlunoDAO;

public class AlunoController implements IController<Aluno> {

    private final AlunoDAO alunoDAO;

    public AlunoController(AlunoDAO alunoDAO) {
        this.alunoDAO = alunoDAO;
    }

    @Override
    public void create(Aluno aluno) throws SQLException {
        abrirConexao();
        alunoDAO.create(aluno);
        alunoDAO.close();
    }

    @Override
    public void update(Aluno aluno) {
        abrirConexao();

        alunoDAO.update(aluno);
        alunoDAO.close();
    }

    @Override
    public void delete(Aluno aluno) {
        abrirConexao();

        alunoDAO.delete(aluno);
        alunoDAO.close();
    }

    @Override
    public Aluno findOne(Aluno aluno) {
        abrirConexao();

        Aluno alunoFound = alunoDAO.findOne(aluno);
        alunoDAO.close();

        return alunoFound;
    }

    @Override
    public List<Aluno> findAll() {
        abrirConexao();

        List<Aluno> alunos = alunoDAO.findAll();
        alunoDAO.close();

        return alunos;
    }

    private void abrirConexao() {
        if (alunoDAO.open() == null) {
            alunoDAO.open();
        }
    }
}
