package model;

import androidx.annotation.NonNull;

import java.util.List;

public abstract class GrupoEstudo {

    private int cdGrupo;
    private String nomeGrupo;
    private Disciplina disciplina;
    private String dataEncontro;
    private List<Aluno> alunos;

    public GrupoEstudo(int cdGrupo, String nomeGrupo, Disciplina disciplina, String dataEncontro, List<Aluno> alunos) {
        this.cdGrupo = cdGrupo;
        this.nomeGrupo = nomeGrupo;
        this.disciplina = disciplina;
        this.dataEncontro = dataEncontro;
        this.alunos = alunos;
    }

    public GrupoEstudo() { }

    public String getDataEncontro() {
        return dataEncontro;
    }

    public void setDataEncontro(String dataEncontro) {
        this.dataEncontro = dataEncontro;
    }

    public int getCdGrupo() {
        return cdGrupo;
    }

    public void setCdGrupo(int cdGrupo) {
        this.cdGrupo = cdGrupo;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public String montaExibicaoAlunos() {
        StringBuilder exibicaoAlunos = new StringBuilder();
        for (Aluno aluno : getAlunos()) {
            exibicaoAlunos.append(aluno.getNome()).append(", ");
        }
        return exibicaoAlunos.toString();
    }

    @NonNull
    @Override
    public String toString() {
        return "GrupoEstudo{" +
                "cdGrupo=" + cdGrupo +
                ", nomeGrupo='" + nomeGrupo + '\'' +
                ", disciplina=" + disciplina +
                ", dataEncontro=" + dataEncontro +
                ", alunos=" + alunos +
                '}';
    }
}
