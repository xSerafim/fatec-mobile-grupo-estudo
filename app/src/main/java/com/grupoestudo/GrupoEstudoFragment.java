package com.grupoestudo;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import controller.DisciplinaController;
import controller.GrupoEstudoController;
import controller.GrupoEstudoOnlineController;
import controller.GrupoEstudoPresencialController;
import model.Disciplina;
import model.GrupoEstudo;
import model.GrupoEstudoOnline;
import model.GrupoEstudoPresencial;
import persistence.DisciplinaDAO;
import persistence.GrupoEstudoDAO;
import persistence.GrupoEstudoOnlineDAO;
import persistence.GrupoEstudoPresencialDAO;
import utils.ItemSpinner;
import utils.ItemSpinnerAdapter;

public class GrupoEstudoFragment extends Fragment {

    private View view;

    private Spinner spnDisciplina;
    private EditText edtTxtCdGrupoEstudo, edtTxtNomeGrupoEstudo, edtTxtDataGrupoEstudo,
            edtTxtLinkGrupoEstudo, edtTxtSalaGrupoEstudo;
    private RadioGroup radioGroupTipoGrupoEstudo;
//    private Da
    private Button btnSalvar, btnBuscar, btnModificar, btnDeletar, btnTrocarFragment;

    private Disciplina disciplinaSelecionada;
    private boolean ehOnline;

