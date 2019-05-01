package com.example.labinventario;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.labinventario.gestorfirebase.GestorFirebaseDB;
import com.example.labinventario.gestorfirebase.GestorImagenesFirebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity
{
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView vistaPreviaImg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vistaPreviaImg = findViewById(R.id.imgVistaPrevia);
        //Producto producto = new Producto("prueba", 123.123, "foto", "esto es una prueba");
        //GestorFirebaseDB.addObject(producto, "producto", this);
    }

    public void btnAgregarProductoListener(View view)
    {
        GestorImagenesFirebase.uploadLocalImage(this, "prueba", imageUri);
    }

    public void openGallery(View view)
    {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            imageUri = data.getData();
            vistaPreviaImg.setImageURI(imageUri);
        }
    }
}
