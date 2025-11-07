/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementacion;

/**
 *
 * @author XiusAM
 */



import dao.ClienteDAO;
import modelo.Cliente;
import modelo.Mascota;
import java.io.*;
import java.util.*;

public class ClienteDAOImpl implements ClienteDAO {
    private Map<String, Cliente> clientes = new HashMap<>();
    private static final String ARCHIVO = "data/clientes.txt";

    public ClienteDAOImpl() {
        cargarDesdeArchivo();
    }

    @Override
    public void crearCliente(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
        guardarEnArchivo();
    }

    @Override
    public Cliente obtenerCliente(String id) {
        return clientes.get(id);
    }

    @Override
    public List<Cliente> obtenerTodosClientes() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
        guardarEnArchivo();
    }

    @Override
    public void eliminarCliente(String id) {
        clientes.remove(id);
        guardarEnArchivo();
    }

    private void guardarEnArchivo() {
        try {
            File directorio = new File("data");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO));
            for (Cliente cliente : clientes.values()) {
                writer.write(convertirClienteATexto(cliente));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error al guardar clientes: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        try {
            File archivo = new File(ARCHIVO);
            if (!archivo.exists()) {
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO));
            String linea;
            while ((linea = reader.readLine()) != null) {
                Cliente cliente = convertirTextoACliente(linea);
                if (cliente != null) {
                    clientes.put(cliente.getId(), cliente);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error al cargar clientes: " + e.getMessage());
        }
    }

    private String convertirClienteATexto(Cliente cliente) {
        StringBuilder sb = new StringBuilder();
        sb.append(cliente.getId()).append("|");
        sb.append(cliente.getNombres()).append("|");
        sb.append(cliente.getDireccion()).append("|");
        sb.append(cliente.getContacto()).append("|");
        sb.append(cliente.getNumeroContacto()).append("|");
        
        // Guardar IDs de mascotas
        List<String> mascotaIds = new ArrayList<>();
        for (Mascota mascota : cliente.getMascotasACargo()) {
            mascotaIds.add(mascota.getId());
        }
        sb.append(String.join(",", mascotaIds));
        
        return sb.toString();
    }

    private Cliente convertirTextoACliente(String texto) {
        try {
            String[] partes = texto.split("\\|");
            if (partes.length != 6) return null;

            Cliente cliente = new Cliente();
            cliente.setId(partes[0]);
            cliente.setNombres(partes[1]);
            cliente.setDireccion(partes[2]);
            cliente.setContacto(partes[3]);
            cliente.setNumeroContacto(partes[4]);

            // Las mascotas se cargarán después desde el controlador
            // ya que necesitamos acceso al MascotaDAO

            return cliente;
        } catch (Exception e) {
            System.err.println("Error al convertir texto a cliente: " + e.getMessage());
            return null;
        }
    }
}