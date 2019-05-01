package com.example.labinventario;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.labinventario.gestorfirebase.GestorFirebaseDB;
import com.example.labinventario.gestorfirebase.GestorImagenesFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    public static final ArrayList<Producto> productos = new ArrayList<>();
    public ArrayList<String> idProductos = new ArrayList<>();
    public ArrayAdapter<String> adapter;
    public ArrayList<String> nbrProductos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nbrProductos);

        ListView listProductos = findViewById(R.id.listProductos);
        listProductos.setAdapter(adapter);

        FirebaseFirestore.getInstance().collection("producto")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
            {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task)
                {
                    if (task.isSuccessful())
                    {
                        for (QueryDocumentSnapshot document : task.getResult())
                        {
                            Map<String, Object> data = document.getData();
                            idProductos.add(document.getId());
                            Producto producto = new Producto();
                            producto.setNombre((String) data.get("nombre"));
                            producto.setDescripcion((String) data.get("descripcion"));
                            producto.setFoto((String) data.get("foto"));
                            producto.setPrecio((Double) data.get("precio"));
                            nbrProductos.add(producto.getNombre());
                            productos.add(producto);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        Log.d("Documents", "Error getting documents: ", task.getException());
                    }
                }
            });

        listProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), IngresarProductoActivity.class);
                intent.putExtra("producto", position);
                intent.putExtra("detalles", true);
                startActivity(intent);
            }
        });
        listProductos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                eliminarProducto(position);
                return false;
            }
        });
    }

    private void eliminarProducto(int idProducto)
    {
        GestorFirebaseDB.deleteObject(idProductos.get(idProducto), "producto", this);
        GestorImagenesFirebase.deleteImage(this, "prueba", productos.get(idProducto).getFoto());
        idProductos.remove(idProducto);
        productos.remove(idProducto);
        nbrProductos.remove(idProducto);
        adapter.notifyDataSetChanged();
    }

    public void btnInsertarProductoListener(View view)
    {
        Intent intent = new Intent(this, IngresarProductoActivity.class);
        startActivity(intent);
    }
}
