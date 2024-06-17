package controller;

import java.util.List;

import model.GrupoEstudoOnline;
import persistence.GrupoEstudoOnlineDAO;

public class GrupoEstudoOnlineController implements IController<GrupoEstudoOnline> {
    private final GrupoEstudoOnlineDAO grupoEstudoDAO;

    public GrupoEstudoOnlineController(GrupoEstudoOnlineDAO grupoEstudoDAO) {
        this.grupoEstudoDAO = grupoEstudoDAO;
    }

    @Override
    public void create(GrupoEstudoOnline grupoEstudo) {
        abrirConexao();
        grupoEstudoDAO.create(grupoEstudo);
        grupoEstudoDAO.close();
    }

    @Override
    public void update(GrupoEstudoOnline grupoEstudo) {
        abrirConexao();

        grupoEstudoDAO.update(grupoEstudo);
        grupoEstudoDAO.close();
    }

    @Override
    public void delete(GrupoEstudoOnline grupoEstudo) {
        abrirConexao();

        grupoEstudoDAO.delete(grupoEstudo);
        grupoEstudoDAO.close();
    }

    @Override
    public GrupoEstudoOnline findOne(GrupoEstudoOnline grupoEstudo) {
        abrirConexao();

        GrupoEstudoOnline grupoEstudoFound = grupoEstudoDAO.findOne(grupoEstudo);
        grupoEstudoDAO.close();

        return grupoEstudoFound;
    }

    @Override
    public List<GrupoEstudoOnline> findAll() {
        abrirConexao();

        List<GrupoEstudoOnline> grupoEstudos = grupoEstudoDAO.findAll();
        grupoEstudoDAO.close();

        return grupoEstudos;
    }

    private void abrirConexao() {
        if (grupoEstudoDAO.open() == null) {
            grupoEstudoDAO.open();
        }
    }
}