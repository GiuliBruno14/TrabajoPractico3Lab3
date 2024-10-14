package com.giulietta.trabajopractico3lab3.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.giulietta.trabajopractico3lab3.model.Usuario;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ApiClient {
    private static File file;

    public static File conectar (Context context){
        if(file == null){
            file = new File(context.getFilesDir(), "Usuario.obj");
        }
        return file;
    }

    public static boolean guardar(Context context, Usuario usuario){
       File file = conectar(context);
        try{
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(usuario);
            bos.flush();
            oos.close();
            return true;
        } catch (FileNotFoundException e) {
            Log.e("ApiClient", "Error al encontrar el archivo: " + e.getMessage());
            Toast.makeText(context, "Error al encontrar el archivo", Toast.LENGTH_SHORT).show();
            return false;
        } catch (IOException e) {
            Log.e("ApiClient", "Error de entrada/salida: " + e.getMessage());
            Toast.makeText(context, "Error entrasa/salida", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static Usuario leer(Context context){
        Usuario usuario = null;
        File file = conectar(context);
        try{
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            usuario = (Usuario) ois.readObject();
        } catch (FileNotFoundException e){
            Toast.makeText(context, "Error al encontrar el archivo", Toast.LENGTH_SHORT).show();
        } catch (IOException e){
            Toast.makeText(context, "Error entrasa/salida", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e){
            Toast.makeText(context, "Error al obtener el usuario", Toast.LENGTH_SHORT).show();
        }
        return usuario;
    }

    public static Usuario login(Context context, String mail, String password) {
        Usuario usuario = leer(context);
        if(usuario != null){
            if(usuario.getMail().equals(mail) && usuario.getPassword().equals(password)){
                return usuario;
            }
        }
        return null;
    }
}
