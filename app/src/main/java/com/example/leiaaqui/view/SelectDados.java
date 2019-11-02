package com.example.leiaaqui.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leiaaqui.R;
import com.example.leiaaqui.cadastros.CadastroCatClientes;
import com.example.leiaaqui.cadastros.CadastroCatLivros;
import com.example.leiaaqui.cadastros.CadastroClientes;
import com.example.leiaaqui.cadastros.CadastroLivros;
import com.example.leiaaqui.model.BancoController;
import com.example.leiaaqui.model.CriaBanco;

import java.util.ArrayList;

public class SelectDados extends Activity {

    ListView lista;
    BancoController crud;
    Intent intent;
    Bundle bundle;
    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_dados);

        titulo = findViewById(R.id.titulo);
        lista = findViewById(R.id.listView);
        crud = new BancoController(getBaseContext());
        bundle = new Bundle();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String botao = "";

        if (bundle != null) {
            botao = bundle.getString("whatButton");
        }

        if (botao != null) { //Verifica qual botão foi clicado e define qual lista a ser criada
            switch (botao) {
                case "clientes":
                    consultaClientes();
                    break;
                case "catClientes":
                    consultaCatClientes();
                    break;
                case "livros":
                    consultaLivros();
                    break;
                case "catLivros":
                    consultaCatLivros();
                    break;
            }
        }
    }

    public void consultaClientes() { //Select da lista de clientes
        titulo.setText(getString(R.string.botao_cliente));
        final Cursor cursor = crud.selectClientes(false, -1);

        ArrayList<CharSequence> listaClientes = new ArrayList<>();

        if (cursor.moveToFirst()) {//Adiciona o retorno do select em um ArrayList
            do {
                listaClientes.add(cursor.getString(1) + "\n" + cursor.getString(2));
            } while (cursor.moveToNext());
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, R.layout.list_text, listaClientes);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getID()));

                intent = new Intent(SelectDados.this, CadastroClientes.class);
                bundle.putString("codigo", codigo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void consultaCatClientes() {  //Select da lista de categorias de clientes
        titulo.setText(getString(R.string.botao_cat_cliente));

        final Cursor cursor = crud.selectCatClientes(false, -1);
        ArrayList<CharSequence> listaClientes = new ArrayList<>();

        if (cursor.moveToFirst()) {//Adiciona o retorno do select em um ArrayList
            do {
                listaClientes.add(cursor.getString(1) + "\n" + cursor.getString(2));
            } while (cursor.moveToNext());
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, R.layout.list_text, listaClientes);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getID()));

                intent = new Intent(SelectDados.this, CadastroCatClientes.class);
                bundle.putString("codigo", codigo);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

    public void consultaLivros() { //Select da lista de livros
        titulo.setText(getString(R.string.botao_livros));
        final Cursor cursor = crud.selectLivros(false, -1);
        ArrayList<CharSequence> listaClientes = new ArrayList<>();

        if (cursor.moveToFirst()) { //Adiciona o retorno do select em um ArrayList
            do {
                listaClientes.add(cursor.getString(2) + "\n" + cursor.getString(1));
            } while (cursor.moveToNext());
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, R.layout.list_text, listaClientes);

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getID()));

                intent = new Intent(SelectDados.this, CadastroLivros.class);
                bundle.putString("codigo", codigo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void consultaCatLivros() { //Select da lista de categorias de livros
        titulo.setText(getString(R.string.botao_cat_livros));
        final Cursor cursor = crud.selectCatLivros(false, -1);
        ArrayList<CharSequence> listaClientes = new ArrayList<>();

        if (cursor.moveToFirst()) {//Adiciona o retorno do select em um ArrayList
            do {
                listaClientes.add(cursor.getString(1) + "\n" + cursor.getString(2));
            } while (cursor.moveToNext());
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, R.layout.list_text, listaClientes);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getID()));

                intent = new Intent(SelectDados.this, CadastroCatLivros.class);
                bundle.putString("codigo", codigo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    //Refresh da activity
    @Override
    protected void onRestart() {
        startActivity(getIntent());
        finish();
        super.onRestart();
    }
}