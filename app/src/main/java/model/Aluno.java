package model;

import androidx.annotation.NonNull;

public class Aluno {

    private int RA;
    private String nome;
    private int idade;

    public Aluno() { }

    public Aluno(int RA, String nome, int idade) {
        this.RA = RA;
        this.nome = nome;
        this.idade = idade;
    }

    public int getRA() {
        return RA;
    }

    public void setRA(int RA) {
        this.RA = RA;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @NonNull
    @Override
    public String toString() {
        return "Aluno{" +
                "RA=" + RA +
                ", nome='" + nome + '\'' +
                ", idade='" + idade + '\'' +
                '}';
    }
}