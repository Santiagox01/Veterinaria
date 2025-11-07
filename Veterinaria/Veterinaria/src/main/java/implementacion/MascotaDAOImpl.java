/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementacion;

/**
 *
 * @author XiusAM
 */



import dao.MascotaDAO;
import modelo.Mascota;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MascotaDAOImpl implements MascotaDAO {
    private Map<String, Mascota> mascotas = new HashMap<>();
    private static final String ARCHIVO = "data/mascotas.txt";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public MascotaDAOImpl() {
        cargarDesdeArchivo();
    }

    @Override
    public void crearMascota(Mascota mascota) {
        mascotas.put(mascota.getId(), mascota);
        guardarEnArchivo();
    }

    @Override
    public Mascota obtenerMascota(String id) {
        return mascotas.get(id);
    }

    @Override
    public List<Mascota> obtenerTodasMascotas() {
        return new ArrayList<>(mascotas.values());
    }

    @Override
    public void actualizarMascota(Mascota mascota) {
        mascotas.put(mascota.getId(), mascota);
        guardarEnArchivo();
    }

    @Override
    public void eliminarMascota(String id) {
        mascotas.remove(id);
        guardarEnArchivo();
    }

    @Override
    public List<Mascota> obtenerMascotasPorEstado(String estado) {
        List<Mascota> resultado = new ArrayList<>();
        for (Mascota mascota : mascotas.values()) {
            if (mascota.getEstado().equals(estado)) {
                resultado.add(mascota);
            }
        }
        return resultado;
    }

    private void guardarEnArchivo() {
        try {
            File directorio = new File("data");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO));
            for (Mascota mascota : mascotas.values()) {
                writer.write(convertirMascotaATexto(mascota));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error al guardar mascotas: " + e.getMessage());
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
                Mascota mascota = convertirTextoAMascota(linea);
                if (mascota != null) {
                    mascotas.put(mascota.getId(), mascota);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error al cargar mascotas: " + e.getMessage());
        }
    }

    private String convertirMascotaATexto(Mascota mascota) {
        StringBuilder sb = new StringBuilder();
        sb.append(mascota.getId()).append("|");
        sb.append(mascota.getNombre()).append("|");
        sb.append(mascota.getRaza()).append("|");
        sb.append(mascota.getEdad()).append("|");
        sb.append(String.join(",", mascota.getVacunas())).append("|");
        sb.append(mascota.getTipo()).append("|");
        sb.append(mascota.getPeso()).append("|");
        sb.append(mascota.getEstado()).append("|");
        sb.append(dateFormat.format(mascota.getFechaIngreso())).append("|");
        sb.append(mascota.getLugarOrigen()).append("|");
        sb.append(mascota.getGenero());
        return sb.toString();
    }

    private Mascota convertirTextoAMascota(String texto) {
        try {
            String[] partes = texto.split("\\|");
            if (partes.length != 11) return null;

            Mascota mascota = new Mascota();
            mascota.setId(partes[0]);
            mascota.setNombre(partes[1]);
            mascota.setRaza(partes[2]);
            mascota.setEdad(Integer.parseInt(partes[3]));
            
            // Procesar vacunas
            List<String> vacunas = new ArrayList<>();
            if (!partes[4].isEmpty()) {
                Collections.addAll(vacunas, partes[4].split(","));
            }
            mascota.setVacunas(vacunas);
            
            mascota.setTipo(partes[5]);
            mascota.setPeso(Double.parseDouble(partes[6]));
            mascota.setEstado(partes[7]);
            mascota.setFechaIngreso(dateFormat.parse(partes[8]));
            mascota.setLugarOrigen(partes[9]);
            mascota.setGenero(partes[10]);

            return mascota;
        } catch (Exception e) {
            System.err.println("Error al convertir texto a mascota: " + e.getMessage());
            return null;
        }
    }
}