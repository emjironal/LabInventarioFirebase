package com.example.labinventario;

public class Usuario
{
    private String nombre;
    private String clave;

    public Usuario(){}

    public Usuario(String pNombre, String pClave)
    {
        setNombre(pNombre);
        setClave(pClave);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
