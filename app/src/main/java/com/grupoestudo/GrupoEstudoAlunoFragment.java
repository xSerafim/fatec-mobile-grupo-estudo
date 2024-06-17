package com.grupoestudo;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import controller.AlunoController;
import controller.GrupoEstudoAlunoController;
import controller.GrupoEstudoController;
import model.Aluno;
import model.Aluno;
import model.Disciplina;
import model.GrupoEstudo;
import model.GrupoEstudoOnline;
import persistence.AlunoDAO;
import persistence.GrupoEstudoAlunoDAO;
import persistence.GrupoEstudoDAO;
import utils.ItemSpinner;
import utils.ItemSpinnerAdapter;

public class GrupoEstudoAlunoFragment extends Fragment {

    private View view;

    private Spinner spGrupo, spAluno;

    private TextView tvExibeGrupos;
    private Button btnSalvar, btnDeletar, btnListar, btnVoltar;
    private AlunoController alunoController;
    private GrupoEstudoController grupoEstudoController;
    private GrupoEstudoAlunoController grupoEstudoAlunoController;
    private Aluno alunoSelecionado;
    private GrupoEstudo grupoEstudoSelecionado;

    public GrupoEstudoAlunoFragment() {
        super();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_grupo_estudo_aluno, container, false);
        
        setViews();
        
        alunoController = new AlunoController(new AlunoDAO(view.getContext()).open());
        grupoEstudoController = new GrupoEstudoController(new GrupoEstudoDAO(view.getContext()).open());
        grupoEstudoAlunoController = new GrupoEstudoAlunoController(new GrupoEstudoAlunoDAO(view.getContext()).open());

        setSpinner(view.getContext());
        setListeners();

        return view;
    }

    private void listarGrupos() {
        try {
            List<GrupoEstudo> grupos = grupoEstudoController.findAll();
            StringBuilder gruposString = new StringBuilder();
            for (GrupoEstudo grupo : grupos) {
                gruposString.append(grupo.toString()).append("\n");
            }
            tvExibeGrupos.setText(gruposString.toString());
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao buscar grupos", Toast.LENGTH_SHORT).show();
        }
    }

    private void inserirAlunoGrupo() {
        try {
            if (alunoSelecionado != null && grupoEstudoSelecionado != null) {
                grupoEstudoAlunoController.create(grupoEstudoSelecionado.getCdGrupo(), alunoSelecionado.getRA());
                Toast.makeText(view.getContext(), "Aluno inserido no grupo com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view.getContext(), "Selecione um aluno e um grupo", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao inserir aluno no grupo", Toast.LENGTH_SHORT).show();
        }
    }

    private void removerAlunoGrupo() {
        try {
            if (alunoSelecionado != null && grupoEstudoSelecionado != null) {
                grupoEstudoAlunoController.delete(grupoEstudoSelecionado.getCdGrupo(), alunoSelecionado.getRA());
                Toast.makeText(view.getContext(), "Aluno removido do grupo com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view.getContext(), "Selecione um aluno e um grupo", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao remover aluno do grupo", Toast.LENGTH_SHORT).show();
        }
    }

    private void voltar() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, new GrupoEstudoFragment());
        fragmentTransaction.commit();
    }

    private void setSpinner(Context context) {
        List<Aluno> alunos = buscarTodosAlunos();
        List<GrupoEstudo> grupos = buscarTodosGrupos();
        if (alunos != null && !alunos.isEmpty()) {
            List<ItemSpinner> itemSpinnerAlunos = listItemSpinnerAlunos(alunos);
            ItemSpinnerAdapter adapter = new ItemSpinnerAdapter(context, itemSpinnerAlunos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spAluno.setAdapter(adapter);
        } else {
            Toast.makeText(view.getContext(), "Erro ao buscar alunos", Toast.LENGTH_SHORT).show();
        }

        if (grupos != null && !grupos.isEmpty()) {
            List<ItemSpinner> itemSpinnerGrupos = listItemSpinnerGrupos(grupos);
            ItemSpinnerAdapter adapter = new ItemSpinnerAdapter(context, itemSpinnerGrupos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGrupo.setAdapter(adapter);
        } else {
            Toast.makeText(view.getContext(), "Erro ao buscar grupos", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Aluno> buscarTodosAlunos() {
        try {
            return alunoController.findAll();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao buscar alunos", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    private List<GrupoEstudo> buscarTodosGrupos() {
        try {
            return grupoEstudoController.findAll();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao buscar Grupos", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    private List<ItemSpinner> listItemSpinnerAlunos(List<Aluno> alunos) {
        List<ItemSpinner> itemSpinnerAlunos = new ArrayList<>();
        for (Aluno aluno : alunos) {
            itemSpinnerAlunos.add(new ItemSpinner(aluno.getRA(), aluno.getNome()));
            System.out.println(aluno.getNome());
        }
        return itemSpinnerAlunos;
    }

    private List<ItemSpinner> listItemSpinnerGrupos(List<GrupoEstudo> grupos) {
        List<ItemSpinner> itemSpinnerGrupos = new ArrayList<>();
        for (GrupoEstudo grupo : grupos) {
            itemSpinnerGrupos.add(new ItemSpinner(grupo.getCdGrupo(), grupo.getNomeGrupo()));
        }
        return itemSpinnerGrupos;
    }

    private void setSpinnerListeners() {
        spAluno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ItemSpinner itemSelecionado = (ItemSpinner) parent.getItemAtPosition(position);
                int RA = itemSelecionado.getCodigo();
                alunoSelecionado = new Aluno();
                alunoSelecionado.setRA(RA);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spGrupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ItemSpinner itemSelecionado = (ItemSpinner) parent.getItemAtPosition(position);
                int cdGrupo = itemSelecionado.getCodigo();
                grupoEstudoSelecionado = new GrupoEstudoOnline();
                grupoEstudoSelecionado.setCdGrupo(cdGrupo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setViews() {
        spGrupo = view.findViewById(R.id.spGrupo);
        spAluno = view.findViewById(R.id.spAluno);
        btnSalvar = view.findViewById(R.id.btnInserirAlunoGrupo);
        btnDeletar = view.findViewById(R.id.btnRemoverAlunoGrupo);
        btnListar = view.findViewById(R.id.btnListarGrupos);
        btnVoltar = view.findViewById(R.id.btnVoltarGrupoEstudo);
        tvExibeGrupos = view.findViewById(R.id.tvExibeGrupos);
    }

    private void setListeners() {
        setSpinnerListeners();
        btnSalvar.setOnClickListener(v -> inserirAlunoGrupo());
        btnDeletar.setOnClickListener(v -> removerAlunoGrupo());
        btnVoltar.setOnClickListener(v -> voltar());
        btnListar.setOnClickListener(v -> listarGrupos());
    }
}