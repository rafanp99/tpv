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
    public static void main(String[] args) throws IOException{
        PanelProductos panelProductos = null;
        try {
            panelProductos = new PanelProductos(leeProductos());
        } catch (IOException ioe){
            LOGGER.severe("No se ha podido leer el csv de productos");
            throw ioe;
        }

        JFrame frame = new JFrame();
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(0,1));
        GridBagConstraints gridBagC = new GridBagConstraints();
        gridBagC.gridx=0;
        gridBagC.gridy=0;
        gridBagC.gridwidth=2;
        PanelSuperiorFechaHora panelSuperiorFechaHora = new PanelSuperiorFechaHora();
        panelPrincipal.add(panelSuperiorFechaHora.getPanel(),gridBagC);
        gridBagC.gridwidth=1;
        gridBagC.gridy=1;
        gridBagC.gridx=0;
        panelPrincipal.add(panelProductos.getPanel(),gridBagC);
        gridBagC.gridwidth=1;
        gridBagC.gridx=1;
        gridBagC.gridy=1;
        PanelLateral panelLateral = new PanelLateral();
        panelPrincipal.add(panelLateral.getPanel(),gridBagC);
        frame.add(panelPrincipal);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private static HashSet<Producto> leeProductos() throws IOException {
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
