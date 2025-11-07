/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String id;
    private String nombres;
    private String direccion;
    private String contacto;
    private String numeroContacto;
    private List<Mascota> mascotasACargo;

    public Cliente() {
        this.mascotasACargo = new ArrayList<>();
    }
    
    public Cliente(String id, String nombres, String direccion, String contacto, String numeroContacto) {
        this();
        this.id = id;
        this.nombres = nombres;
        this.direccion = direccion;
        this.contacto = contacto;
        this.numeroContacto = numeroContacto;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    
    public String getNumeroContacto() { return numeroContacto; }
    public void setNumeroContacto(String numeroContacto) { this.numeroContacto = numeroContacto; }
    
    public List<Mascota> getMascotasACargo() { return mascotasACargo; }
    public void setMascotasACargo(List<Mascota> mascotasACargo) { this.mascotasACargo = mascotasACargo; }
    
    public void agregarMascota(Mascota mascota) {
        this.mascotasACargo.add(mascota);
    }
}
