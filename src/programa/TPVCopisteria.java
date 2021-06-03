package programa;

import logger.LogFactory;
import paneles.PanelLateral;
import paneles.PanelProductos;
import paneles.PanelBarraSuperior;
import productos.CategoriaProducto;
import productos.Producto;
import tiquets.HistoricoTiquets;
import tiquets.Tiquet;

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
    private static HistoricoTiquets HISTORICO_TIQUETS;
    private static final Logger LOGGER = LogFactory.getLogger(TPVCopisteria.class.getName());
    public static final JFrame FRAME = new JFrame();;
    public static void anyadeTiquetAHistorico(Tiquet tiquet){
        HISTORICO_TIQUETS.anyadeTiquet(tiquet);
    }
    private final PanelBarraSuperior panelBarraSuperior;
    private final PanelProductos panelProductos;
    private final PanelLateral panelLateral;

    public static void guardaHistorico() {
        /*try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("resources/historico.tiquets")))){
            oos.writeObject(HISTORICO_TIQUETS);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }*/
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
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("resources/historico.tiquets"))){
            HISTORICO_TIQUETS = (HistoricoTiquets) ois.readObject();
        } catch (ClassNotFoundException | EOFException e) {
            e.printStackTrace();
            HISTORICO_TIQUETS = new HistoricoTiquets();
            LOGGER.severe("Hay algun fallo en el fichero del historico de tiquets, no detecta que sea un objeto correcto");
        } catch (FileNotFoundException notFoundException){
            HISTORICO_TIQUETS = new HistoricoTiquets();
            LOGGER.info("El fichero de historico tiquets aun no existe");
        }
        this.panelBarraSuperior = new PanelBarraSuperior();
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
        JPanel panelGlobal = new JPanel(new GridBagLayout());
        //intentaDisenyoBonito();
        PanelBarraSuperior panelBarraSuperior = new PanelBarraSuperior();
        JPanel panelProductosYLateral = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor=GridBagConstraints.NORTH;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.gridwidth=20;
        constraints.weightx=1;
        constraints.gridheight=2;
        panelGlobal.add(panelBarraSuperior.getPanel(),constraints);
        constraints.gridx=0;
        constraints.gridwidth=15;
        constraints.gridheight=20;
        panelProductosYLateral.add(tpvCopisteria.getPanelProductos(),constraints);
        constraints.gridwidth=5;
        constraints.gridx=15;
        panelProductosYLateral.add(tpvCopisteria.getPanelLateral(),constraints);
        constraints.gridx=1;
        constraints.gridy=2;
        constraints.gridwidth=1;
        constraints.gridheight=8;
        panelGlobal.add(panelProductosYLateral,constraints);
        tpvCopisteria.FRAME.add(panelGlobal);
        tpvCopisteria.FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tpvCopisteria.FRAME.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
