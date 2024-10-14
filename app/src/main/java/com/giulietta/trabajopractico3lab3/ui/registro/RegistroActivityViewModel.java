package com.giulietta.trabajopractico3lab3.ui.registro;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.giulietta.trabajopractico3lab3.model.Usuario;
import com.giulietta.trabajopractico3lab3.request.ApiClient;
import com.giulietta.trabajopractico3lab3.ui.login.MainActivity;
import static android.app.Activity.RESULT_OK;

public class RegistroActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Usuario> mUsuario;
    private MutableLiveData<Uri> uriMutableLiveData;
    private Uri uri;

    public RegistroActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Usuario> getMUsuario(){
        if(mUsuario == null){
            mUsuario = new MutableLiveData<>();

        }
        return mUsuario;
    }

    public LiveData<Uri> getUriMutable(){

        if(uriMutableLiveData==null){
            uriMutableLiveData=new MutableLiveData<>();
        }
        return uriMutableLiveData;
    }

    public void leerDatos(boolean bool){
        if(bool){
            mUsuario.setValue(ApiClient.leer(context));
        }
    }

    public void editar(Usuario usuario){
        if(usuario.getDni().isEmpty() || usuario.getApellido().isEmpty() || usuario.getNombre().isEmpty() || usuario.getMail().isEmpty() ||usuario.getPassword().isEmpty()){
            Toast.makeText(context, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            if (uri != null) {
                usuario.setImagen(uri.toString()); // Asegúrate de que el método setImagen acepte un String
            } else {
                Toast.makeText(context, "No se ha seleccionado ninguna imagen", Toast.LENGTH_SHORT).show();
            }
            if(ApiClient.guardar(context, usuario)) {
                Toast.makeText(context, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "No se pudo actualizar/guardar correctamente", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void recibirFoto(ActivityResult result) {
        if(result.getResultCode() == RESULT_OK){
            Intent data=result.getData();
            uri=data.getData();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                context.getContentResolver().takePersistableUriPermission (uri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            uriMutableLiveData.setValue(uri);
        }
    }


}
