package com.grupoestudo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import controller.DisciplinaController;
import model.Disciplina;
import persistence.DisciplinaDAO;

public class DisciplinaFragment extends Fragment {

    private View view;
    private EditText edtTxtCdDisciplina, edtTxtDisciplinaNome;
    private Button btnSalvar, btnBuscar, btnModificar, btnDeletar, btnListar;
    private DisciplinaController disciplinaController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_disciplina, container, false);

        setViews();

        disciplinaController = new DisciplinaController(new DisciplinaDAO(view.getContext()).open());

        setListeners();

        return view;
    }

    private void cadastrarDisciplina() {
        try {
            Disciplina disciplina = montarDisciplina(false);
            disciplinaController.create(disciplina);
            Toast.makeText(view.getContext(), "Disciplina cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            apagarCampos();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao cadastrar disciplina", Toast.LENGTH_SHORT).show();
        }
    }

    private void modificarDisciplina() {
        try {
            Disciplina disciplina = montarDisciplina(false);
            disciplinaController.update(disciplina);
            Toast.makeText(view.getContext(), "Disciplina atualizada com sucesso", Toast.LENGTH_SHORT).show();
            apagarCampos();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao atualizar disciplina", Toast.LENGTH_SHORT).show();
        }
    }

    private void excluirDisciplina() {
        try {
            Disciplina disciplina = montarDisciplina(true);
            disciplinaController.delete(disciplina);
            Toast.makeText(view.getContext(), "Disciplina excluída com sucesso", Toast.LENGTH_SHORT).show();
            apagarCampos();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao excluir disciplina", Toast.LENGTH_SHORT).show();
        }
    }

    private void buscarDisciplina() {
        try {
            Disciplina disciplina = montarDisciplina(true);
            disciplina = disciplinaController.findOne(disciplina);
            if(disciplina.getNomeDisciplina() != null) {
                preencherCampos(disciplina);
            } else {
                Toast.makeText(view.getContext(), "Disciplina não encontrada", Toast.LENGTH_SHORT).show();
                apagarCampos();
            }

        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao buscar disciplina", Toast.LENGTH_SHORT).show();
            apagarCampos();
        }
    }

    private void buscarTodosDisciplinas() {
        try {
            List<Disciplina> disciplinas = disciplinaController.findAll();
            StringBuilder stringBuffer = new StringBuilder();
            for (Disciplina disciplina : disciplinas) {
                stringBuffer.append(disciplina.toString()).append("\n");
            }
            Toast.makeText(view.getContext(), stringBuffer.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao buscar disciplinas", Toast.LENGTH_SHORT).show();
        }
    }

    private Disciplina montarDisciplina(boolean ehBuscaOuExclusao) {
        Disciplina disciplina = new Disciplina();
        if (ehBuscaOuExclusao) {
            disciplina.setCdDisciplina(Integer.parseInt(edtTxtCdDisciplina.getText().toString()));
        } else {
            disciplina.setCdDisciplina(Integer.parseInt(edtTxtCdDisciplina.getText().toString()));
            disciplina.setNomeDisciplina(edtTxtDisciplinaNome.getText().toString());
        }
        return disciplina;
    }

    private void setViews() {
        edtTxtCdDisciplina = view.findViewById(R.id.etCdDisciplina);
        edtTxtDisciplinaNome = view.findViewById(R.id.etNomeDisciplina);
        btnBuscar  = view.findViewById(R.id.btnBuscarDisciplina);
        btnSalvar = view.findViewById(R.id.btnCadastrarDisciplina);
        btnModificar = view.findViewById(R.id.btnModificarDisciplina);
        btnDeletar = view.findViewById(R.id.btnExcluirDisciplina);
        btnListar = view.findViewById(R.id.btnListarDisciplina);
    }

    private void setListeners() {
        btnSalvar.setOnClickListener(v -> cadastrarDisciplina());
        btnBuscar.setOnClickListener(v -> buscarDisciplina());
        btnModificar.setOnClickListener(v -> modificarDisciplina());
        btnDeletar.setOnClickListener(v -> excluirDisciplina());
        btnListar.setOnClickListener(v -> buscarTodosDisciplinas());
    }

    private void preencherCampos(Disciplina disciplina) {
        edtTxtCdDisciplina.setText(String.valueOf(disciplina.getCdDisciplina()));
        edtTxtDisciplinaNome.setText(disciplina.getNomeDisciplina());
    }
    private void apagarCampos() {
        edtTxtCdDisciplina.setText("");
        edtTxtDisciplinaNome.setText("");
    }
}