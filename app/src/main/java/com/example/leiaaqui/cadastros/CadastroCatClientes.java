package com.example.leiaaqui.cadastros;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leiaaqui.R;
import com.example.leiaaqui.model.BancoController;
import com.example.leiaaqui.model.CriaBanco;

public class CadastroCatClientes extends Activity {

    Button botao;
    Button botaoExcluir;
    EditText caixa1;
    EditText caixa2;
    EditText caixa3;
    String nomeCat;
    BancoController crud;
    Cursor cursor;
    Cursor cursor2;

    boolean alterar = false;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cat_clientes);

        botao = findViewById(R.id.botaoConfirmarId);
        botaoExcluir = findViewById(R.id.botaoExcluirId);
        //Caixas de texto
        caixa1 = findViewById(R.id.caixa1Id);
        caixa2 = findViewById(R.id.caixa2Id);
        caixa3 = findViewById(R.id.caixa3Id);

        Intent intent = getIntent();

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                //Preenchimento dos campos para posibilitar a alteração
                id = Integer.parseInt(bundle.getString("codigo"));
                crud = new BancoController(getBaseContext());
                cursor = crud.selectCatClientes(true, id);

                cursor2 = crud.selectClientes(false, -1);

                nomeCat = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getCodCat()));
                caixa1.setText(nomeCat);
                caixa2.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getDescCat())));
                caixa3.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.getMaxDias())));
                botao.setText(R.string.alterar_text);

                alterar = true;
                botaoExcluir.setVisibility(View.VISIBLE);
            }
        }

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BancoController crud = new BancoController(getBaseContext());

                //Valor das caixas
                String codCat = caixa1.getText().toString();
                String descCat = caixa2.getText().toString();
                String maxDias = caixa3.getText().toString();

                if (codCat.isEmpty() || descCat.isEmpty() || maxDias.isEmpty()) {
                    Toast.makeText(CadastroCatClientes.this, R.string.preencha, Toast.LENGTH_SHORT).show();
                } else {
                    String resultado;
                    if (alterar) {
                        //update no banco
                        resultado = crud.insertCatCliente(true, id, codCat, descCat, maxDias, cursor2.getString(3), getApplicationContext());
                    } else {
                        //insert no banco
                        resultado = crud.insertCatCliente(false, -1, codCat, descCat, maxDias, "", getApplicationContext());
                    }
                    Toast.makeText(CadastroCatClientes.this, resultado, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        //define se será possível excluir ou não
        botaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean deletavel = false;

                if (cursor2.moveToFirst()) {
                    do {
                        if (cursor2.getString(3).equalsIgnoreCase(nomeCat)) {
                            deletavel = true;
                            break;
                        }
                    } while (cursor2.moveToNext());
                }

                if (deletavel) {
                    impedeExcluir();
                } else {
                    confirmarExcluir();
                }
            }
        });
    }

    //Confirmação de exclusão
    private void confirmarExcluir() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage(getString(R.string.confrimarExcluir)).setPositiveButton(R.string.botaoConfirmar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                crud.deletaRegistro(2, id);
                Toast.makeText(CadastroCatClientes.this, R.string.sucessoDeletar, Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
                finish();

            }
        }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.create();
        alert.show();
    }

    //Impede a exclusão caso exista um livro com a categoria selecionada
    private void impedeExcluir() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage(getString(R.string.impedeDel)).setPositiveButton(R.string.botaoConfirmar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        alert.create();
        alert.show();
    }
}