/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author XiusAM
 */
package dao;

import modelo.Cliente;
import java.util.List;

public interface ClienteDAO {
    void crearCliente(Cliente cliente);
    Cliente obtenerCliente(String id);
    List<Cliente> obtenerTodosClientes();
    void actualizarCliente(Cliente cliente);
    void eliminarCliente(String id);
}