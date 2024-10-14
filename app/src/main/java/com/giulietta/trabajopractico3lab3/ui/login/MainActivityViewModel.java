package com.giulietta.trabajopractico3lab3.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.giulietta.trabajopractico3lab3.model.Usuario;
import com.giulietta.trabajopractico3lab3.request.ApiClient;
import com.giulietta.trabajopractico3lab3.ui.registro.RegistroActivity;

public class MainActivityViewModel extends AndroidViewModel {
    private Context context;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void Login(String mail, String password){
        if(mail.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        Usuario usuario = ApiClient.login(context, mail, password);
        if(usuario != null){
            Intent intent = new Intent(context, RegistroActivity.class);
            intent.putExtra("login", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else{
            Toast.makeText(context,"Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void Registrar(){
        Intent intent = new Intent(context, RegistroActivity.class);
        intent.putExtra("login", false);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
