/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author XiusAM
 */
package dao;

import modelo.Consulta;
import java.util.List;

public interface ConsultaDAO {
    void crearConsulta(Consulta consulta);
    Consulta obtenerConsulta(String id);
    List<Consulta> obtenerTodasConsultas();
    void actualizarConsulta(Consulta consulta);
    void eliminarConsulta(String id);
    List<Consulta> obtenerConsultasPorMascota(String mascotaId);
}
