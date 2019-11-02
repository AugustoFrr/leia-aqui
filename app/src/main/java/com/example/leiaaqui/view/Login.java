package com.example.leiaaqui.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leiaaqui.R;
import com.example.leiaaqui.model.BancoController;

public class Login extends Activity {

    Button botaoLogin;
    EditText user, senha;
    BancoController crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        botaoLogin = findViewById(R.id.botaoLoginId);
        user = findViewById(R.id.userId);
        senha = findViewById(R.id.senhaId);

        crud = new BancoController(getBaseContext());

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checarLogin();
            }
        });
    }

    //Método para fazer o login
    public void checarLogin(){
        Intent intent = new Intent(Login.this, MainActivity.class);

        String usuario = user.getText().toString();
        Cursor cursor;
        if (usuario.equals("")) {
            Toast.makeText(Login.this, getString(R.string.digite_user), Toast.LENGTH_SHORT).show();
        } else {
            cursor = crud.selectLogin(usuario);

            if (cursor.moveToFirst()) {
                if (senha.getText().toString().equals(cursor.getString(0))) {
                    Toast.makeText(Login.this, getString(R.string.bem_vindo), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, getString(R.string.senha_incorreta), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Login.this, getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
