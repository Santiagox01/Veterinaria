/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementacion;

/**
 *
 * @author XiusAM
 */

import dao.ConsultaDAO;
import modelo.Consulta;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsultaDAOImpl implements ConsultaDAO {
    private Map<String, Consulta> consultas = new HashMap<>();
    private static final String ARCHIVO = "data/consultas.txt";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ConsultaDAOImpl() {
        cargarDesdeArchivo();
    }

    @Override
    public void crearConsulta(Consulta consulta) {
        consultas.put(consulta.getId(), consulta);
        guardarEnArchivo();
    }

    @Override
    public Consulta obtenerConsulta(String id) {
        return consultas.get(id);
    }

    @Override
    public List<Consulta> obtenerTodasConsultas() {
        return new ArrayList<>(consultas.values());
    }

    @Override
    public void actualizarConsulta(Consulta consulta) {
        consultas.put(consulta.getId(), consulta);
        guardarEnArchivo();
    }

    @Override
    public void eliminarConsulta(String id) {
        consultas.remove(id);
        guardarEnArchivo();
    }

    @Override
    public List<Consulta> obtenerConsultasPorMascota(String mascotaId) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : consultas.values()) {
            if (consulta.getMascota().getId().equals(mascotaId)) {
                resultado.add(consulta);
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
            for (Consulta consulta : consultas.values()) {
                writer.write(convertirConsultaATexto(consulta));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error al guardar consultas: " + e.getMessage());
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
                Consulta consulta = convertirTextoAConsulta(linea);
                if (consulta != null) {
                    consultas.put(consulta.getId(), consulta);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error al cargar consultas: " + e.getMessage());
        }
    }

    private String convertirConsultaATexto(Consulta consulta) {
        StringBuilder sb = new StringBuilder();
        sb.append(consulta.getId()).append("|");
        sb.append(dateFormat.format(consulta.getFecha())).append("|");
        sb.append(consulta.getSintomas().replace("|", " ")).append("|");
        sb.append(consulta.getTratamiento().replace("|", " ")).append("|");
        sb.append(consulta.getMascota().getId());
        return sb.toString();
    }

    private Consulta convertirTextoAConsulta(String texto) {
        try {
            String[] partes = texto.split("\\|");
            if (partes.length != 5) return null;

            Consulta consulta = new Consulta();
            consulta.setId(partes[0]);
            consulta.setFecha(dateFormat.parse(partes[1]));
            consulta.setSintomas(partes[2]);
            consulta.setTratamiento(partes[3]);
            
            // La mascota se cargará desde el controlador
            
            return consulta;
        } catch (Exception e) {
            System.err.println("Error al convertir texto a consulta: " + e.getMessage());
            return null;
        }
    }
}