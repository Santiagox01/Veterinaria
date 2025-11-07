/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author XiusAM
 */
package dao;

import modelo.Mascota;
import java.util.List;

public interface MascotaDAO {
    void crearMascota(Mascota mascota);
    Mascota obtenerMascota(String id);
    List<Mascota> obtenerTodasMascotas();
    void actualizarMascota(Mascota mascota);
    void eliminarMascota(String id);
    List<Mascota> obtenerMascotasPorEstado(String estado);
}