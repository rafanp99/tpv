package programa;

import logger.LogFactory;
import paneles.PanelLateral;
import paneles.PanelProductos;
import paneles.PanelSuperiorFechaHora;
import productos.CategoriaProducto;
import productos.Producto;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class TPVCopisteria {
    private static final Logger LOGGER = LogFactory.getLogger(TPVCopisteria.class.getName());
    private final PanelSuperiorFechaHora panelSuperiorFechaHora;
    private final PanelProductos panelProductos;
    private final PanelLateral panelLateral;

    public JPanel getPanelSuperiorFechaHora() {
        return panelSuperiorFechaHora.getPanel();
    }

    public JPanel getPanelProductos() {
        return panelProductos.getPanel();
    }

    public JPanel getPanelLateral() {
        return panelLateral.getPanel();
    }

    public TPVCopisteria() throws IOException {
        panelLateral = new PanelLateral(this);
        try {
            panelProductos = new PanelProductos(leeProductos(),panelLateral);
        } catch (IOException ioe){
            LOGGER.severe("No se ha podido leer el csv de productos");
            throw ioe;
        }
        panelSuperiorFechaHora = new PanelSuperiorFechaHora();
    }

    public static void main(String[] args) throws IOException{
        TPVCopisteria tpvCopisteria = new TPVCopisteria();

        JFrame frame = new JFrame();
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(0,1));
        /* Quito por ahora la parte del logo y hora por temas de diseño
        PanelSuperiorFechaHora panelSuperiorFechaHora = new PanelSuperiorFechaHora();
        panelPrincipal.add(panelSuperiorFechaHora.getPanel());*/
        JPanel panelProductosYLateral = new JPanel(new GridLayout(1,2));
        panelProductosYLateral.add(tpvCopisteria.getPanelProductos());
        panelProductosYLateral.add(tpvCopisteria.getPanelLateral());
        panelPrincipal.add(panelProductosYLateral);
        frame.add(panelPrincipal);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private HashSet<Producto> leeProductos() throws IOException {
        Set<Producto> productos = new HashSet<>();
        List<String> lineasCsv = Files.readAllLines(Paths.get("productos.csv"));
        for (String linea:lineasCsv) {
            String[] separadoPorComas = linea.split(",");
            String nombre = separadoPorComas[0];
            int precioEnCent = Integer.parseInt(separadoPorComas[1]);
            String uriImagen = separadoPorComas[2];
            Set<CategoriaProducto> categoriasProducto = new HashSet<>();
            String[] categoriasEnTexto = separadoPorComas[3].split(":");
            for (String categoriaEnTexto:categoriasEnTexto) {
                categoriasProducto.add(CategoriaProducto.valueOf(categoriaEnTexto));
            }
            Producto producto = new Producto(nombre,precioEnCent,uriImagen,categoriasProducto);
            productos.add(producto);
        }
        return (HashSet<Producto>) productos;
    }
}
