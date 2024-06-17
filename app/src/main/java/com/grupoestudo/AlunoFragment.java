package com.grupoestudo;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import controller.AlunoController;
import model.Aluno;
import persistence.AlunoDAO;

public class AlunoFragment extends Fragment {

    private View view;
    private EditText edtTxtAlunoRA, edtTxtAlunoNome, edtTxtAlunoIdade;
    private Button btnSalvar, btnBuscar, btnModificar, btnDeletar, btnListar;
    private AlunoController alunoController;

    public AlunoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aluno, container, false);

        setViews();

        alunoController = new AlunoController(new AlunoDAO(view.getContext()));

        setListeners();

        return view;
    }

    private void cadastrarAluno() {
        try {
            Aluno aluno = montarAluno(false);
            alunoController.create(aluno);
            Toast.makeText(view.getContext(), "Aluno cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            apagarCampos();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao cadastrar aluno", Toast.LENGTH_SHORT).show();
        }
    }

    private void modificarAluno() {
        try {
            Aluno aluno = montarAluno(false);
            alunoController.update(aluno);
            Toast.makeText(view.getContext(), "Aluno atualizado com sucesso", Toast.LENGTH_SHORT).show();
            apagarCampos();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao atualizar aluno", Toast.LENGTH_SHORT).show();
        }
    }

    private void excluirAluno() {
        try {
            Aluno aluno = montarAluno(true);
            alunoController.delete(aluno);
            Toast.makeText(view.getContext(), "Aluno excluído com sucesso", Toast.LENGTH_SHORT).show();
            apagarCampos();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao excluir aluno", Toast.LENGTH_SHORT).show();
        }
    }

    private void buscarAluno() {
        try {
            Aluno aluno = montarAluno(true);
            aluno = alunoController.findOne(aluno);
            if(aluno.getNome() != null) {
                preencherCampos(aluno);
            } else {
                Toast.makeText(view.getContext(), "Aluno não encontrado", Toast.LENGTH_SHORT).show();
                apagarCampos();
            }

        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao buscar aluno", Toast.LENGTH_SHORT).show();
            apagarCampos();
        }
    }

    private void buscarTodosAlunos() {
        try {
            List<Aluno> alunos = alunoController.findAll();
            StringBuilder stringBuffer = new StringBuilder();
            for (Aluno aluno : alunos) {
                stringBuffer.append(aluno.toString()).append("\n");
            }
            Toast.makeText(view.getContext(), stringBuffer.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao buscar alunos", Toast.LENGTH_SHORT).show();
        }
    }

    private Aluno montarAluno(boolean ehBuscaOuExclusao) {
        Aluno aluno = new Aluno();
        if (ehBuscaOuExclusao) {
            aluno.setRA(Integer.parseInt(edtTxtAlunoRA.getText().toString()));
        } else {
            aluno.setRA(Integer.parseInt(edtTxtAlunoRA.getText().toString()));
            aluno.setNome(edtTxtAlunoNome.getText().toString());
            aluno.setIdade(Integer.parseInt(edtTxtAlunoIdade.getText().toString()));
        }
        return aluno;
    }

    private void setViews() {
        edtTxtAlunoRA = view.findViewById(R.id.etRAAluno);
        edtTxtAlunoNome = view.findViewById(R.id.etNomeAluno);
        edtTxtAlunoIdade = view.findViewById(R.id.etIdadeAluno);
        btnBuscar  = view.findViewById(R.id.btnBuscarAluno);
        btnSalvar = view.findViewById(R.id.btnCadastrarAluno);
        btnModificar = view.findViewById(R.id.btnModificarAlunos);
        btnDeletar = view.findViewById(R.id.btnExcluirAluno);
        btnListar = view.findViewById(R.id.btnListarAlunos);
    }

    private void setListeners() {
        btnSalvar.setOnClickListener(v -> cadastrarAluno());
        btnBuscar.setOnClickListener(v -> buscarAluno());
        btnModificar.setOnClickListener(v -> modificarAluno());
        btnDeletar.setOnClickListener(v -> excluirAluno());
        btnListar.setOnClickListener(v -> buscarTodosAlunos());
    }

    private void preencherCampos(Aluno aluno) {
        edtTxtAlunoRA.setText(String.valueOf(aluno.getRA()));
        edtTxtAlunoNome.setText(aluno.getNome());
        edtTxtAlunoIdade.setText(String.valueOf(aluno.getIdade()));
    }
    private void apagarCampos() {
        edtTxtAlunoRA.setText("");
        edtTxtAlunoNome.setText("");
        edtTxtAlunoIdade.setText("");
    }
}