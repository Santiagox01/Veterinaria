/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;
import controlador.VeterinariaController;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 *
 * @author XiusAM
 */
public class PanelPrincipal extends javax.swing.JPanel {
    private VeterinariaController controller;
    private JTabbedPane tabbedPane;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    // Componentes reutilizables
    private DefaultTableModel modeloMascotas;
    private DefaultTableModel modeloClientes;
    private DefaultTableModel modeloVentas;
    private DefaultTableModel modeloAccesorios;
    
    // Componentes del Dashboard
    private JLabel lblTotalMascotas, lblMascotasDisponibles, lblMascotasAdoptadas;
    private JLabel lblTotalClientes, lblTotalVentas, lblIngresosTotales;

    public PanelPrincipal() {
        this.controller = new VeterinariaController();
        inicializarComponentes();
        cargarDatosIniciales();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        // Crear barra de título
        JLabel titulo = new JLabel("Sistema de Gestión - Veterinaria", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        tabbedPane = new JTabbedPane();
        
        // Crear pestañas mejoradas
        tabbedPane.addTab("Mascotas", crearPanelMascotas());
        tabbedPane.addTab("Clientes", crearPanelClientes());
        tabbedPane.addTab("Ventas", crearPanelVentas());
        tabbedPane.addTab("Consultas", crearPanelConsultas());
        tabbedPane.addTab("Accesorios", crearPanelAccesorios());
        tabbedPane.addTab("Reportes", crearPanelReportes());
        tabbedPane.addTab("Dashboard", crearPanelDashboard());
        
        add(titulo, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelMascotas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtBusqueda = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpiarBusqueda = new JButton("Mostrar Todas");
        
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBusqueda);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnLimpiarBusqueda);

        // Formulario de registro
        JPanel panelFormulario = new JPanel(new GridLayout(0, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Nueva Mascota"));
        
        JTextField txtId = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtRaza = new JTextField();
        JSpinner spnEdad = new JSpinner(new SpinnerNumberModel(1, 1, 240, 1));
        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"Perro", "Gato", "Ave", "Roedor", "Reptil", "Pez"});
        JSpinner spnPeso = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 100.0, 0.1));
        JComboBox<String> cmbGenero = new JComboBox<>(new String[]{"Macho", "Hembra"});
        JTextField txtLugarOrigen = new JTextField();
        JTextArea txtVacunas = new JTextArea(2, 20);
        
        panelFormulario.add(new JLabel("ID *:"));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Nombre *:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Raza:"));
        panelFormulario.add(txtRaza);
        panelFormulario.add(new JLabel("Edad (meses):"));
        panelFormulario.add(spnEdad);
        panelFormulario.add(new JLabel("Tipo:"));
        panelFormulario.add(cmbTipo);
        panelFormulario.add(new JLabel("Peso (kg):"));
        panelFormulario.add(spnPeso);
        panelFormulario.add(new JLabel("Género:"));
        panelFormulario.add(cmbGenero);
        panelFormulario.add(new JLabel("Lugar Origen:"));
        panelFormulario.add(txtLugarOrigen);
        panelFormulario.add(new JLabel("Vacunas (separar por coma):"));
        panelFormulario.add(new JScrollPane(txtVacunas));

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnRegistrar = new JButton("Registrar Mascota");
        JButton btnLimpiar = new JButton("Limpiar Formulario");
        JButton btnEliminar = new JButton("Eliminar Seleccionada");
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEliminar);

        // Tabla de mascotas
        modeloMascotas = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloMascotas.addColumn("ID");
        modeloMascotas.addColumn("Nombre");
        modeloMascotas.addColumn("Tipo");
        modeloMascotas.addColumn("Raza");
        modeloMascotas.addColumn("Edad");
        modeloMascotas.addColumn("Peso");
        modeloMascotas.addColumn("Estado");
        modeloMascotas.addColumn("Fecha Ingreso");
        
        JTable tablaMascotas = new JTable(modeloMascotas);
        tablaMascotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollTabla = new JScrollPane(tablaMascotas);

        // Organizar panels
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelBusqueda, BorderLayout.NORTH);
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        panel.add(panelNorte, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);

        // Listeners
        btnRegistrar.addActionListener(e -> {
            if (validarCamposMascota(txtId, txtNombre)) {
                registrarMascota(txtId, txtNombre, txtRaza, spnEdad, cmbTipo, 
                               spnPeso, cmbGenero, txtLugarOrigen, txtVacunas);
            }
        });

        btnLimpiar.addActionListener(e -> {
            limpiarFormularioMascota(txtId, txtNombre, txtRaza, spnEdad, cmbTipo,
                                   spnPeso, cmbGenero, txtLugarOrigen, txtVacunas);
        });

        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaMascotas.getSelectedRow();
            if (filaSeleccionada >= 0) {
                String id = modeloMascotas.getValueAt(filaSeleccionada, 0).toString();
                eliminarMascota(id);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una mascota para eliminar");
            }
        });

        btnBuscar.addActionListener(e -> {
            buscarMascotas(txtBusqueda.getText());
        });

        btnLimpiarBusqueda.addActionListener(e -> {
            txtBusqueda.setText("");
            actualizarListaMascotas();
        });

        return panel;
    }

    private JPanel crearPanelClientes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtBusqueda = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpiarBusqueda = new JButton("Mostrar Todos");
        
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBusqueda);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnLimpiarBusqueda);

        // Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(0, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Cliente"));
        
        JTextField txtId = new JTextField();
        JTextField txtNombres = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtTelefono = new JTextField();
        
        panelFormulario.add(new JLabel("ID *:"));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Nombres *:"));
        panelFormulario.add(txtNombres);
        panelFormulario.add(new JLabel("Dirección:"));
        panelFormulario.add(txtDireccion);
        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(txtEmail);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnRegistrar = new JButton("Registrar Cliente");
        JButton btnLimpiar = new JButton("Limpiar Formulario");
        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEliminar);

        // Tabla
        modeloClientes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloClientes.addColumn("ID");
        modeloClientes.addColumn("Nombres");
        modeloClientes.addColumn("Dirección");
        modeloClientes.addColumn("Email");
        modeloClientes.addColumn("Teléfono");
        modeloClientes.addColumn("Mascotas");
        
        JTable tablaClientes = new JTable(modeloClientes);
        JScrollPane scrollTabla = new JScrollPane(tablaClientes);

        // Organizar
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelBusqueda, BorderLayout.NORTH);
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        panel.add(panelNorte, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);

        // Listeners
        btnRegistrar.addActionListener(e -> {
            if (validarCamposCliente(txtId, txtNombres)) {
                registrarCliente(txtId, txtNombres, txtDireccion, txtEmail, txtTelefono);
            }
        });

        btnLimpiar.addActionListener(e -> {
            limpiarFormularioCliente(txtId, txtNombres, txtDireccion, txtEmail, txtTelefono);
        });

        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaClientes.getSelectedRow();
            if (filaSeleccionada >= 0) {
                String id = modeloClientes.getValueAt(filaSeleccionada, 0).toString();
                eliminarCliente(id);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar");
            }
        });

        btnBuscar.addActionListener(e -> {
            buscarClientes(txtBusqueda.getText());
        });

        btnLimpiarBusqueda.addActionListener(e -> {
            txtBusqueda.setText("");
            actualizarListaClientes();
        });

        return panel;
    }

    private JPanel crearPanelVentas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelFormulario = new JPanel(new GridLayout(0, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Venta"));
        
        JTextField txtIdVenta = new JTextField();
        JSpinner spnPrecio = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100000.0, 100.0));
        JTextField txtVendedor = new JTextField();
        JComboBox<String> cmbCliente = new JComboBox<>();
        JComboBox<String> cmbMascotas = new JComboBox<>();
        JComboBox<String> cmbAccesorios = new JComboBox<>();
        JTextArea txtObservaciones = new JTextArea(3, 20);
        
        // Cargar combos
        actualizarCombosVenta(cmbCliente, cmbMascotas, cmbAccesorios);
        
        panelFormulario.add(new JLabel("ID Venta *:"));
        panelFormulario.add(txtIdVenta);
        panelFormulario.add(new JLabel("Precio Total *:"));
        panelFormulario.add(spnPrecio);
        panelFormulario.add(new JLabel("Vendedor *:"));
        panelFormulario.add(txtVendedor);
        panelFormulario.add(new JLabel("Cliente *:"));
        panelFormulario.add(cmbCliente);
        panelFormulario.add(new JLabel("Mascota *:"));
        panelFormulario.add(cmbMascotas);
        panelFormulario.add(new JLabel("Accesorio:"));
        panelFormulario.add(cmbAccesorios);
        panelFormulario.add(new JLabel("Observaciones:"));
        panelFormulario.add(new JScrollPane(txtObservaciones));

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnRegistrar = new JButton("Registrar Venta");
        JButton btnActualizarCombos = new JButton("Actualizar Listas");
        JButton btnLimpiar = new JButton("Limpiar");
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizarCombos);
        panelBotones.add(btnLimpiar);

        // Tabla de ventas
        modeloVentas = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloVentas.addColumn("ID");
        modeloVentas.addColumn("Fecha");
        modeloVentas.addColumn("Cliente");
        modeloVentas.addColumn("Mascota");
        modeloVentas.addColumn("Precio");
        modeloVentas.addColumn("Vendedor");
        
        JTable tablaVentas = new JTable(modeloVentas);
        JScrollPane scrollTabla = new JScrollPane(tablaVentas);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        panel.add(panelNorte, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> {
            registrarVenta(txtIdVenta, spnPrecio, txtVendedor, cmbCliente, cmbMascotas, cmbAccesorios, txtObservaciones);
        });

        btnActualizarCombos.addActionListener(e -> {
            actualizarCombosVenta(cmbCliente, cmbMascotas, cmbAccesorios);
        });

        btnLimpiar.addActionListener(e -> {
            limpiarFormularioVenta(txtIdVenta, spnPrecio, txtVendedor, txtObservaciones);
        });

        return panel;
    }

    private JPanel crearPanelConsultas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelFormulario = new JPanel(new GridLayout(0, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Consulta Médica"));
        
        JTextField txtIdConsulta = new JTextField();
        JComboBox<String> cmbMascota = new JComboBox<>();
        JTextArea txtSintomas = new JTextArea(3, 20);
        JTextArea txtDiagnostico = new JTextArea(3, 20);
        JTextArea txtTratamiento = new JTextArea(3, 20);
        
        actualizarComboMascotas(cmbMascota);
        
        panelFormulario.add(new JLabel("ID Consulta *:"));
        panelFormulario.add(txtIdConsulta);
        panelFormulario.add(new JLabel("Mascota *:"));
        panelFormulario.add(cmbMascota);
        panelFormulario.add(new JLabel("Síntomas *:"));
        panelFormulario.add(new JScrollPane(txtSintomas));
        panelFormulario.add(new JLabel("Diagnóstico:"));
        panelFormulario.add(new JScrollPane(txtDiagnostico));
        panelFormulario.add(new JLabel("Tratamiento:"));
        panelFormulario.add(new JScrollPane(txtTratamiento));

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnRegistrar = new JButton("Registrar Consulta");
        JButton btnLimpiar = new JButton("Limpiar");
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnLimpiar);

        panel.add(panelFormulario, BorderLayout.NORTH);
        panel.add(panelBotones, BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> {
            registrarConsulta(txtIdConsulta, cmbMascota, txtSintomas, txtDiagnostico, txtTratamiento);
        });

        btnLimpiar.addActionListener(e -> {
            limpiarFormularioConsulta(txtIdConsulta, txtSintomas, txtDiagnostico, txtTratamiento);
        });

        return panel;
    }

    private JPanel crearPanelAccesorios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelFormulario = new JPanel(new GridLayout(0, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Accesorio"));
        
        JTextField txtId = new JTextField();
        JTextField txtNombre = new JTextField();
        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"Juguete", "Alimento", "Medicamento", "Higiene", "Otro"});
        JSpinner spnPrecio = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000.0, 0.5));
        JSpinner spnStock = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        
        panelFormulario.add(new JLabel("ID *:"));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Nombre *:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Tipo:"));
        panelFormulario.add(cmbTipo);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(spnPrecio);
        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(spnStock);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnRegistrar = new JButton("Registrar Accesorio");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEliminar);

        // Tabla
        modeloAccesorios = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloAccesorios.addColumn("ID");
        modeloAccesorios.addColumn("Nombre");
        modeloAccesorios.addColumn("Tipo");
        modeloAccesorios.addColumn("Precio");
        modeloAccesorios.addColumn("Stock");
        
        JTable tablaAccesorios = new JTable(modeloAccesorios);
        JScrollPane scrollTabla = new JScrollPane(tablaAccesorios);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        panel.add(panelNorte, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> {
            registrarAccesorio(txtId, txtNombre, cmbTipo, spnPrecio, spnStock);
        });

        btnLimpiar.addActionListener(e -> {
            limpiarFormularioAccesorio(txtId, txtNombre, cmbTipo, spnPrecio, spnStock);
        });

        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaAccesorios.getSelectedRow();
            if (filaSeleccionada >= 0) {
                String id = modeloAccesorios.getValueAt(filaSeleccionada, 0).toString();
                eliminarAccesorio(id);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un accesorio para eliminar");
            }
        });

        return panel;
    }

    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelBotonesSuperior = new JPanel(new GridLayout(2, 3, 5, 5));
        
        JButton btnReporteClientes = new JButton("Mascotas por Cliente");
        JButton btnReporteCompleto = new JButton("Historial Completo Mascotas");
        JButton btnReporteMascotasSinAdoptar = new JButton("Mascotas Disponibles");
        JButton btnReporteVentas = new JButton("Reporte de Ventas");
        JButton btnEstadisticas = new JButton("Estadísticas Generales");
        JButton btnGastosClientes = new JButton("Gastos por Cliente");

        panelBotonesSuperior.add(btnReporteClientes);
        panelBotonesSuperior.add(btnReporteCompleto);
        panelBotonesSuperior.add(btnReporteMascotasSinAdoptar);
        panelBotonesSuperior.add(btnReporteVentas);
        panelBotonesSuperior.add(btnEstadisticas);
        panelBotonesSuperior.add(btnGastosClientes);

        JPanel panelBotonesInferior = new JPanel(new FlowLayout());
        JButton btnExportarClientes = new JButton("Exportar Clientes a TXT");
        JButton btnExportarMascotas = new JButton("Exportar Mascotas a TXT");
        JButton btnExportarVentas = new JButton("Exportar Ventas a TXT");
        
        panelBotonesInferior.add(btnExportarClientes);
        panelBotonesInferior.add(btnExportarMascotas);
        panelBotonesInferior.add(btnExportarVentas);

        JTextArea areaReporte = new JTextArea(20, 60);
        areaReporte.setEditable(false);
        areaReporte.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollReporte = new JScrollPane(areaReporte);

        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.add(panelBotonesSuperior, BorderLayout.NORTH);
        panelBotones.add(panelBotonesInferior, BorderLayout.SOUTH);

        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollReporte, BorderLayout.CENTER);

        // Listeners para reportes
        btnReporteClientes.addActionListener(e -> generarReporteMascotasPorCliente(areaReporte));
        btnReporteCompleto.addActionListener(e -> generarReporteCompletoMascotas(areaReporte));
        btnReporteMascotasSinAdoptar.addActionListener(e -> generarReporteMascotasDisponibles(areaReporte));
        btnReporteVentas.addActionListener(e -> generarReporteVentas(areaReporte));
        btnEstadisticas.addActionListener(e -> generarEstadisticasGenerales(areaReporte));
        btnGastosClientes.addActionListener(e -> generarReporteGastosClientes(areaReporte));

        // Listeners para exportación
        btnExportarClientes.addActionListener(e -> exportarClientesATXT());
        btnExportarMascotas.addActionListener(e -> exportarMascotasATXT());
        btnExportarVentas.addActionListener(e -> exportarVentasATXT());

        return panel;
    }

    private JPanel crearPanelDashboard() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de estadísticas
        JPanel panelEstadisticas = new JPanel(new GridLayout(2, 3, 10, 10));
        
        lblTotalMascotas = crearTarjetaEstadistica("Total Mascotas", "0", Color.BLUE);
        lblMascotasDisponibles = crearTarjetaEstadistica("Mascotas Disponibles", "0", Color.GREEN);
        lblMascotasAdoptadas = crearTarjetaEstadistica("Mascotas Adoptadas", "0", Color.ORANGE);
        lblTotalClientes = crearTarjetaEstadistica("Total Clientes", "0", Color.RED);
        lblTotalVentas = crearTarjetaEstadistica("Total Ventas", "0", Color.MAGENTA);
        lblIngresosTotales = crearTarjetaEstadistica("Ingresos Totales", "$0", Color.CYAN);

        panelEstadisticas.add(lblTotalMascotas);
        panelEstadisticas.add(lblMascotasDisponibles);
        panelEstadisticas.add(lblMascotasAdoptadas);
        panelEstadisticas.add(lblTotalClientes);
        panelEstadisticas.add(lblTotalVentas);
        panelEstadisticas.add(lblIngresosTotales);

        // Botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnActualizar = new JButton("Actualizar Dashboard");
        JButton btnDatosPrueba = new JButton("Cargar Datos de Prueba");
        
        panelBotones.add(btnActualizar);
        panelBotones.add(btnDatosPrueba);

        panel.add(panelEstadisticas, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        // Listeners
        btnActualizar.addActionListener(e -> actualizarDashboard());
        btnDatosPrueba.addActionListener(e -> cargarDatosPrueba());

        // Actualizar dashboard inicial
        actualizarDashboard();

        return panel;
    }

    private JLabel crearTarjetaEstadistica(String titulo, String valor, Color color) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBorder(BorderFactory.createLineBorder(color, 2));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setPreferredSize(new Dimension(150, 80));

        JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 12));
        lblTitulo.setForeground(color);

        JLabel lblValor = new JLabel(valor, JLabel.CENTER);
        lblValor.setFont(new Font("Arial", Font.BOLD, 18));
        lblValor.setForeground(color);

        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(lblValor, BorderLayout.CENTER);

        // Devolvemos solo el label del valor para poder actualizarlo
        return lblValor;
    }

    // ============ MÉTODOS DE NEGOCIO MEJORADOS ============

    private void registrarMascota(JTextField txtId, JTextField txtNombre, JTextField txtRaza,
                                 JSpinner spnEdad, JComboBox<String> cmbTipo, JSpinner spnPeso,
                                 JComboBox<String> cmbGenero, JTextField txtLugarOrigen, JTextArea txtVacunas) {
        try {
            Mascota mascota = new Mascota();
            mascota.setId(txtId.getText().trim());
            mascota.setNombre(txtNombre.getText().trim());
            mascota.setRaza(txtRaza.getText().trim());
            mascota.setEdad((Integer) spnEdad.getValue());
            mascota.setTipo(cmbTipo.getSelectedItem().toString());
            mascota.setPeso((Double) spnPeso.getValue());
            mascota.setEstado("SIN_ADOPTAR");
            mascota.setFechaIngreso(new Date());
            mascota.setLugarOrigen(txtLugarOrigen.getText().trim());
            mascota.setGenero(cmbGenero.getSelectedItem().toString());
            
            // Procesar vacunas
            String vacunasText = txtVacunas.getText().trim();
            if (!vacunasText.isEmpty()) {
                List<String> vacunas = new ArrayList<>();
                for (String vacuna : vacunasText.split(",")) {
                    vacunas.add(vacuna.trim());
                }
                mascota.setVacunas(vacunas);
            } else {
                mascota.setVacunas(new ArrayList<>());
            }
            
            controller.registrarMascota(mascota);
            JOptionPane.showMessageDialog(this, "Mascota registrada exitosamente");
            limpiarFormularioMascota(txtId, txtNombre, txtRaza, spnEdad, cmbTipo, spnPeso, cmbGenero, txtLugarOrigen, txtVacunas);
            actualizarListaMascotas();
            actualizarDashboard();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar mascota: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarCliente(JTextField txtId, JTextField txtNombres, JTextField txtDireccion,
                                JTextField txtEmail, JTextField txtTelefono) {
        try {
            Cliente cliente = new Cliente();
            cliente.setId(txtId.getText().trim());
            cliente.setNombres(txtNombres.getText().trim());
            cliente.setDireccion(txtDireccion.getText().trim());
            cliente.setContacto(txtEmail.getText().trim());
            cliente.setNumeroContacto(txtTelefono.getText().trim());
            
            controller.registrarCliente(cliente);
            JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente");
            actualizarListaClientes();
            actualizarDashboard();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarVenta(JTextField txtIdVenta, JSpinner spnPrecio, JTextField txtVendedor,
                               JComboBox<String> cmbCliente, JComboBox<String> cmbMascotas, 
                               JComboBox<String> cmbAccesorios, JTextArea txtObservaciones) {
        try {
            if (cmbCliente.getSelectedIndex() < 0 || cmbMascotas.getSelectedIndex() < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente y una mascota", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (txtIdVenta.getText().trim().isEmpty() || txtVendedor.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Venta y Vendedor son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener datos seleccionados
            String clienteId = cmbCliente.getSelectedItem().toString().split(" - ")[0];
            String mascotaId = cmbMascotas.getSelectedItem().toString().split(" - ")[0];
            
            Cliente cliente = controller.obtenerCliente(clienteId);
            Mascota mascota = controller.obtenerMascota(mascotaId);
            
            List<Mascota> mascotas = new ArrayList<>();
            mascotas.add(mascota);
            
            List<Accesorio> accesorios = new ArrayList<>();
            if (cmbAccesorios.getSelectedIndex() > 0) {
                String accesorioId = cmbAccesorios.getSelectedItem().toString().split(" - ")[0];
                Accesorio accesorio = controller.obtenerAccesorio(accesorioId);
                if (accesorio != null) {
                    accesorios.add(accesorio);
                }
            }

            Venta venta = new Venta();
            venta.setId(txtIdVenta.getText().trim());
            venta.setFecha(new Date());
            venta.setPrecio((Double) spnPrecio.getValue());
            venta.setCliente(cliente);
            venta.setVendedor(txtVendedor.getText().trim());
            venta.setMascotas(mascotas);
            venta.setAccesorios(accesorios);

            controller.registrarVenta(venta);
            JOptionPane.showMessageDialog(this, "Venta registrada exitosamente");
            actualizarListaVentas();
            actualizarDashboard();
            actualizarCombosVenta(cmbCliente, cmbMascotas, cmbAccesorios);
            limpiarFormularioVenta(txtIdVenta, spnPrecio, txtVendedor, txtObservaciones);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarConsulta(JTextField txtIdConsulta, JComboBox<String> cmbMascota, 
                                  JTextArea txtSintomas, JTextArea txtDiagnostico, JTextArea txtTratamiento) {
        try {
            if (txtIdConsulta.getText().trim().isEmpty() || txtSintomas.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Consulta y Síntomas son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cmbMascota.getSelectedIndex() < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una mascota", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String mascotaId = cmbMascota.getSelectedItem().toString().split(" - ")[0];
            Mascota mascota = controller.obtenerMascota(mascotaId);
            
            Consulta consulta = new Consulta();
            consulta.setId(txtIdConsulta.getText().trim());
            consulta.setFecha(new Date());
            consulta.setSintomas(txtSintomas.getText().trim());
            consulta.setDiagnostico(txtDiagnostico.getText().trim());
            consulta.setTratamiento(txtTratamiento.getText().trim());
            consulta.setMascota(mascota);

            controller.registrarConsulta(consulta);
            JOptionPane.showMessageDialog(this, "Consulta registrada exitosamente");
            limpiarFormularioConsulta(txtIdConsulta, txtSintomas, txtDiagnostico, txtTratamiento);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar consulta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarAccesorio(JTextField txtId, JTextField txtNombre, JComboBox<String> cmbTipo,
                                   JSpinner spnPrecio, JSpinner spnStock) {
        try {
            if (txtId.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID y Nombre son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Accesorio accesorio = new Accesorio();
            accesorio.setId(txtId.getText().trim());
            accesorio.setNombre(txtNombre.getText().trim());
            accesorio.setTipo(cmbTipo.getSelectedItem().toString());
            accesorio.setPrecio((Double) spnPrecio.getValue());
            accesorio.setStock((Integer) spnStock.getValue());

            controller.registrarAccesorio(accesorio);
            JOptionPane.showMessageDialog(this, "Accesorio registrado exitosamente");
            actualizarListaAccesorios();
            actualizarDashboard();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar accesorio: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ============ MÉTODOS DE ELIMINACIÓN ============

    private void eliminarMascota(String id) {
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar la mascota con ID: " + id + "?", 
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                controller.eliminarMascota(id);
                JOptionPane.showMessageDialog(this, "Mascota eliminada exitosamente");
                actualizarListaMascotas();
                actualizarDashboard();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar mascota: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarCliente(String id) {
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar el cliente con ID: " + id + "?", 
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                controller.eliminarCliente(id);
                JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente");
                actualizarListaClientes();
                actualizarDashboard();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarAccesorio(String id) {
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar el accesorio con ID: " + id + "?", 
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                controller.eliminarAccesorio(id);
                JOptionPane.showMessageDialog(this, "Accesorio eliminado exitosamente");
                actualizarListaAccesorios();
                actualizarDashboard();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar accesorio: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ============ MÉTODOS DE BÚSQUEDA ============

    private void buscarMascotas(String criterio) {
        if (criterio.isEmpty()) {
            actualizarListaMascotas();
            return;
        }

        modeloMascotas.setRowCount(0);
        List<Mascota> mascotas = controller.buscarMascotas(criterio);
        for (Mascota m : mascotas) {
            modeloMascotas.addRow(new Object[]{
                m.getId(), m.getNombre(), m.getTipo(), m.getRaza(), 
                m.getEdad(), m.getPeso(), m.getEstado(), dateFormat.format(m.getFechaIngreso())
            });
        }
    }

    private void buscarClientes(String criterio) {
        if (criterio.isEmpty()) {
            actualizarListaClientes();
            return;
        }

        modeloClientes.setRowCount(0);
        List<Cliente> clientes = controller.buscarClientes(criterio);
        for (Cliente c : clientes) {
            modeloClientes.addRow(new Object[]{
                c.getId(), c.getNombres(), c.getDireccion(), 
                c.getContacto(), c.getNumeroContacto(), c.getMascotasACargo().size()
            });
        }
    }

    // ============ MÉTODOS DE VALIDACIÓN ============

    private boolean validarCamposMascota(JTextField txtId, JTextField txtNombre) {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El ID es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtId.requestFocus();
            return false;
        }
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }
        if (controller.existeMascota(txtId.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Ya existe una mascota con este ID", "Error", JOptionPane.ERROR_MESSAGE);
            txtId.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validarCamposCliente(JTextField txtId, JTextField txtNombres) {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El ID es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtId.requestFocus();
            return false;
        }
        if (txtNombres.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los nombres son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombres.requestFocus();
            return false;
        }
        if (controller.existeCliente(txtId.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Ya existe un cliente con este ID", "Error", JOptionPane.ERROR_MESSAGE);
            txtId.requestFocus();
            return false;
        }
        return true;
    }

    // ============ MÉTODOS DE LIMPIEZA ============

    private void limpiarFormularioMascota(JTextField txtId, JTextField txtNombre, JTextField txtRaza,
                                        JSpinner spnEdad, JComboBox<String> cmbTipo, JSpinner spnPeso,
                                        JComboBox<String> cmbGenero, JTextField txtLugarOrigen, JTextArea txtVacunas) {
        txtId.setText("");
        txtNombre.setText("");
        txtRaza.setText("");
        spnEdad.setValue(1);
        cmbTipo.setSelectedIndex(0);
        spnPeso.setValue(1.0);
        cmbGenero.setSelectedIndex(0);
        txtLugarOrigen.setText("");
        txtVacunas.setText("");
    }

    private void limpiarFormularioCliente(JTextField txtId, JTextField txtNombres, JTextField txtDireccion,
                                        JTextField txtEmail, JTextField txtTelefono) {
        txtId.setText("");
        txtNombres.setText("");
        txtDireccion.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
    }

    private void limpiarFormularioVenta(JTextField txtIdVenta, JSpinner spnPrecio, JTextField txtVendedor, JTextArea txtObservaciones) {
        txtIdVenta.setText("");
        spnPrecio.setValue(0.0);
        txtVendedor.setText("");
        txtObservaciones.setText("");
    }

    private void limpiarFormularioConsulta(JTextField txtIdConsulta, JTextArea txtSintomas, 
                                         JTextArea txtDiagnostico, JTextArea txtTratamiento) {
        txtIdConsulta.setText("");
        txtSintomas.setText("");
        txtDiagnostico.setText("");
        txtTratamiento.setText("");
    }

    private void limpiarFormularioAccesorio(JTextField txtId, JTextField txtNombre, JComboBox<String> cmbTipo,
                                          JSpinner spnPrecio, JSpinner spnStock) {
        txtId.setText("");
        txtNombre.setText("");
        cmbTipo.setSelectedIndex(0);
        spnPrecio.setValue(0.0);
        spnStock.setValue(0);
    }

    // ============ MÉTODOS DE ACTUALIZACIÓN ============

    private void actualizarListaMascotas() {
        modeloMascotas.setRowCount(0);
        List<Mascota> mascotas = controller.obtenerMascotas();
        for (Mascota m : mascotas) {
            modeloMascotas.addRow(new Object[]{
                m.getId(), m.getNombre(), m.getTipo(), m.getRaza(), 
                m.getEdad(), m.getPeso(), m.getEstado(), dateFormat.format(m.getFechaIngreso())
            });
        }
    }

    private void actualizarListaClientes() {
        modeloClientes.setRowCount(0);
        List<Cliente> clientes = controller.obtenerClientes();
        for (Cliente c : clientes) {
            modeloClientes.addRow(new Object[]{
                c.getId(), c.getNombres(), c.getDireccion(), 
                c.getContacto(), c.getNumeroContacto(), c.getMascotasACargo().size()
            });
        }
    }

    private void actualizarListaVentas() {
        modeloVentas.setRowCount(0);
        List<Venta> ventas = controller.obtenerVentas();
        for (Venta v : ventas) {
            String mascotas = "";
            if (!v.getMascotas().isEmpty()) {
                mascotas = v.getMascotas().get(0).getNombre();
            }
            modeloVentas.addRow(new Object[]{
                v.getId(), dateFormat.format(v.getFecha()), v.getCliente().getNombres(),
                mascotas, v.getPrecio(), v.getVendedor()
            });
        }
    }

    private void actualizarListaAccesorios() {
        modeloAccesorios.setRowCount(0);
        List<Accesorio> accesorios = controller.obtenerAccesorios();
        for (Accesorio a : accesorios) {
            modeloAccesorios.addRow(new Object[]{
                a.getId(), a.getNombre(), a.getTipo(), a.getPrecio(), a.getStock()
            });
        }
    }

    private void actualizarCombosVenta(JComboBox<String> cmbCliente, JComboBox<String> cmbMascotas, JComboBox<String> cmbAccesorios) {
        cmbCliente.removeAllItems();
        cmbMascotas.removeAllItems();
        cmbAccesorios.removeAllItems();
        
        // Clientes
        for (Cliente cliente : controller.obtenerClientes()) {
            cmbCliente.addItem(cliente.getId() + " - " + cliente.getNombres());
        }
        
        // Mascotas sin adoptar
        for (Mascota mascota : controller.obtenerMascotasSinAdoptar()) {
            cmbMascotas.addItem(mascota.getId() + " - " + mascota.getNombre() + " (" + mascota.getTipo() + ")");
        }
        
        // Accesorios con stock
        cmbAccesorios.addItem("Ninguno");
        for (Accesorio accesorio : controller.obtenerAccesoriosConStock()) {
            cmbAccesorios.addItem(accesorio.getId() + " - " + accesorio.getNombre() + " ($" + accesorio.getPrecio() + ")");
        }
    }

    private void actualizarComboMascotas(JComboBox<String> cmbMascota) {
        cmbMascota.removeAllItems();
        for (Mascota mascota : controller.obtenerMascotas()) {
            cmbMascota.addItem(mascota.getId() + " - " + mascota.getNombre() + " (" + mascota.getTipo() + ")");
        }
    }

    private void actualizarDashboard() {
        Map<String, Integer> stats = controller.obtenerEstadisticasGenerales();
        double ingresos = controller.calcularIngresosTotales();
        
        lblTotalMascotas.setText(String.valueOf(stats.get("Total Mascotas")));
        lblMascotasDisponibles.setText(String.valueOf(stats.get("Mascotas Disponibles")));
        lblMascotasAdoptadas.setText(String.valueOf(stats.get("Mascotas Adoptadas")));
        lblTotalClientes.setText(String.valueOf(stats.get("Total Clientes")));
        lblTotalVentas.setText(String.valueOf(stats.get("Total Ventas")));
        lblIngresosTotales.setText("$" + String.format("%.2f", ingresos));
    }

    // ============ MÉTODOS DE CARGA INICIAL ============

    private void cargarDatosIniciales() {
        // Cargar datos en las tablas
        actualizarListaMascotas();
        actualizarListaClientes();
        actualizarListaVentas();
        actualizarListaAccesorios();
        actualizarDashboard();
    }

    private void cargarDatosPrueba() {
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de cargar datos de prueba? Esto agregará mascotas, clientes y accesorios de ejemplo.", 
            "Cargar Datos de Prueba", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                controller.inicializarDatosPrueba();
                JOptionPane.showMessageDialog(this, "Datos de prueba cargados exitosamente");
                cargarDatosIniciales();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar datos de prueba: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ============ MÉTODOS DE GENERACIÓN DE REPORTES ============

    private void generarReporteMascotasPorCliente(JTextArea areaReporte) {
        Map<Cliente, List<Mascota>> reporte = controller.generarReporteMascotasPorCliente();
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE MASCOTAS POR CLIENTE ===\n\n");
        
        for (Map.Entry<Cliente, List<Mascota>> entry : reporte.entrySet()) {
            Cliente cliente = entry.getKey();
            List<Mascota> mascotas = entry.getValue();
            
            sb.append("CLIENTE: ").append(cliente.getNombres()).append("\n");
            sb.append("Teléfono: ").append(cliente.getNumeroContacto()).append("\n");
            sb.append("Email: ").append(cliente.getContacto()).append("\n");
            sb.append("Mascotas a cargo (").append(mascotas.size()).append("):\n");
            
            if (mascotas.isEmpty()) {
                sb.append("  - No tiene mascotas\n");
            } else {
                for (Mascota mascota : mascotas) {
                    sb.append("  - ").append(mascota.getNombre())
                      .append(" (").append(mascota.getTipo()).append(" - ")
                      .append(mascota.getRaza()).append(") - Edad: ")
                      .append(mascota.getEdad()).append(" meses\n");
                }
            }
            sb.append("\n").append("-".repeat(50)).append("\n\n");
        }
        
        areaReporte.setText(sb.toString());
    }

    private void generarReporteCompletoMascotas(JTextArea areaReporte) {
        Map<Mascota, Map<Cliente, List<Consulta>>> reporte = controller.generarReporteCompletoMascotas();
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE COMPLETO MASCOTAS ===\n\n");
        
        for (Map.Entry<Mascota, Map<Cliente, List<Consulta>>> entry : reporte.entrySet()) {
            Mascota mascota = entry.getKey();
            Map<Cliente, List<Consulta>> info = entry.getValue();
            
            sb.append("MASCOTA: ").append(mascota.getNombre()).append("\n");
            sb.append("ID: ").append(mascota.getId()).append(" | ");
            sb.append("Tipo: ").append(mascota.getTipo()).append(" | ");
            sb.append("Raza: ").append(mascota.getRaza()).append(" | ");
            sb.append("Edad: ").append(mascota.getEdad()).append(" meses | ");
            sb.append("Estado: ").append(mascota.getEstado()).append("\n");
            
            for (Map.Entry<Cliente, List<Consulta>> infoEntry : info.entrySet()) {
                Cliente cliente = infoEntry.getKey();
                List<Consulta> consultas = infoEntry.getValue();
                
                sb.append("Dueño: ").append(cliente != null ? cliente.getNombres() : "Sin dueño").append("\n");
                sb.append("Historial de Consultas (").append(consultas.size()).append("):\n");
                
                if (consultas.isEmpty()) {
                    sb.append("  - Sin consultas registradas\n");
                } else {
                    for (Consulta consulta : consultas) {
                        sb.append("  - Fecha: ").append(dateFormat.format(consulta.getFecha()))
                          .append(" | Síntomas: ").append(consulta.getSintomas())
                          .append(" | Tratamiento: ").append(consulta.getTratamiento()).append("\n");
                    }
                }
            }
            sb.append("\n").append("-".repeat(50)).append("\n\n");
        }
        
        areaReporte.setText(sb.toString());
    }

    private void generarReporteMascotasDisponibles(JTextArea areaReporte) {
        List<Mascota> mascotas = controller.obtenerMascotasSinAdoptar();
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== MASCOTAS DISPONIBLES ===\n\n");
        sb.append("Total: ").append(mascotas.size()).append(" mascotas\n\n");
        
        for (Mascota mascota : mascotas) {
            sb.append("ID: ").append(mascota.getId()).append(" | ");
            sb.append("Nombre: ").append(mascota.getNombre()).append(" | ");
            sb.append("Tipo: ").append(mascota.getTipo()).append(" | ");
            sb.append("Raza: ").append(mascota.getRaza()).append(" | ");
            sb.append("Edad: ").append(mascota.getEdad()).append(" meses | ");
            sb.append("Peso: ").append(mascota.getPeso()).append(" kg\n");
        }
        
        areaReporte.setText(sb.toString());
    }

    private void generarReporteVentas(JTextArea areaReporte) {
        List<Venta> ventas = controller.obtenerVentas();
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE DE VENTAS ===\n\n");
        sb.append("Total ventas: ").append(ventas.size()).append("\n");
        sb.append("Ingresos totales: $").append(String.format("%.2f", controller.calcularIngresosTotales())).append("\n\n");
        
        for (Venta venta : ventas) {
            sb.append("ID: ").append(venta.getId()).append(" | ");
            sb.append("Fecha: ").append(dateFormat.format(venta.getFecha())).append(" | ");
            sb.append("Cliente: ").append(venta.getCliente().getNombres()).append(" | ");
            sb.append("Vendedor: ").append(venta.getVendedor()).append(" | ");
            sb.append("Precio: $").append(venta.getPrecio()).append("\n");
        }
        
        areaReporte.setText(sb.toString());
    }

    private void generarEstadisticasGenerales(JTextArea areaReporte) {
        Map<String, Integer> stats = controller.obtenerEstadisticasGenerales();
        Map<String, Integer> statsMascotas = controller.generarEstadisticasMascotas();
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADÍSTICAS GENERALES ===\n\n");
        
        sb.append("RESUMEN DEL SISTEMA:\n");
        sb.append("Total Mascotas: ").append(stats.get("Total Mascotas")).append("\n");
        sb.append("Mascotas Adoptadas: ").append(stats.get("Mascotas Adoptadas")).append("\n");
        sb.append("Mascotas Disponibles: ").append(stats.get("Mascotas Disponibles")).append("\n");
        sb.append("Total Clientes: ").append(stats.get("Total Clientes")).append("\n");
        sb.append("Total Ventas: ").append(stats.get("Total Ventas")).append("\n");
        sb.append("Total Consultas: ").append(stats.get("Total Consultas")).append("\n");
        sb.append("Total Accesorios: ").append(stats.get("Total Accesorios")).append("\n");
        sb.append("Ingresos Totales: $").append(String.format("%.2f", controller.calcularIngresosTotales())).append("\n\n");
        
        sb.append("DISTRIBUCIÓN POR TIPO DE MASCOTA:\n");
        for (Map.Entry<String, Integer> entry : statsMascotas.entrySet()) {
            if (!entry.getKey().startsWith("Total") && !entry.getKey().startsWith("Mascotas")) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        }
        
        areaReporte.setText(sb.toString());
    }

    private void generarReporteGastosClientes(JTextArea areaReporte) {
        Map<Cliente, Double> reporte = controller.generarReporteGastosPorCliente();
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== GASTOS POR CLIENTE ===\n\n");
        
        for (Map.Entry<Cliente, Double> entry : reporte.entrySet()) {
            Cliente cliente = entry.getKey();
            double gasto = entry.getValue();
            
            sb.append("Cliente: ").append(cliente.getNombres()).append("\n");
            sb.append("Email: ").append(cliente.getContacto()).append("\n");
            sb.append("Total gastado: $").append(String.format("%.2f", gasto)).append("\n");
            sb.append("Mascotas: ").append(cliente.getMascotasACargo().size()).append("\n");
            sb.append("-".repeat(40)).append("\n");
        }
        
        areaReporte.setText(sb.toString());
    }

    // ============ MÉTODOS DE EXPORTACIÓN ============

    private void exportarClientesATXT() {
        try {
            controller.exportarClientesATXT("export/clientes_export.txt");
            JOptionPane.showMessageDialog(this, "Clientes exportados exitosamente a export/clientes_export.txt");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al exportar clientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportarMascotasATXT() {
        try {
            controller.exportarMascotasATXT("export/mascotas_export.txt");
            JOptionPane.showMessageDialog(this, "Mascotas exportadas exitosamente a export/mascotas_export.txt");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al exportar mascotas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportarVentasATXT() {
        try {
            controller.exportarVentasATXT("export/ventas_export.txt");
            JOptionPane.showMessageDialog(this, "Ventas exportadas exitosamente a export/ventas_export.txt");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al exportar ventas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
