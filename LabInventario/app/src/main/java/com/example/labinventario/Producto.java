package com.example.labinventario;

public class Producto
{
    private String nombre;
    private Double precio;
    private String foto;
    private String descripcion;

    public Producto(){}

    public Producto(String pNombre, Double pPrecio, String pFoto, String pDescripcion)
    {
        setNombre(pNombre);
        setPrecio(pPrecio);
        setFoto(pFoto);
        setDescripcion(pDescripcion);
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
