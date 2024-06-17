package controller;

import java.sql.SQLException;

import model.GrupoEstudoAluno;
import persistence.GrupoEstudoAlunoDAO;

public class GrupoEstudoAlunoController {

    private final GrupoEstudoAlunoDAO grupoEstudoAlunoDAO;

    public GrupoEstudoAlunoController(GrupoEstudoAlunoDAO grupoEstudoAlunoDAO) {
        this.grupoEstudoAlunoDAO = grupoEstudoAlunoDAO;
    }

    public void create(int cdGrupo, int cdAluno) throws SQLException {
        abrirConexao();
        grupoEstudoAlunoDAO.create(cdGrupo, cdAluno);
        grupoEstudoAlunoDAO.close();
    }

    public void delete(int cdGrupo, int cdAluno) {
        abrirConexao();

        grupoEstudoAlunoDAO.delete(cdGrupo, cdAluno);
        grupoEstudoAlunoDAO.close();
    }
    private void abrirConexao() {
        if (grupoEstudoAlunoDAO.open() == null) {
            grupoEstudoAlunoDAO.open();
        }
    }
}
