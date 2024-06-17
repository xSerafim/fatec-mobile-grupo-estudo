package model;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class GrupoEstudoOnline extends GrupoEstudo {
    private String link;

    public GrupoEstudoOnline(int cdGrupo, String nomeGrupo, Disciplina disciplina, List<Aluno> alunos,
                             String dataEncontro, String link) {
        super(cdGrupo, nomeGrupo, disciplina, dataEncontro, alunos);
        this.link = link;
    }

    public GrupoEstudoOnline() {
        super();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @NonNull
    @Override
    public String toString() {
        return "GrupoEstudoOnline{" +
                "cdGrupo=" + getCdGrupo() +
                ", nomeGrupo='" + getNomeGrupo() + '\'' +
                ", disciplina=" + getDisciplina() +
                ", dataEncontro=" + getDataEncontro() +
                ", link='" + link + '\'' +
                ", alunos=" + montaExibicaoAlunos() +
                '}';
    }
}
