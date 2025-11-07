/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementacion;

/**
 *
 * @author XiusAM
 */




import dao.VentaDAO;
import modelo.Venta;
import modelo.Mascota;
import modelo.Cliente;
import modelo.Accesorio;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class VentaDAOImpl implements VentaDAO {
    private Map<String, Venta> ventas = new HashMap<>();
    private static final String ARCHIVO = "data/ventas.txt";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public VentaDAOImpl() {
        cargarDesdeArchivo();
    }

    @Override
    public void crearVenta(Venta venta) {
        ventas.put(venta.getId(), venta);
        guardarEnArchivo();
    }

    @Override
    public Venta obtenerVenta(String id) {
        return ventas.get(id);
    }

    @Override
    public List<Venta> obtenerTodasVentas() {
        return new ArrayList<>(ventas.values());
    }

    @Override
    public void actualizarVenta(Venta venta) {
        ventas.put(venta.getId(), venta);
        guardarEnArchivo();
    }

    @Override
    public void eliminarVenta(String id) {
        ventas.remove(id);
        guardarEnArchivo();
    }

    private void guardarEnArchivo() {
        try {
            File directorio = new File("data");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO));
            for (Venta venta : ventas.values()) {
                writer.write(convertirVentaATexto(venta));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error al guardar ventas: " + e.getMessage());
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
                Venta venta = convertirTextoAVenta(linea);
                if (venta != null) {
                    ventas.put(venta.getId(), venta);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error al cargar ventas: " + e.getMessage());
        }
    }

    private String convertirVentaATexto(Venta venta) {
        StringBuilder sb = new StringBuilder();
        sb.append(venta.getId()).append("|");
        sb.append(dateFormat.format(venta.getFecha())).append("|");
        sb.append(venta.getPrecio()).append("|");
        sb.append(venta.getCliente().getId()).append("|");
        sb.append(venta.getVendedor()).append("|");
        
        // IDs de mascotas
        List<String> mascotaIds = new ArrayList<>();
        for (Mascota mascota : venta.getMascotas()) {
            mascotaIds.add(mascota.getId());
        }
        sb.append(String.join(",", mascotaIds)).append("|");
        
        // IDs de accesorios
        List<String> accesorioIds = new ArrayList<>();
        for (Accesorio accesorio : venta.getAccesorios()) {
            accesorioIds.add(accesorio.getId());
        }
        sb.append(String.join(",", accesorioIds));
        
        return sb.toString();
    }

    private Venta convertirTextoAVenta(String texto) {
        try {
            String[] partes = texto.split("\\|");
            if (partes.length != 7) return null;

            Venta venta = new Venta();
            venta.setId(partes[0]);
            venta.setFecha(dateFormat.parse(partes[1]));
            venta.setPrecio(Double.parseDouble(partes[2]));
            
            // El cliente y las mascotas se cargarán desde el controlador
            
            venta.setVendedor(partes[4]);

            return venta;
        } catch (Exception e) {
            System.err.println("Error al convertir texto a venta: " + e.getMessage());
            return null;
        }
    }
}