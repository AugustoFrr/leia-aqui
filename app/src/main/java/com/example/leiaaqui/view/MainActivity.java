package com.example.leiaaqui.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.view.ContextThemeWrapper;

import com.example.leiaaqui.R;
import com.example.leiaaqui.cadastros.CadastroCatClientes;
import com.example.leiaaqui.cadastros.CadastroCatLivros;
import com.example.leiaaqui.cadastros.CadastroClientes;
import com.example.leiaaqui.cadastros.CadastroLivros;

public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

    ImageButton botaoCliente, botaoCatCliente, botaoLivros, botaoCatLivros;
    private String whatButton;
    ImageView imageView;
    AnimationDrawable animation;
    AlertDialog.Builder confirmExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoCliente = findViewById(R.id.imageButton1);
        botaoLivros = findViewById(R.id.imageButton2);
        botaoCatCliente = findViewById(R.id.imageButton3);
        botaoCatLivros = findViewById(R.id.imageButton4);
        imageView = findViewById(R.id.imageView);

        //Configura a animação da coruja movendo a cabeça
        imageView.setImageResource(R.drawable.head_move);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        animation = (AnimationDrawable) imageView.getDrawable();
    }

    //Permite que a animação seja iniciada
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animation.start();
    }

    //função de onClick dos botões
    public void showPopup(View view) {
        Context wrapper = new ContextThemeWrapper(this, R.style.popupMenu);

        PopupMenu popupMenu = new PopupMenu(wrapper, view);

        whatButton = view.getTag().toString(); // variável que identifica qual botao foi clicado

        popupMenu.setOnMenuItemClickListener(this);
        if (whatButton.equals("livros")) {
            popupMenu.inflate(R.menu.livro_menu);
        } else {
            popupMenu.inflate(R.menu.popover_menu);
        }
        popupMenu.show();

    }

    //funções dos menus dos botoes
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        Intent telaCadasto = null;
        Intent intent = new Intent(MainActivity.this, SelectDados.class);
        Bundle bundle = new Bundle();

        switch (menuItem.getItemId()) {
            case R.id.adicionarId:

                switch (whatButton) {
                    case "clientes":
                        telaCadasto = new Intent(MainActivity.this, CadastroClientes.class);
                        break;
                    case "catClientes":
                        telaCadasto = new Intent(MainActivity.this, CadastroCatClientes.class);
                        break;

                    case "livros":
                        telaCadasto = new Intent(MainActivity.this, CadastroLivros.class);
                        break;

                    case "catLivros":
                        telaCadasto = new Intent(MainActivity.this, CadastroCatLivros.class);
                        break;
                }

                startActivity(telaCadasto);

                return true;

            case R.id.alterarId:
                bundle.putString("whatButton", whatButton);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;

            case R.id.consultarId:
                startActivity(new Intent(MainActivity.this, ConsultaLivros.class));
                return true;
        }
        return false;
    }

    //Mensagem de confirmação de saída do App
    public void onBackPressed() {

        confirmExit = new AlertDialog.Builder(this);

        confirmExit.setMessage(R.string.exit_message).setPositiveButton(R.string.sair, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();

            }
        }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        confirmExit.create();
        confirmExit.show();
    }
}
