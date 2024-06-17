package controller;

import java.util.List;

import model.GrupoEstudoPresencial;
import persistence.GrupoEstudoPresencialDAO;

public class GrupoEstudoPresencialController implements IController<GrupoEstudoPresencial> {
    private final GrupoEstudoPresencialDAO grupoEstudoDAO;

    public GrupoEstudoPresencialController(GrupoEstudoPresencialDAO grupoEstudoDAO) {
        this.grupoEstudoDAO = grupoEstudoDAO;
    }

    @Override
    public void create(GrupoEstudoPresencial grupoEstudo) {
        abrirConexao();

        grupoEstudoDAO.create(grupoEstudo);
        grupoEstudoDAO.close();
    }

    @Override
    public void update(GrupoEstudoPresencial grupoEstudo) {
        abrirConexao();

        grupoEstudoDAO.update(grupoEstudo);
        grupoEstudoDAO.close();
    }

    @Override
    public void delete(GrupoEstudoPresencial grupoEstudo) {
        abrirConexao();

        grupoEstudoDAO.delete(grupoEstudo);
        grupoEstudoDAO.close();
    }

    @Override
    public GrupoEstudoPresencial findOne(GrupoEstudoPresencial grupoEstudo) {
        abrirConexao();

        GrupoEstudoPresencial grupoEstudoFound = grupoEstudoDAO.findOne(grupoEstudo);
        grupoEstudoDAO.close();

        return grupoEstudoFound;
    }

    @Override
    public List<GrupoEstudoPresencial> findAll() {
        abrirConexao();

        List<GrupoEstudoPresencial> grupoEstudos = grupoEstudoDAO.findAll();
        grupoEstudoDAO.close();

        return grupoEstudos;
    }

    private void abrirConexao() {
        if (grupoEstudoDAO.open() == null) {
            grupoEstudoDAO.open();
        }
    }
}