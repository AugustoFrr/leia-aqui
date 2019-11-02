package com.example.leiaaqui.view;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.leiaaqui.R;
import com.example.leiaaqui.fragments.ListaEmprestados;
import com.example.leiaaqui.fragments.SearchCliente;
import com.example.leiaaqui.model.BancoController;
import com.example.leiaaqui.model.CriaBanco;

import java.util.ArrayList;

public class ConsultaLivros extends AppCompatActivity {

    ListView listView;
    public static String codigo = "";
    TextView button;

    public ConsultaLivros() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_livros);

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.emprestados);
        searchLivro("");

        //Criação da barra de pesquisa
        SearchView searchView = findViewById(R.id.search_id);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLivro(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchLivro(newText);
                return true;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarLivro();
            }
        });
    }


    //Método que cria e atualiza a lista de livros pesquisados
    private void searchLivro(String keyword) {
        BancoController crud = new BancoController(getApplicationContext());

        final Cursor cursor = crud.searchLivro(keyword, false);

        ArrayList<CharSequence> listaLivros = new ArrayList<>();
        String indisponivel = "";

        if (cursor.moveToFirst()) {

            do {
                if (cursor.getString(2).equals("true")) {
                    indisponivel = " - Emprestado";
                } else {
                    indisponivel = "";
                }

                listaLivros.add(cursor.getString(0) + indisponivel);
            } while (cursor.moveToNext());
        } else {
            listaLivros.add(getString(R.string.no_results));
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, R.layout.list_text, listaLivros);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!(cursor.getCount() <= 0)) {
                    cursor.moveToPosition(position);
                    codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getID()));

                    if (cursor.getString(2).equals("true")) {
                        Toast.makeText(ConsultaLivros.this, "Livro indisponível!", Toast.LENGTH_SHORT).show();
                    } else {

                        alertaEmprestar();
                    }
                }
            }

        });
    }

    //Confirmação de empréstimo
    public void alertaEmprestar() {
        AlertDialog.Builder emprestar = new AlertDialog.Builder(this);

        emprestar.setMessage(getString(R.string.mensagem_emprestar) + "?").setPositiveButton(R.string.emprestar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                selecionarCliente();

            }
        }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        emprestar.create();
        emprestar.show();
    }

    //Escolher o cliente que pegará o livro emprestado
    private void selecionarCliente() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SearchCliente fragment = new SearchCliente();

        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    //Lista de livros emprestados
    private void selecionarLivro() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ListaEmprestados fragment = new ListaEmprestados();

        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    //Fecha o fragment ou a activity
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if (fragment != null) {
            fragmentTransaction.remove(fragment).commit();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        } else {
            finish();
        }
    }

    @Override
    protected void onRestart() {
        startActivity(getIntent());
        finish();
        super.onRestart();
    }
}
