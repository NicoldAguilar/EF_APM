package com.example.practiceforandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.practiceforandroid.entidades.Image;
import com.example.practiceforandroid.entidades.ImageResponse;
import com.example.practiceforandroid.entidades.PokemonEnt;
import com.example.practiceforandroid.servicios.ImageService;
import com.example.practiceforandroid.servicios.PokeService;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;

public class FormPokemonActivity extends AppCompatActivity {

    EditText etNombre, etimgURL;
    private ImageView ivPokemonImg;
    private Button button,btnOpenGallery;
    Spinner spTipo;

    private final static int CAMERA_REQUEST = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pokemon);

        etNombre = findViewById(R.id.etNombre);
        etimgURL = findViewById(R.id.etimgURL);

        spTipo = findViewById(R.id.spTipo);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pokemons_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spTipo.setAdapter(adapter);

        button = findViewById(R.id.btnEnviar);
        button.setOnClickListener(v -> {
            onClick(v);
        });

        //Datos de la camara
        btnOpenGallery = findViewById(R.id.btnOpenGallery);
        ivPokemonImg = findViewById(R.id.ivPokemonImg);
        btnOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    openGallery();
                }
                else{
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 101 );
                }

            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1001);
    }

    private void onClick(View v) {
        enviar(v);
        Intent intent = new Intent(this, PokemonActivity.class);
        startActivity(intent);

    }

    public void enviar(View v) {
        String nombres = etNombre.getText().toString();
        String type = spTipo.getSelectedItem().toString(); //cargar el spinner a la api
        String imgURL = etimgURL.getText().toString();
        PokemonEnt poke = new PokemonEnt();
        poke.nombre = nombres;
        poke.type = type;
        poke.imgURL = imgURL;
        postRetrofit(poke);
    }

    public void postRetrofit(PokemonEnt contactosrg) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://63023872c6dda4f287b57f7c.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PokeService service = retrofit.create(PokeService.class);
        service.create(contactosrg).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("MAIN_APP", "RESPONSE" + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CAMERA_REQUEST && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivPokemonImg.setImageBitmap(imageBitmap);
        }

        if(requestCode==1001){
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(),filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap imageBitmap = BitmapFactory.decodeFile(picturePath);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            String imgBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.imgur.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ImageService imageService = retrofit.create(ImageService.class);
            Image image = new Image();
            image.image = imgBase64;

            imageService.sendImage(image).enqueue(new Callback<ImageResponse>() {
                @Override
                public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                    String imR = response.body().data.link;
                    etimgURL.setText(imR);
                }

                @Override
                public void onFailure(Call<ImageResponse> call, Throwable t) {

                }
            });
        }
    }
}