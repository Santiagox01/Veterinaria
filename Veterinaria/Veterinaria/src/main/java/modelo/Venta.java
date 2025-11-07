/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package modelo;

import java.util.Date;
import java.util.List;

public class Venta {
    private String id;
    private Date fecha;
    private double precio;
    private Cliente cliente;
    private String vendedor;
    private List<Mascota> mascotas;
    private List<Accesorio> accesorios;

    public Venta() {}
    
    public Venta(String id, Date fecha, double precio, Cliente cliente, 
                String vendedor, List<Mascota> mascotas, List<Accesorio> accesorios) {
        this.id = id;
        this.fecha = fecha;
        this.precio = precio;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.mascotas = mascotas;
        this.accesorios = accesorios;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public String getVendedor() { return vendedor; }
    public void setVendedor(String vendedor) { this.vendedor = vendedor; }
    
    public List<Mascota> getMascotas() { return mascotas; }
    public void setMascotas(List<Mascota> mascotas) { this.mascotas = mascotas; }
    
    public List<Accesorio> getAccesorios() { return accesorios; }
    public void setAccesorios(List<Accesorio> accesorios) { this.accesorios = accesorios; }
}