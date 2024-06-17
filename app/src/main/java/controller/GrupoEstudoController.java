package controller;

import java.util.List;

import model.GrupoEstudo;
import persistence.GrupoEstudoDAO;

public class GrupoEstudoController  implements IController<GrupoEstudo> {
    private final GrupoEstudoDAO grupoEstudoDAO;

    public GrupoEstudoController(GrupoEstudoDAO grupoEstudoDAO) {
        this.grupoEstudoDAO = grupoEstudoDAO;
    }

    @Override
    public void create(GrupoEstudo grupoEstudo) {
        abrirConexao();
        grupoEstudoDAO.create(grupoEstudo);
        grupoEstudoDAO.close();
    }

    @Override
    public void update(GrupoEstudo grupoEstudo) {
        abrirConexao();

        grupoEstudoDAO.update(grupoEstudo);
        grupoEstudoDAO.close();
    }

    @Override
    public void delete(GrupoEstudo grupoEstudo) {
        abrirConexao();

        grupoEstudoDAO.delete(grupoEstudo);
        grupoEstudoDAO.close();
    }

    @Override
    public GrupoEstudo findOne(GrupoEstudo grupoEstudo) {
        abrirConexao();

        GrupoEstudo grupoEstudoFound = grupoEstudoDAO.findOne(grupoEstudo);
        grupoEstudoDAO.close();

        return grupoEstudoFound;
    }

    @Override
    public List<GrupoEstudo> findAll() {
        abrirConexao();

        List<GrupoEstudo> grupoEstudos = grupoEstudoDAO.findAll();
        grupoEstudoDAO.close();

        return grupoEstudos;
    }

    private void abrirConexao() {
        if (grupoEstudoDAO.open() == null) {
            grupoEstudoDAO.open();
        }
    }
}