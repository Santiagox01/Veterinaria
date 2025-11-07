/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author XiusAM
 */
package modelo;

import java.util.Date;

public class Consulta {
    private String id;
    private Date fecha;
    private String sintomas;
    private String tratamiento;
    private Mascota mascota;

    public Consulta() {}
    
    public Consulta(String id, Date fecha, String sintomas, String tratamiento, Mascota mascota) {
        this.id = id;
        this.fecha = fecha;
        this.sintomas = sintomas;
        this.tratamiento = tratamiento;
        this.mascota = mascota;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public String getSintomas() { return sintomas; }
    public void setSintomas(String sintomas) { this.sintomas = sintomas; }
    
    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
    
    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota mascota) { this.mascota = mascota; }

    public void setDiagnostico(String trim) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
