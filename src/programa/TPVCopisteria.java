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
    public static final JFrame FRAME = new JFrame();;

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

    private static void intentaDisenyoBonito(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        TPVCopisteria tpvCopisteria = new TPVCopisteria();
        //intentaDisenyoBonito();
        /* Quito por ahora la parte del logo y hora por temas de diseño
        PanelSuperiorFechaHora panelSuperiorFechaHora = new PanelSuperiorFechaHora();
        panelPrincipal.add(panelSuperiorFechaHora.getPanel());*/
        JPanel panelProductosYLateral = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor=GridBagConstraints.NORTH;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.gridx=0;
        constraints.gridwidth=15;
        constraints.gridheight=20;
        constraints.gridy=0;
        panelProductosYLateral.add(tpvCopisteria.getPanelProductos(),constraints);
        constraints.gridwidth=5;
        constraints.gridx=15;
        panelProductosYLateral.add(tpvCopisteria.getPanelLateral(),constraints);
        tpvCopisteria.FRAME.add(panelProductosYLateral);
        tpvCopisteria.FRAME.setExtendedState(JFrame.MAXIMIZED_BOTH);
        tpvCopisteria.FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tpvCopisteria.FRAME.setUndecorated(true);
        tpvCopisteria.FRAME.pack();
        tpvCopisteria.FRAME.setVisible(true);
        tpvCopisteria.FRAME.setLocationRelativeTo(null);
    }

    private HashSet<Producto> leeProductos() throws IOException {
        Set<Producto> productos = new HashSet<>();
        List<String> lineasCsv = Files.readAllLines(Paths.get("resources/productos.csv"));
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