    private DisciplinaController disciplinaController;
    private GrupoEstudoOnlineController grupoEstudoOnlineController;
    private GrupoEstudoPresencialController grupoEstudoPresencialController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_grupo_estudo, container, false);

        setViews();

        Context context = view.getContext();

        disciplinaController = new DisciplinaController(new DisciplinaDAO(context).open());
        grupoEstudoOnlineController = new GrupoEstudoOnlineController(new GrupoEstudoOnlineDAO(context).open());
        grupoEstudoPresencialController = new GrupoEstudoPresencialController(new GrupoEstudoPresencialDAO(context).open());

        setSpinner(context);
        setListeners();

        return view;
    }

    private void cadastrarGrupo() {
        try {
            GrupoEstudo grupoEstudo = montarGrupo(false, ehOnline);
            if(ehOnline) {
                grupoEstudoOnlineController.create((GrupoEstudoOnline) grupoEstudo);
            } else {
                grupoEstudoPresencialController.create((GrupoEstudoPresencial) grupoEstudo);
            }
            Toast.makeText(view.getContext(), "Grupo cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            apagarCampos();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao cadastrar Grupo", Toast.LENGTH_SHORT).show();
        }
    }

    private void deletarGrupo() {
        try {
            GrupoEstudo grupoEstudo = montarGrupo(true, ehOnline);
            if(ehOnline) {
                grupoEstudoOnlineController.delete((GrupoEstudoOnline) grupoEstudo);
            } else {
                grupoEstudoPresencialController.delete((GrupoEstudoPresencial) grupoEstudo);
            }
            Toast.makeText(view.getContext(), "Grupo excluído com sucesso", Toast.LENGTH_SHORT).show();
            apagarCampos();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao excluir Grupo", Toast.LENGTH_SHORT).show();
        }
    }

    private void buscarGrupo() {
        try {
            GrupoEstudo grupoEstudo = montarGrupo(true, ehOnline);
            if(ehOnline) {
                grupoEstudo = grupoEstudoOnlineController.findOne((GrupoEstudoOnline) grupoEstudo);
            } else {
                grupoEstudo = grupoEstudoPresencialController.findOne((GrupoEstudoPresencial) grupoEstudo);
            }
            if(grupoEstudo.getNomeGrupo() != null) {
                preencherCampos(grupoEstudo);
            } else {
                Toast.makeText(view.getContext(), "Grupo não encontrado", Toast.LENGTH_SHORT).show();
                apagarCampos();
            }

        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao buscar grupo", Toast.LENGTH_SHORT).show();
            apagarCampos();
        }
    }

    private void modificarGrupo() {
        try {
            GrupoEstudo grupoEstudo = montarGrupo(false, ehOnline);
            if(ehOnline) {
                grupoEstudoOnlineController.update((GrupoEstudoOnline) grupoEstudo);
            } else {
                grupoEstudoPresencialController.update((GrupoEstudoPresencial) grupoEstudo);
            }
            Toast.makeText(view.getContext(), "Grupo atualizado com sucesso", Toast.LENGTH_SHORT).show();
            apagarCampos();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao atualizar grupo", Toast.LENGTH_SHORT).show();
        }
    }

    private GrupoEstudo montarGrupo(boolean ehBuscaOuExclusao, boolean ehOnline) {
        GrupoEstudo grupoEstudo;
        if (ehOnline) {
            grupoEstudo = new GrupoEstudoOnline();
            if(ehBuscaOuExclusao) {
                grupoEstudo.setCdGrupo(Integer.parseInt(edtTxtCdGrupoEstudo.getText().toString()));
            } else {
                grupoEstudo.setCdGrupo(Integer.parseInt(edtTxtCdGrupoEstudo.getText().toString()));
                grupoEstudo.setNomeGrupo(edtTxtNomeGrupoEstudo.getText().toString());
                grupoEstudo.setDisciplina(disciplinaSelecionada);
                grupoEstudo.setDataEncontro(edtTxtDataGrupoEstudo.getText().toString());
                ((GrupoEstudoOnline) grupoEstudo).setLink(edtTxtLinkGrupoEstudo.getText().toString());
            }
        } else {
            grupoEstudo = new GrupoEstudoPresencial();
            if(ehBuscaOuExclusao) {
                grupoEstudo.setCdGrupo(Integer.parseInt(edtTxtCdGrupoEstudo.getText().toString()));
            } else {
                grupoEstudo.setCdGrupo(Integer.parseInt(edtTxtCdGrupoEstudo.getText().toString()));
                grupoEstudo.setNomeGrupo(edtTxtNomeGrupoEstudo.getText().toString());
                grupoEstudo.setDisciplina(disciplinaSelecionada);
                grupoEstudo.setDataEncontro(edtTxtDataGrupoEstudo.getText().toString());
                ((GrupoEstudoPresencial) grupoEstudo).setSala(Integer.parseInt(edtTxtSalaGrupoEstudo.getText().toString()));
            }
        }

        return grupoEstudo;
    }

    private List<Disciplina> buscarTodosDisciplinas() {
        try {
            return disciplinaController.findAll();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao buscar Grupos", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    private List<ItemSpinner> listItemSpinnerDisciplinas(List<Disciplina> disciplinas) {
        List<ItemSpinner> itemSpinnerDisciplinas = new ArrayList<>();
        for (Disciplina disciplina : disciplinas) {
            itemSpinnerDisciplinas.add(new ItemSpinner(disciplina.getCdDisciplina(), disciplina.getNomeDisciplina()));
        }
        return itemSpinnerDisciplinas;
    }

    private void setSpinner(Context context) {
        List<Disciplina> disciplinas = buscarTodosDisciplinas();
        if (disciplinas != null) {
            List<ItemSpinner> itemSpinnerDisciplinas = listItemSpinnerDisciplinas(disciplinas);
            ItemSpinnerAdapter adapter = new ItemSpinnerAdapter(context, itemSpinnerDisciplinas);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnDisciplina.setAdapter(adapter);
        } else {
            Toast.makeText(view.getContext(), "Erro ao buscar disciplinas", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSpinnerListeners() {
        spnDisciplina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ItemSpinner itemSelecionado = (ItemSpinner) parent.getItemAtPosition(position);
                int codigoDisciplina = itemSelecionado.getCodigo();
                String nomeDisciplina = itemSelecionado.getNome();
                disciplinaSelecionada = new Disciplina(codigoDisciplina, nomeDisciplina);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setRadioGroupListeners() {
        radioGroupTipoGrupoEstudo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rBtnOnline) {
                    edtTxtLinkGrupoEstudo.setVisibility(View.VISIBLE);
                    edtTxtSalaGrupoEstudo.setVisibility(View.GONE);
                    ehOnline = true;
                } else if (checkedId == R.id.rdBtnPresencial) {
                    edtTxtLinkGrupoEstudo.setVisibility(View.GONE);
                    edtTxtSalaGrupoEstudo.setVisibility(View.VISIBLE);
                    ehOnline = false;
                }
            }
        });
    }

    private void setViews() {
        spnDisciplina = view.findViewById(R.id.spDisciplina);
        edtTxtCdGrupoEstudo = view.findViewById(R.id.edtTxtCdGrupoEstudo);
        edtTxtNomeGrupoEstudo = view.findViewById(R.id.edtTxtNomeGrupoEstudo);
        edtTxtDataGrupoEstudo = view.findViewById(R.id.edtTxtDataGrupoEstudo);
        edtTxtLinkGrupoEstudo = view.findViewById(R.id.edtTxtLinkGrupoEstudo);
        edtTxtSalaGrupoEstudo = view.findViewById(R.id.edtTxtSalaGrupoEstudo);
        radioGroupTipoGrupoEstudo = view.findViewById(R.id.radioGroup);
        btnSalvar = view.findViewById(R.id.btnCadastrarGrupoEstudo);
        btnBuscar = view.findViewById(R.id.btnBuscarGrupoEstudo);
        btnModificar = view.findViewById(R.id.btnModificarGrupoEstudo);
        btnDeletar = view.findViewById(R.id.btnExcluirGrupoEstudo);
        btnTrocarFragment = view.findViewById(R.id.btnTrocarFragment);
    }

    private void setListeners() {
        setSpinnerListeners();
        setRadioGroupListeners();
        btnSalvar.setOnClickListener(v -> cadastrarGrupo());
        btnBuscar.setOnClickListener(v -> buscarGrupo());
        btnModificar.setOnClickListener(v -> modificarGrupo());
        btnDeletar.setOnClickListener(v -> deletarGrupo());
        btnTrocarFragment.setOnClickListener(v -> irParaGrupoEstudoAlunoFragment());
    }

    private void preencherCampos(GrupoEstudo grupoEstudo) {
        edtTxtCdGrupoEstudo.setText(String.valueOf(grupoEstudo.getCdGrupo()));
        edtTxtNomeGrupoEstudo.setText(grupoEstudo.getNomeGrupo());
        edtTxtDataGrupoEstudo.setText(grupoEstudo.getDataEncontro());
        if (grupoEstudo instanceof GrupoEstudoOnline) {
            edtTxtLinkGrupoEstudo.setText(((GrupoEstudoOnline) grupoEstudo).getLink());
            radioGroupTipoGrupoEstudo.check(R.id.rBtnOnline);
        } else {
            edtTxtSalaGrupoEstudo.setText(String.valueOf(((GrupoEstudoPresencial) grupoEstudo).getSala()));
            radioGroupTipoGrupoEstudo.check(R.id.rdBtnPresencial);
        }
    }
    private void apagarCampos() {
        edtTxtCdGrupoEstudo.setText("");
        edtTxtNomeGrupoEstudo.setText("");
        edtTxtDataGrupoEstudo.setText("");
        edtTxtLinkGrupoEstudo.setText("");
        edtTxtSalaGrupoEstudo.setText("");
        spnDisciplina.setSelection(0);
        radioGroupTipoGrupoEstudo.clearCheck();
    }

    private void irParaGrupoEstudoAlunoFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, new GrupoEstudoAlunoFragment());
        fragmentTransaction.commit();
    }

}