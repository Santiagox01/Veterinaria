/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author XiusAM
 */
package dao;

import modelo.Venta;
import java.util.List;

public interface VentaDAO {
    void crearVenta(Venta venta);
    Venta obtenerVenta(String id);
    List<Venta> obtenerTodasVentas();
    void actualizarVenta(Venta venta);
    void eliminarVenta(String id);
}