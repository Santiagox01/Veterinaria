/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementacion;

/**
 *
 * @author XiusAM
 */




import dao.AccesorioDAO;
import modelo.Accesorio;
import java.io.*;
import java.util.*;

public class AccesorioDAOImpl implements AccesorioDAO {
    private Map<String, Accesorio> accesorios = new HashMap<>();
    private static final String ARCHIVO = "data/accesorios.txt";

    public AccesorioDAOImpl() {
        cargarDesdeArchivo();
    }

    @Override
    public void crearAccesorio(Accesorio accesorio) {
        accesorios.put(accesorio.getId(), accesorio);
        guardarEnArchivo();
    }

    @Override
    public Accesorio obtenerAccesorio(String id) {
        return accesorios.get(id);
    }

    @Override
    public List<Accesorio> obtenerTodosAccesorios() {
        return new ArrayList<>(accesorios.values());
    }

    @Override
    public void actualizarAccesorio(Accesorio accesorio) {
        accesorios.put(accesorio.getId(), accesorio);
        guardarEnArchivo();
    }

    @Override
    public void eliminarAccesorio(String id) {
        accesorios.remove(id);
        guardarEnArchivo();
    }

    private void guardarEnArchivo() {
        try {
            File directorio = new File("data");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO));
            for (Accesorio accesorio : accesorios.values()) {
                writer.write(convertirAccesorioATexto(accesorio));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error al guardar accesorios: " + e.getMessage());
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
                Accesorio accesorio = convertirTextoAAccesorio(linea);
                if (accesorio != null) {
                    accesorios.put(accesorio.getId(), accesorio);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error al cargar accesorios: " + e.getMessage());
        }
    }

    private String convertirAccesorioATexto(Accesorio accesorio) {
        StringBuilder sb = new StringBuilder();
        sb.append(accesorio.getId()).append("|");
        sb.append(accesorio.getNombre()).append("|");
        sb.append(accesorio.getTipo()).append("|");
        sb.append(accesorio.getPrecio()).append("|");
        sb.append(accesorio.getStock());
        return sb.toString();
    }

    private Accesorio convertirTextoAAccesorio(String texto) {
        try {
            String[] partes = texto.split("\\|");
            if (partes.length != 5) return null;

            Accesorio accesorio = new Accesorio();
            accesorio.setId(partes[0]);
            accesorio.setNombre(partes[1]);
            accesorio.setTipo(partes[2]);
            accesorio.setPrecio(Double.parseDouble(partes[3]));
            accesorio.setStock(Integer.parseInt(partes[4]));

            return accesorio;
        } catch (Exception e) {
            System.err.println("Error al convertir texto a accesorio: " + e.getMessage());
            return null;
        }
    }
}