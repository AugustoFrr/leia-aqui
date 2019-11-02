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
import com.example.leiaaqui.view.ConsultaLivros;
import com.example.leiaaqui.R;
import com.example.leiaaqui.model.BancoController;
import com.example.leiaaqui.model.CriaBanco;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SearchCliente extends Fragment {

    private ListView listView;
    private String idLivro, categoriaLivro, nomeCliente, tituloLivro, dataRetirada, dataDevolucao;
    BancoController crud;
    private String categoriaCliente;

    public SearchCliente() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_cliente, container, false);

        listView = view.findViewById(R.id.listView);
        crud = new BancoController(getActivity().getApplicationContext());

        searchCliente("");

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
                searchCliente(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchCliente(newText);
                return true;
            }
        });
        return view;
    }

    //Pesquisa na tabela Cliente por nome ou CPF
    private void searchCliente(String keyword) {

        final Cursor cursor = crud.searchCliente(keyword);

        ArrayList<CharSequence> listaClientes = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listaClientes.add(cursor.getString(0) + "\n" + cursor.getString(1));
            } while (cursor.moveToNext());
        } else {
            listaClientes.add(getString(R.string.no_results));
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_text_black, listaClientes);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(!(cursor.getCount() <= 0)){
                    cursor.moveToPosition(position);

                    nomeCliente = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getNOME()));
                    categoriaCliente = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getCategoriaLeitor()));
                    alertaConfirmar();
                }
            }

        });
    }

    //Confirma o cliente selecionado e insere o registro na tabela de livros emprestados
    private void alertaConfirmar() {

        AlertDialog.Builder emprestar = new AlertDialog.Builder(getActivity());

        idLivro = ConsultaLivros.codigo;

        Cursor cursor = crud.selectLivros(true, Integer.parseInt(idLivro));

        tituloLivro = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getTITULO()));
        categoriaLivro = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getCodCategoria()));

        calcularDatas();

        emprestar.setMessage(getString(R.string.deseja_emprestar) + ": \n"+ tituloLivro + "\n"+ getString(R.string.para) + ": \n" + nomeCliente + "?").setPositiveButton(R.string.botaoConfirmar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String result = crud.insertEmprestados(idLivro, categoriaLivro, nomeCliente, tituloLivro, dataRetirada, dataDevolucao, getActivity().getApplicationContext());

                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                //Fecha a lista de clientes e abre a lista de livros emprestados
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
                if(fragment != null) {
                    fragmentTransaction.remove(fragment);

                    ListaEmprestados fragment2 = new ListaEmprestados();

                    fragmentTransaction.add(R.id.fragmentContainer, fragment2);
                    fragmentTransaction.commit();
                }

            }
        }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        emprestar.create();
        emprestar.show();
    }


    //Calcula qual a menor quantidade de dias máximos e define a data de devolução prevista
    private void calcularDatas() {

        int maxCliente = Integer.parseInt(crud.selectMaxDias(categoriaCliente, true));
        int maxLivro = Integer.parseInt(crud.selectMaxDias(categoriaLivro, false));

        int adicionar = (maxCliente < maxLivro) ? maxCliente : maxLivro;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
        Date data = new Date();
        dataRetirada = dateFormat.format(data);
        Calendar c = Calendar.getInstance();
        c.setTime(data);

        c.add(Calendar.DATE,  +adicionar);
        dataDevolucao = dateFormat.format(c.getTime());
    }
}
