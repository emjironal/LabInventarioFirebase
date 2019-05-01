package com.example.labinventario;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.labinventario.gestorfirebase.GestorFirebaseDB;
import com.example.labinventario.gestorfirebase.GestorImagenesFirebase;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.ExecutionException;

public class IngresarProductoActivity extends AppCompatActivity
{
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView vistaPreviaImg;
    EditText txtNombre;
    EditText txtPrecio;
    EditText txtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_producto);

        vistaPreviaImg = findViewById(R.id.imgVistaPrevia);
        imageUri = null;
        txtNombre = findViewById(R.id.txtNombreProducto);
        txtPrecio = findViewById(R.id.txtPrecioProducto);
        txtDescripcion = findViewById(R.id.txtDescripcionProducto);

        Intent intent = getIntent();
        boolean detalles = intent.getBooleanExtra("detalles", false);
        if(detalles)
        {
            Button btnAgregar = findViewById(R.id.btnAgregarProducto);
            Button btnImagen = findViewById(R.id.btnSelectImage);

            btnAgregar.setVisibility(View.INVISIBLE);
            btnImagen.setVisibility(View.INVISIBLE);
            int producto = intent.getIntExtra("producto", -1);
            setProducto(producto);
        }
    }
    public void setProducto(int idProducto)
    {
        if(idProducto != -1)
        {
            Producto producto = MainActivity.productos.get(idProducto);
            txtNombre.setText(producto.getNombre());
            txtDescripcion.setText(producto.getDescripcion());
            txtPrecio.setText(producto.getPrecio().toString());
            GestorImagenesFirebase.getUrlFoto("prueba", producto.getFoto()).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    ImageDownloader imageDownloader = new ImageDownloader();
                    try
                    {
                        Bitmap bitmap = imageDownloader.execute(uri.toString()).get();
                        vistaPreviaImg.setImageBitmap(bitmap);
                    }
                    catch (ExecutionException e)
                    {
                        e.printStackTrace();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            txtPrecio.setEnabled(false);
            txtDescripcion.setEnabled(false);
            txtNombre.setEnabled(false);
        }
    }

    public void btnAgregarProductoListener(View view)
    {
        if(imageUri != null)
        {
            GestorImagenesFirebase.uploadLocalImage(this, "prueba", imageUri);

            String nombre = txtNombre.getText().toString();
            Double precio = Double.parseDouble(txtPrecio.getText().toString());
            String descripcion = txtDescripcion.getText().toString();

            Producto producto = new Producto(nombre, precio, imageUri.getLastPathSegment(), descripcion);

            GestorFirebaseDB.addObject(producto, "producto", this);
        }
        else
        {
            PrintMessage.printMessage(this, "Datos incompletos", "Seleccione una imagen");
        }
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
