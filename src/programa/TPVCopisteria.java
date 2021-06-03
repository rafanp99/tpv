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
    private final JPanel panelGlobal;
    private final GridBagConstraints constraintGlobal;

    public JPanel getPanelGlobal() {
        return panelGlobal;
    }

    public static void guardaHistorico() {
        try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("resources/historico.tiquets")))){
            oos.writeObject(HISTORICO_TIQUETS);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public PanelBarraSuperior getPanelBarraSuperior() {
        return panelBarraSuperior;
    }

    public JPanel getPanelProductos() {
        return panelProductos.getPanel();
    }

    public JPanel getPanelLateral() {
        return panelLateral.getPanel();
    }

    public TPVCopisteria() throws IOException {
        this.panelGlobal = new JPanel(new GridBagLayout());
        panelLateral = new PanelLateral(this);
        try {
            panelProductos = new PanelProductos(leeProductos(),panelLateral);
        } catch (IOException ioe){
            LOGGER.severe("No se ha podido leer el csv de productos");
            throw ioe;
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("resources/historico.tiquets"))){
            HISTORICO_TIQUETS = (HistoricoTiquets) ois.readObject();
            for (Tiquet tiquet:HISTORICO_TIQUETS.getTiquets()) {
                System.out.println(tiquet.getCantidadProductos()+" "+tiquet.getTotalEnCent()+"centimos "+tiquet.getFechaFormateada());
            }
        } catch (ClassNotFoundException | EOFException e) {
            e.printStackTrace();
            HISTORICO_TIQUETS = new HistoricoTiquets();
            LOGGER.severe("Hay algun fallo en el fichero del historico de tiquets, no detecta que sea un objeto correcto");
        } catch (FileNotFoundException notFoundException){
            HISTORICO_TIQUETS = new HistoricoTiquets();
            LOGGER.info("El fichero de historico tiquets aun no existe");
        } catch (InvalidClassException icException){
            icException.printStackTrace();
            LOGGER.info("El fichero del historico de tiquets esta obsoleto, creando uno nuevo");
            HISTORICO_TIQUETS = new HistoricoTiquets();

        }
        this.panelBarraSuperior = new PanelBarraSuperior();
        this.constraintGlobal = new GridBagConstraints();
        disenyaPanelGlobal();
    }

    private void disenyaPanelGlobal() {
        constraintGlobal.anchor=GridBagConstraints.NORTH;
        constraintGlobal.fill=GridBagConstraints.HORIZONTAL;
        constraintGlobal.gridx=0;
        constraintGlobal.gridy=0;
        constraintGlobal.weightx=1;
        constraintGlobal.weighty=1;
        constraintGlobal.gridwidth=20;
        constraintGlobal.gridheight=2;
        panelGlobal.add(panelBarraSuperior.getPanel(),constraintGlobal);
        constraintGlobal.gridx=0;
        constraintGlobal.gridy=2;
        constraintGlobal.gridwidth=15;
        constraintGlobal.gridheight=20;
        panelGlobal.add(panelProductos.getPanel(),constraintGlobal);
        constraintGlobal.gridwidth=5;
        constraintGlobal.gridx=15;
        panelGlobal.add(panelLateral.getPanel(),constraintGlobal);
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
        tpvCopisteria.FRAME.add(tpvCopisteria.getPanelGlobal());
        tpvCopisteria.FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tpvCopisteria.FRAME.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //Quitado de pantalla completa porque para el jdialog del cobro no me deja si esta undecorated
        //tpvCopisteria.FRAME.setUndecorated(true);
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
