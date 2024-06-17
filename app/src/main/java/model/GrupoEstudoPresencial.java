package model;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.List;

public class GrupoEstudoPresencial extends GrupoEstudo {
    private int sala;

    public GrupoEstudoPresencial(int cdGrupo, String nomeGrupo, Disciplina disciplina, List<Aluno> alunos,
                                 String dataEncontro, int sala, int capacidade) {
        super(cdGrupo, nomeGrupo, disciplina, dataEncontro, alunos);
        this.sala = sala;
    }

    public GrupoEstudoPresencial() { }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }


    @NonNull
    @Override
    public String toString() {
        return "GrupoEstudoPresencial{" +
                "sala=" + sala +
                ", cdGrupo=" + getCdGrupo() +
                ", nomeGrupo='" + getNomeGrupo() + '\'' +
                ", disciplina=" + getDisciplina() +
                ", dataEncontro=" + getDataEncontro() +
                ", alunos=" + montaExibicaoAlunos() +
                '}';
    }
}
