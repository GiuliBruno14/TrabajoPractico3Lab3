package com.giulietta.trabajopractico3lab3.ui.registro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.giulietta.trabajopractico3lab3.databinding.ActivityRegistroBinding;
import com.giulietta.trabajopractico3lab3.model.Usuario;

public class RegistroActivity extends AppCompatActivity {
    public RegistroActivityViewModel mv;
    public ActivityRegistroBinding binding;
    private Intent i;

    private ActivityResultLauncher<Intent> arl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mv= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistroActivityViewModel.class);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        abrirGaleria();

        mv.getMUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                binding.etDni.setText(usuario.getDni());
                binding.etApellido.setText(usuario.getApellido());
                binding.etNombre.setText(usuario.getNombre());
                binding.etMail.setText(usuario.getMail());
                binding.etPassword.setText(usuario.getPassword());
                binding.ivImagen.setImageURI(Uri.parse(usuario.getImagen()));

            }
        });
        Intent intent = getIntent();
        boolean bool = intent.getBooleanExtra("login", false);

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dni = binding.etDni.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String nombre = binding.etNombre.getText().toString();
                String mail = binding.etMail.getText().toString();
                String password = binding.etPassword.getText().toString();
                mv.editar(new Usuario(dni,apellido, nombre, mail, password));
            }
        });
        mv.getUriMutable().observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {

                binding.ivImagen.setImageURI(uri);
            }
        });
        mv.leerDatos(bool);

        binding.btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                arl.launch(i);
            }
        });

    }

    private void abrirGaleria(){
        i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        arl=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                mv.recibirFoto(result);
            }
        });

    }
}