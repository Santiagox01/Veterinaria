/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

import java.util.Date;
import java.util.List;

public class Mascota {
    private String id;
    private String nombre;
    private String raza;
    private int edad;
    private List<String> vacunas;
    private String tipo;
    private double peso;
    private String estado; // "ADOPTADO" o "SIN_ADOPTAR"
    private Date fechaIngreso;
    private String lugarOrigen;
    private String genero;
    private List<Consulta> historialConsultas;

    public Mascota() {}
    
    public Mascota(String id, String nombre, String raza, int edad, List<String> vacunas, 
                  String tipo, double peso, String estado, Date fechaIngreso, 
                  String lugarOrigen, String genero) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.vacunas = vacunas;
        this.tipo = tipo;
        this.peso = peso;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
        this.lugarOrigen = lugarOrigen;
        this.genero = genero;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }
    
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    
    public List<String> getVacunas() { return vacunas; }
    public void setVacunas(List<String> vacunas) { this.vacunas = vacunas; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public Date getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Date fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    
    public String getLugarOrigen() { return lugarOrigen; }
    public void setLugarOrigen(String lugarOrigen) { this.lugarOrigen = lugarOrigen; }
    
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    
    public List<Consulta> getHistorialConsultas() { return historialConsultas; }
    public void setHistorialConsultas(List<Consulta> historialConsultas) { this.historialConsultas = historialConsultas; }
}
