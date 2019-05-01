package com.example.labinventario.gestorfirebase;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.labinventario.PrintMessage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class GestorFirebaseDB
{
    private GestorFirebaseDB(){}

    public static void deleteObject(String id, String collection, final Activity activity)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collection).document(id).delete()
        .addOnSuccessListener(activity, new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                PrintMessage.printMessage(activity, "DeleteObject", "Se eliminó");
            }
        })
        .addOnFailureListener(activity, new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                PrintMessage.printMessage(activity, "DeleteObject", "No se eliminó");
            }
        });
    }

    /**
     * Adds an object to the Firebase firestore
     * @param object Object to add
     * @param collection Name of the collection to add the new object
     * @param activity Activity that call it
     */
    public static void addObject(Object object, String collection, final Activity activity)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collection).add(object)
        .addOnSuccessListener(activity, new OnSuccessListener<DocumentReference>()
        {
            @Override
            public void onSuccess(DocumentReference documentReference)
            {
                PrintMessage.printMessage(activity, "AddObject", "Se agregó");
            }
        }).addOnFailureListener(activity, new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                PrintMessage.printMessage(activity, "AddObject", "No se agregó");
            }
        });
    }
}
