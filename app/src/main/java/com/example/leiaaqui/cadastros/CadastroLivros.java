package com.example.leiaaqui.cadastros;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.leiaaqui.R;
import com.example.leiaaqui.model.BancoController;
import com.example.leiaaqui.model.CriaBanco;

import java.util.ArrayList;

public class CadastroLivros extends Activity {

    Button botao;
    Button botaoExcluir;
    EditText caixa1;
    EditText caixa2;
    EditText caixa3;
    EditText caixa4;
    EditText caixa5;
    EditText caixa6;
    EditText caixa7;
    EditText caixa8;
    EditText caixa9;
    EditText caixa10;
    Spinner spinner;
    BancoController crud;

    boolean alterar = false;
    int id;
    String codCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livros);

        crud = new BancoController(getBaseContext());

        botao = findViewById(R.id.botaoConfirmarId);
        botaoExcluir = findViewById(R.id.botaoExcluirId);
        spinner = findViewById(R.id.spinnerId);

        //Caixas de texto
        caixa1 = findViewById(R.id.caixa1Id);
        caixa2 = findViewById(R.id.caixa2Id);
        caixa3 = findViewById(R.id.caixa3Id);
        caixa5 = findViewById(R.id.caixa5Id);
        caixa6 = findViewById(R.id.caixa6Id);
        caixa7 = findViewById(R.id.caixa7Id);
        caixa8 = findViewById(R.id.caixa8Id);
        caixa9 = findViewById(R.id.caixa9Id);
        caixa10 = findViewById(R.id.caixa10Id);

        Cursor cursorCategorias = crud.selectCatLivros(false, 0);
        ArrayList<CharSequence> lista = new ArrayList<>();

        lista.add(getString(R.string.selecione_cat));
        if (cursorCategorias.moveToFirst()) {
            do {
                lista.add(cursorCategorias.getString(0));
            } while (cursorCategorias.moveToNext());
        } else {
            showAlert();
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, R.layout.spinner_custom, lista);
        adapter.setDropDownViewResource(R.layout.spinner_custom);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                codCategoria = (String) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Intent intent = getIntent();

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {

                //Preenchimento dos campos para posibilitar a alteração
                id = Integer.parseInt(bundle.getString("codigo"));

                Cursor cursor = crud.selectLivros(true, id);

                caixa1.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getCODIGO())));
                caixa2.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getISBN())));
                caixa3.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getTITULO())));
                caixa5.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getAUTORES())));
                caixa6.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getPalavrasChave())));
                caixa7.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getDataPublic())));
                caixa8.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getNumeroEdicao())));
                caixa9.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getEDITORA())));
                caixa10.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getNumPaginas())));
                botao.setText(R.string.alterar_text);

                //Preenchimento do spinner com as categorias definidas
                for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
                    if (spinner.getAdapter().getItem(i).toString().contains((cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getCodCategoria()))))) {
                        spinner.setSelection(i);
                    }
                }
                alterar = true;
                botaoExcluir.setVisibility(View.VISIBLE);

            }
        }

        //Confirma o cadastramento
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BancoController crud = new BancoController(getBaseContext());

                //Valor das caixas
                String codigo = caixa1.getText().toString();
                String isbn = caixa2.getText().toString();
                String titulo = caixa3.getText().toString();
                String autores = caixa5.getText().toString();
                String palavrasChave = caixa6.getText().toString();
                String dataPublic = caixa7.getText().toString();
                String numEdicao = caixa8.getText().toString();
                String editora = caixa9.getText().toString();
                String numPags = caixa10.getText().toString();

                if (codigo.isEmpty() || isbn.isEmpty() || titulo.isEmpty() || codCategoria.equals(getString(R.string.selecione_cat)) || autores.isEmpty() ||
                        palavrasChave.isEmpty() || dataPublic.isEmpty() || numEdicao.isEmpty() || editora.isEmpty() || numPags.isEmpty()) {

                    Toast.makeText(CadastroLivros.this, R.string.preencha, Toast.LENGTH_SHORT).show();
                } else {
                    String resultado;
                    if (alterar) {
                        //update no banco
                        resultado = crud.insertLivro(true, id, codigo, isbn, titulo, codCategoria, autores, palavrasChave,
                                dataPublic, numEdicao, editora, numPags, getApplicationContext());

                    } else {
                        //insert no banco
                        resultado = crud.insertLivro(false, -1, codigo, isbn, titulo, codCategoria, autores, palavrasChave,
                                dataPublic, numEdicao, editora, numPags, getApplicationContext());

                    }
                    Toast.makeText(CadastroLivros.this, resultado, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        botaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmarExcluir();
            }
        });
    }


    //Alerta para a criação de uma categoria, se não existir uma
    private void showAlert() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setCancelable(false);

        alert.setMessage(getString(R.string.alertCatLivro)).setPositiveButton(R.string.vamos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                startActivity(new Intent(CadastroLivros.this, CadastroCatLivros.class));
                finish();

            }
        }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        alert.create();
        alert.show();
    }

    //Alerta para confirmar a exclusão de um item
    private void confirmarExcluir() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage(getString(R.string.confrimarExcluir)).setPositiveButton(R.string.botaoConfirmar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                crud.deletaRegistro(3, id);
                Toast.makeText(CadastroLivros.this, R.string.sucessoDeletar, Toast.LENGTH_SHORT).show();
                finish();

            }
        }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.create();
        alert.show();
    }
}