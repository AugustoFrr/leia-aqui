package com.example.leiaaqui.fragments;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.leiaaqui.R;
import com.example.leiaaqui.model.BancoController;
import com.example.leiaaqui.model.CriaBanco;
import java.util.ArrayList;

public class ListaEmprestados extends Fragment {

    private ListView listView;
    private String idLivro, nomeLivro;
    private BancoController crud;

    public ListaEmprestados() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_emprestados, container, false);

        listView = view.findViewById(R.id.listView);
        crud = new BancoController(getActivity().getApplicationContext());

        searchLivrosEmprestados("");

        //Criação da barra de pesquisa
        SearchView searchView = view.findViewById(R.id.search_id);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLivrosEmprestados(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchLivrosEmprestados(newText);
                return true;
            }
        });

        return view;
    }

    //Pesquisa e cria a lista de livros emprestados
    private void searchLivrosEmprestados(String keyword) {

        final Cursor cursor = crud.searchLivro(keyword, true);

        ArrayList<CharSequence> listaLivros = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listaLivros.add(cursor.getString(0) + "\n" + cursor.getString(1) + "\nRetirada: " + cursor.getString(2) + " - Retorno: " + cursor.getString(3));
            } while (cursor.moveToNext());
        } else {
            listaLivros.add(getString(R.string.no_results));
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_text_black, listaLivros);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(!(cursor.getCount() <= 0)){
                    cursor.moveToPosition(position);

                    idLivro = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getIdLivro()));
                    nomeLivro = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getTituloObra()));

                    alertaDevolucao();
                }
            }

        });
    }

    //Confirma a devolução de um livro
    private void alertaDevolucao() {

        AlertDialog.Builder devolver = new AlertDialog.Builder(getActivity());

        devolver.setMessage(getString(R.string.devolConf) + "\n" + nomeLivro).setPositiveButton(R.string.botaoConfirmar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                crud.devolveLivro(Integer.parseInt(idLivro));

                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.livroDevolvido), Toast.LENGTH_SHORT).show();
                refreshFragment();

            }
        }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        devolver.create();
        devolver.show();

    }

    //Atualiza o fragment após uma devolução
    private void refreshFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        fragmentTransaction.detach(fragment).attach(fragment).commit();
    }
}
