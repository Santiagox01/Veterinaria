/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author XiusAM
 */


import modelo.Accesorio;
import java.util.List;

public interface AccesorioDAO {
    void crearAccesorio(Accesorio accesorio);
    Accesorio obtenerAccesorio(String id);
    List<Accesorio> obtenerTodosAccesorios();
    void actualizarAccesorio(Accesorio accesorio);
    void eliminarAccesorio(String id);
}