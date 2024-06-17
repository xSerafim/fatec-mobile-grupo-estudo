package model;

import androidx.annotation.NonNull;

public class GrupoEstudoAluno {
    private int cdGrupo;
    private int cdAluno;

    public GrupoEstudoAluno(int cdGrupo, int cdAluno) {
        this.cdGrupo = cdGrupo;
        this.cdAluno = cdAluno;
    }

    public int getCdGrupo() {
        return cdGrupo;
    }

    public void setCdGrupo(int cdGrupo) {
        this.cdGrupo = cdGrupo;
    }

    public int getCdAluno() {
        return cdAluno;
    }

    public void setCdAluno(int cdAluno) {
        this.cdAluno = cdAluno;
    }

    @NonNull
    @Override
    public String toString() {
        return "GrupoEstudoAluno{" +
                "cdGrupo=" + cdGrupo +
                ", cdAluno=" + cdAluno +
                '}';
    }
}
