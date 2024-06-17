package model;

import androidx.annotation.NonNull;

public class Disciplina {
    private int cdDisciplina;
    private String nomeDisciplina;

    public Disciplina() { }

    public Disciplina(int cdDisciplina, String nomeDisciplina) {
        this.cdDisciplina = cdDisciplina;
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public int getCdDisciplina() {
        return cdDisciplina;
    }

    public void setCdDisciplina(int cdDisciplina) {
        this.cdDisciplina = cdDisciplina;
    }

    @NonNull
    @Override
    public String toString() {
        return "Disciplina{" +
                "cdDisciplina=" + cdDisciplina +
                ", nomeDisciplina='" + nomeDisciplina + '\'' +
                '}';
    }
}
