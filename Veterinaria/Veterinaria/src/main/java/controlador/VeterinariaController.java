/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author XiusAM
 */
import dao.*;
import implementacion.*;
import modelo.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class VeterinariaController {
    private MascotaDAO mascotaDAO;
    private ClienteDAO clienteDAO;
    private VentaDAO ventaDAO;
    private ConsultaDAO consultaDAO;
    private AccesorioDAO accesorioDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public VeterinariaController() {
        this.mascotaDAO = new MascotaDAOImpl();
        this.clienteDAO = new ClienteDAOImpl();
        this.ventaDAO = new VentaDAOImpl();
        this.consultaDAO = new ConsultaDAOImpl();
        this.accesorioDAO = new AccesorioDAOImpl();
        cargarRelaciones();
    }

    // ==================== OPERACIONES MASCOTA ====================
    
    public void registrarMascota(Mascota mascota) {
        mascotaDAO.crearMascota(mascota);
    }

    public Mascota obtenerMascota(String id) {
        return mascotaDAO.obtenerMascota(id);
    }

    public List<Mascota> obtenerMascotas() {
        return mascotaDAO.obtenerTodasMascotas();
    }

    public List<Mascota> obtenerMascotasSinAdoptar() {
        return mascotaDAO.obtenerMascotasPorEstado("SIN_ADOPTAR");
    }

    public List<Mascota> obtenerMascotasAdoptadas() {
        return mascotaDAO.obtenerMascotasPorEstado("ADOPTADO");
    }

    public void actualizarMascota(Mascota mascota) {
        mascotaDAO.actualizarMascota(mascota);
    }

    public void eliminarMascota(String id) {
        mascotaDAO.eliminarMascota(id);
    }

    public List<Mascota> obtenerMascotasPorTipo(String tipo) {
        List<Mascota> todasMascotas = mascotaDAO.obtenerTodasMascotas();
        List<Mascota> resultado = new ArrayList<>();
        for (Mascota mascota : todasMascotas) {
            if (mascota.getTipo().equalsIgnoreCase(tipo)) {
                resultado.add(mascota);
            }
        }
        return resultado;
    }

    // ==================== OPERACIONES CLIENTE ====================
    
    public void registrarCliente(Cliente cliente) {
        clienteDAO.crearCliente(cliente);
    }

    public Cliente obtenerCliente(String id) {
        return clienteDAO.obtenerCliente(id);
    }

    public List<Cliente> obtenerClientes() {
        return clienteDAO.obtenerTodosClientes();
    }

    public void actualizarCliente(Cliente cliente) {
        clienteDAO.actualizarCliente(cliente);
    }

    public void eliminarCliente(String id) {
        clienteDAO.eliminarCliente(id);
    }

    public List<Cliente> buscarClientesPorNombre(String nombre) {
        List<Cliente> todosClientes = clienteDAO.obtenerTodosClientes();
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente cliente : todosClientes) {
            if (cliente.getNombres().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(cliente);
            }
        }
        return resultado;
    }

    // ==================== OPERACIONES VENTA ====================
    
    public void registrarVenta(Venta venta) {
        // Validar que haya mascotas en la venta
        if (venta.getMascotas() == null || venta.getMascotas().isEmpty()) {
            throw new IllegalArgumentException("La venta debe incluir al menos una mascota");
        }

        // Validar que el cliente existe
        Cliente cliente = clienteDAO.obtenerCliente(venta.getCliente().getId());
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no existe");
        }

        // Actualizar estado de mascotas a ADOPTADO y asignar al cliente
        for (Mascota mascota : venta.getMascotas()) {
            Mascota mascotaExistente = mascotaDAO.obtenerMascota(mascota.getId());
            if (mascotaExistente == null) {
                throw new IllegalArgumentException("La mascota con ID " + mascota.getId() + " no existe");
            }
            
            if ("ADOPTADO".equals(mascotaExistente.getEstado())) {
                throw new IllegalArgumentException("La mascota " + mascota.getNombre() + " ya está adoptada");
            }
            
            mascotaExistente.setEstado("ADOPTADO");
            mascotaDAO.actualizarMascota(mascotaExistente);
            
            // Agregar mascota al cliente
            cliente.agregarMascota(mascotaExistente);
        }

        // Actualizar stock de accesorios si hay alguno
        if (venta.getAccesorios() != null && !venta.getAccesorios().isEmpty()) {
            for (Accesorio accesorio : venta.getAccesorios()) {
                Accesorio accesorioExistente = accesorioDAO.obtenerAccesorio(accesorio.getId());
                if (accesorioExistente != null && accesorioExistente.getStock() > 0) {
                    accesorioExistente.setStock(accesorioExistente.getStock() - 1);
                    accesorioDAO.actualizarAccesorio(accesorioExistente);
                }
            }
        }

        // Actualizar cliente y registrar venta
        clienteDAO.actualizarCliente(cliente);
        ventaDAO.crearVenta(venta);
    }

    public Venta obtenerVenta(String id) {
        return ventaDAO.obtenerVenta(id);
    }

    public List<Venta> obtenerVentas() {
        return ventaDAO.obtenerTodasVentas();
    }

    public void actualizarVenta(Venta venta) {
        ventaDAO.actualizarVenta(venta);
    }

    public void eliminarVenta(String id) {
        ventaDAO.eliminarVenta(id);
    }

    public List<Venta> obtenerVentasPorCliente(String clienteId) {
        List<Venta> todasVentas = ventaDAO.obtenerTodasVentas();
        List<Venta> resultado = new ArrayList<>();
        for (Venta venta : todasVentas) {
            if (venta.getCliente().getId().equals(clienteId)) {
                resultado.add(venta);
            }
        }
        return resultado;
    }

    public List<Venta> obtenerVentasPorFecha(Date fechaInicio, Date fechaFin) {
        List<Venta> todasVentas = ventaDAO.obtenerTodasVentas();
        List<Venta> resultado = new ArrayList<>();
        for (Venta venta : todasVentas) {
            if (!venta.getFecha().before(fechaInicio) && !venta.getFecha().after(fechaFin)) {
                resultado.add(venta);
            }
        }
        return resultado;
    }

    // ==================== OPERACIONES CONSULTA ====================
    
    public void registrarConsulta(Consulta consulta) {
        // Validar que la mascota existe
        Mascota mascota = mascotaDAO.obtenerMascota(consulta.getMascota().getId());
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no existe");
        }

        consultaDAO.crearConsulta(consulta);
        
        // Agregar consulta al historial de la mascota
        if (mascota.getHistorialConsultas() == null) {
            mascota.setHistorialConsultas(new ArrayList<>());
        }
        mascota.getHistorialConsultas().add(consulta);
        mascotaDAO.actualizarMascota(mascota);
    }

    public Consulta obtenerConsulta(String id) {
        return consultaDAO.obtenerConsulta(id);
    }

    public List<Consulta> obtenerConsultas() {
        return consultaDAO.obtenerTodasConsultas();
    }

    public void actualizarConsulta(Consulta consulta) {
        consultaDAO.actualizarConsulta(consulta);
    }

    public void eliminarConsulta(String id) {
        consultaDAO.eliminarConsulta(id);
    }

    public List<Consulta> obtenerConsultasPorMascota(String mascotaId) {
        return consultaDAO.obtenerConsultasPorMascota(mascotaId);
    }

    public List<Consulta> obtenerConsultasPorFecha(Date fecha) {
        List<Consulta> todasConsultas = consultaDAO.obtenerTodasConsultas();
        List<Consulta> resultado = new ArrayList<>();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        
        for (Consulta consulta : todasConsultas) {
            cal1.setTime(consulta.getFecha());
            cal2.setTime(fecha);
            
            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
                resultado.add(consulta);
            }
        }
        return resultado;
    }

    // ==================== OPERACIONES ACCESORIO ====================
    
    public void registrarAccesorio(Accesorio accesorio) {
        accesorioDAO.crearAccesorio(accesorio);
    }

    public Accesorio obtenerAccesorio(String id) {
        return accesorioDAO.obtenerAccesorio(id);
    }

    public List<Accesorio> obtenerAccesorios() {
        return accesorioDAO.obtenerTodosAccesorios();
    }

    public void actualizarAccesorio(Accesorio accesorio) {
        accesorioDAO.actualizarAccesorio(accesorio);
    }

    public void eliminarAccesorio(String id) {
        accesorioDAO.eliminarAccesorio(id);
    }

    public List<Accesorio> obtenerAccesoriosPorTipo(String tipo) {
        List<Accesorio> todosAccesorios = accesorioDAO.obtenerTodosAccesorios();
        List<Accesorio> resultado = new ArrayList<>();
        for (Accesorio accesorio : todosAccesorios) {
            if (accesorio.getTipo().equalsIgnoreCase(tipo)) {
                resultado.add(accesorio);
            }
        }
        return resultado;
    }

    public List<Accesorio> obtenerAccesoriosConStock() {
        List<Accesorio> todosAccesorios = accesorioDAO.obtenerTodosAccesorios();
        List<Accesorio> resultado = new ArrayList<>();
        for (Accesorio accesorio : todosAccesorios) {
            if (accesorio.getStock() > 0) {
                resultado.add(accesorio);
            }
        }
        return resultado;
    }

    // ==================== REPORTES Y CONSULTAS ESPECIALES ====================
    
    public Map<Cliente, List<Mascota>> generarReporteMascotasPorCliente() {
        Map<Cliente, List<Mascota>> reporte = new HashMap<>();
        List<Cliente> clientes = clienteDAO.obtenerTodosClientes();
        
        for (Cliente cliente : clientes) {
            reporte.put(cliente, cliente.getMascotasACargo());
        }
        return reporte;
    }

    public Map<Mascota, Map<Cliente, List<Consulta>>> generarReporteCompletoMascotas() {
        Map<Mascota, Map<Cliente, List<Consulta>>> reporte = new HashMap<>();
        List<Mascota> mascotas = mascotaDAO.obtenerTodasMascotas();
        
        for (Mascota mascota : mascotas) {
            Map<Cliente, List<Consulta>> infoMascota = new HashMap<>();
            Cliente clienteDueño = encontrarClienteDeMascota(mascota);
            List<Consulta> consultas = consultaDAO.obtenerConsultasPorMascota(mascota.getId());
            
            infoMascota.put(clienteDueño, consultas);
            reporte.put(mascota, infoMascota);
        }
        return reporte;
    }

    public Map<String, Integer> generarEstadisticasMascotas() {
        Map<String, Integer> estadisticas = new HashMap<>();
        List<Mascota> todasMascotas = mascotaDAO.obtenerTodasMascotas();
        
        int totalMascotas = todasMascotas.size();
        int mascotasAdoptadas = obtenerMascotasAdoptadas().size();
        int mascotasSinAdoptar = obtenerMascotasSinAdoptar().size();
        
        // Contar por tipo
        Map<String, Integer> porTipo = new HashMap<>();
        for (Mascota mascota : todasMascotas) {
            String tipo = mascota.getTipo();
            porTipo.put(tipo, porTipo.getOrDefault(tipo, 0) + 1);
        }
        
        estadisticas.put("Total Mascotas", totalMascotas);
        estadisticas.put("Mascotas Adoptadas", mascotasAdoptadas);
        estadisticas.put("Mascotas Sin Adoptar", mascotasSinAdoptar);
        estadisticas.putAll(porTipo);
        
        return estadisticas;
    }

    public double calcularIngresosTotales() {
        List<Venta> todasVentas = ventaDAO.obtenerTodasVentas();
        double total = 0;
        for (Venta venta : todasVentas) {
            total += venta.getPrecio();
        }
        return total;
    }

    public Map<Cliente, Double> generarReporteGastosPorCliente() {
        Map<Cliente, Double> reporte = new HashMap<>();
        List<Cliente> clientes = clienteDAO.obtenerTodosClientes();
        
        for (Cliente cliente : clientes) {
            double gastoTotal = 0;
            List<Venta> ventasCliente = obtenerVentasPorCliente(cliente.getId());
            
            for (Venta venta : ventasCliente) {
                gastoTotal += venta.getPrecio();
            }
            
            reporte.put(cliente, gastoTotal);
        }
        return reporte;
    }

    // ==================== MÉTODOS DE CARGA DE RELACIONES ====================
    
    private void cargarRelaciones() {
        cargarMascotasEnClientes();
        cargarRelacionesEnVentas();
        cargarRelacionesEnConsultas();
    }

    private void cargarMascotasEnClientes() {
        // Las mascotas se cargan automáticamente desde las ventas
    }

    private void cargarRelacionesEnVentas() {
        List<Venta> ventas = ventaDAO.obtenerTodasVentas();
        for (Venta venta : ventas) {
            // Las relaciones se cargan automáticamente desde los archivos
            // en las implementaciones DAO
        }
    }

    private void cargarRelacionesEnConsultas() {
        List<Consulta> consultas = consultaDAO.obtenerTodasConsultas();
        for (Consulta consulta : consultas) {
            // Las relaciones se cargan automáticamente desde los archivos
            // en las implementaciones DAO
        }
    }

    // ==================== MÉTODOS PRIVADOS AUXILIARES ====================
    
    private Cliente encontrarClienteDeMascota(Mascota mascota) {
        for (Cliente cliente : clienteDAO.obtenerTodosClientes()) {
            for (Mascota mascotaCliente : cliente.getMascotasACargo()) {
                if (mascotaCliente.getId().equals(mascota.getId())) {
                    return cliente;
                }
            }
        }
        return null;
    }

    // ==================== MÉTODOS DE VALIDACIÓN ====================
    
    public boolean existeMascota(String id) {
        return mascotaDAO.obtenerMascota(id) != null;
    }

    public boolean existeCliente(String id) {
        return clienteDAO.obtenerCliente(id) != null;
    }

    public boolean existeAccesorio(String id) {
        return accesorioDAO.obtenerAccesorio(id) != null;
    }

    public boolean existeConsulta(String id) {
        return consultaDAO.obtenerConsulta(id) != null;
    }

    public boolean existeVenta(String id) {
        return ventaDAO.obtenerVenta(id) != null;
    }

    // ==================== MÉTODOS DE BÚSQUEDA AVANZADA ====================
    
    public List<Mascota> buscarMascotas(String criterio) {
        List<Mascota> todasMascotas = mascotaDAO.obtenerTodasMascotas();
        List<Mascota> resultado = new ArrayList<>();
        
        for (Mascota mascota : todasMascotas) {
            if (mascota.getNombre().toLowerCase().contains(criterio.toLowerCase()) ||
                mascota.getRaza().toLowerCase().contains(criterio.toLowerCase()) ||
                mascota.getTipo().toLowerCase().contains(criterio.toLowerCase()) ||
                mascota.getId().toLowerCase().contains(criterio.toLowerCase())) {
                resultado.add(mascota);
            }
        }
        return resultado;
    }

    public List<Cliente> buscarClientes(String criterio) {
        List<Cliente> todosClientes = clienteDAO.obtenerTodosClientes();
        List<Cliente> resultado = new ArrayList<>();
        
        for (Cliente cliente : todosClientes) {
            if (cliente.getNombres().toLowerCase().contains(criterio.toLowerCase()) ||
                cliente.getContacto().toLowerCase().contains(criterio.toLowerCase()) ||
                cliente.getNumeroContacto().contains(criterio) ||
                cliente.getId().toLowerCase().contains(criterio.toLowerCase())) {
                resultado.add(cliente);
            }
        }
        return resultado;
    }

    // ==================== MÉTODOS DE ESTADO DEL SISTEMA ====================
    
    public Map<String, Integer> obtenerEstadisticasGenerales() {
        Map<String, Integer> stats = new HashMap<>();
        
        stats.put("Total Mascotas", mascotaDAO.obtenerTodasMascotas().size());
        stats.put("Total Clientes", clienteDAO.obtenerTodosClientes().size());
        stats.put("Total Ventas", ventaDAO.obtenerTodasVentas().size());
        stats.put("Total Consultas", consultaDAO.obtenerTodasConsultas().size());
        stats.put("Total Accesorios", accesorioDAO.obtenerTodosAccesorios().size());
        stats.put("Mascotas Adoptadas", obtenerMascotasAdoptadas().size());
        stats.put("Mascotas Disponibles", obtenerMascotasSinAdoptar().size());
        
        return stats;
    }

    // ==================== MÉTODOS DE EXPORTACIÓN ====================
    
    public void exportarClientesATXT(String rutaArchivo) {
        try {
            File directorio = new File("export");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo));
            writer.write("=== REPORTE DE CLIENTES ===\n");
            writer.write("Generado: " + new Date() + "\n\n");
            
            List<Cliente> clientes = obtenerClientes();
            for (Cliente cliente : clientes) {
                writer.write("ID: " + cliente.getId() + "\n");
                writer.write("Nombres: " + cliente.getNombres() + "\n");
                writer.write("Dirección: " + cliente.getDireccion() + "\n");
                writer.write("Email: " + cliente.getContacto() + "\n");
                writer.write("Teléfono: " + cliente.getNumeroContacto() + "\n");
                writer.write("Mascotas a cargo: " + cliente.getMascotasACargo().size() + "\n");
                
                if (!cliente.getMascotasACargo().isEmpty()) {
                    writer.write("Lista de mascotas:\n");
                    for (Mascota mascota : cliente.getMascotasACargo()) {
                        writer.write("  - " + mascota.getNombre() + " (" + mascota.getTipo() + ")\n");
                    }
                }
                writer.write("----------------------------------------\n");
            }
            
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error al exportar clientes: " + e.getMessage());
        }
    }

    public void exportarMascotasATXT(String rutaArchivo) {
        try {
            File directorio = new File("export");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo));
            writer.write("=== REPORTE DE MASCOTAS ===\n");
            writer.write("Generado: " + new Date() + "\n\n");
            
            List<Mascota> mascotas = obtenerMascotas();
            for (Mascota mascota : mascotas) {
                writer.write("ID: " + mascota.getId() + "\n");
                writer.write("Nombre: " + mascota.getNombre() + "\n");
                writer.write("Tipo: " + mascota.getTipo() + "\n");
                writer.write("Raza: " + mascota.getRaza() + "\n");
                writer.write("Edad: " + mascota.getEdad() + " meses\n");
                writer.write("Peso: " + mascota.getPeso() + " kg\n");
                writer.write("Estado: " + mascota.getEstado() + "\n");
                writer.write("Fecha Ingreso: " + dateFormat.format(mascota.getFechaIngreso()) + "\n");
                writer.write("Género: " + mascota.getGenero() + "\n");
                writer.write("Lugar Origen: " + mascota.getLugarOrigen() + "\n");
                writer.write("Vacunas: " + String.join(", ", mascota.getVacunas()) + "\n");
                
                if (mascota.getHistorialConsultas() != null && !mascota.getHistorialConsultas().isEmpty()) {
                    writer.write("Consultas: " + mascota.getHistorialConsultas().size() + "\n");
                }
                writer.write("----------------------------------------\n");
            }
            
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error al exportar mascotas: " + e.getMessage());
        }
    }

    public void exportarVentasATXT(String rutaArchivo) {
        try {
            File directorio = new File("export");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo));
            writer.write("=== REPORTE DE VENTAS ===\n");
            writer.write("Generado: " + new Date() + "\n\n");
            
            List<Venta> ventas = obtenerVentas();
            for (Venta venta : ventas) {
                writer.write("ID Venta: " + venta.getId() + "\n");
                writer.write("Fecha: " + dateFormat.format(venta.getFecha()) + "\n");
                writer.write("Cliente: " + venta.getCliente().getNombres() + "\n");
                writer.write("Vendedor: " + venta.getVendedor() + "\n");
                writer.write("Precio: $" + venta.getPrecio() + "\n");
                writer.write("Mascotas: ");
                for (Mascota mascota : venta.getMascotas()) {
                    writer.write(mascota.getNombre() + " ");
                }
                writer.write("\nAccesorios: ");
                for (Accesorio accesorio : venta.getAccesorios()) {
                    writer.write(accesorio.getNombre() + " ");
                }
                writer.write("\n----------------------------------------\n");
            }
            
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error al exportar ventas: " + e.getMessage());
        }
    }

    // ==================== MÉTODOS DE INICIALIZACIÓN ====================
    
    public void inicializarDatosPrueba() {
        // Datos de prueba para mascotas
        Mascota mascota1 = new Mascota("M001", "Max", "Labrador", 12, 
            Arrays.asList("Rabia", "Moquillo"), "Perro", 25.5, "SIN_ADOPTAR", 
            new Date(), "Criadero Golden", "Macho");
        
        Mascota mascota2 = new Mascota("M002", "Luna", "Siamés", 8, 
            Arrays.asList("Rabia", "Leucemia"), "Gato", 4.2, "SIN_ADOPTAR", 
            new Date(), "Refugio Felino", "Hembra");
        
        if (!existeMascota("M001")) registrarMascota(mascota1);
        if (!existeMascota("M002")) registrarMascota(mascota2);

        // Datos de prueba para clientes
        Cliente cliente1 = new Cliente("C001", "Juan Pérez", "Calle 123", 
            "juan@email.com", "3001234567");
        
        Cliente cliente2 = new Cliente("C002", "María García", "Avenida 456", 
            "maria@email.com", "3109876543");
        
        if (!existeCliente("C001")) registrarCliente(cliente1);
        if (!existeCliente("C002")) registrarCliente(cliente2);

        // Datos de prueba para accesorios
        Accesorio accesorio1 = new Accesorio("A001", "Collar Antipulgas", "Collar", 25.0, 10);
        Accesorio accesorio2 = new Accesorio("A002", "Juguete Hueso", "Juguete", 15.0, 20);
        
        if (!existeAccesorio("A001")) registrarAccesorio(accesorio1);
        if (!existeAccesorio("A002")) registrarAccesorio(accesorio2);
    }
}
