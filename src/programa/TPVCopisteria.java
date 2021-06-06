package programa;

import logger.LogFactory;
import paneles.PanelLateral;
import paneles.PanelProductos;
import productos.CategoriaProducto;
import productos.Producto;
import productos.ProductoEnTiquet;
import tiquets.HistoricoTiquets;
import tiquets.Tiquet;
import utilidades.fechas.UtilidadesFechas;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * Clase que lanzara la ejecucion del TPV
 * @author Rafael Niñoles Parra
 */
public class TPVCopisteria {
    private static final Logger LOGGER = LogFactory.getLogger();
    public static final JFrame FRAME = new JFrame();
    private static HistoricoTiquets historicoTiquets;
    private static final HistoricoTiquets tiquetsDelDia = new HistoricoTiquets();
    private static final LocalDateTime fechaInicio = LocalDateTime.now();
    public static void anyadeTiquetAHistorico(Tiquet tiquet){
        historicoTiquets.anyadeTiquet(tiquet);
    }
    public static void anyadeTiquetDia(Tiquet tiquet){
        tiquetsDelDia.anyadeTiquet(tiquet);
    }
    private final PanelProductos panelProductos;
    private final PanelLateral panelLateral;
    private final JPanel panelGlobal;
    private final GridBagConstraints constraintGlobal;

    /**
     * Devuelve el panel global que compone todo el TPV
     * @return panel global que compone todo el TPV
     */
    public JPanel getPanelGlobal() {
        return panelGlobal;
    }

    /**
     * Guarda el historico de tiquets
     */
    public static void guardaHistorico() {
        try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("resources/historico.tiquets")))){
            oos.writeObject(historicoTiquets);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Genera un fichero HTML en la carpeta informes, en el que informa del rendimiento del TPV basandose en el historico de tiquets
     */
    public static void generaEstadisticasTPV(){
        //TODO REVISAR EN JAR
        carpetaInformes();
        checkeaImagenYCSS();
        LocalDateTime fechaActual = LocalDateTime.now();
        Tiquet tiquetMasGrande = historicoTiquets.getTiquets().get(0);
        int totalProductosComprados = 0;
        int totalCompras = 0;
        int cantidadCompras = historicoTiquets.getTiquets().size();
        int compraMasCara = 0;
        Map<Producto,Integer> ventasPorProducto = new HashMap<>();
        for (Tiquet tiquet:historicoTiquets.getTiquets()) {
            if(tiquet.getTotalEnCent()>tiquetMasGrande.getTotalEnCent()){
                tiquetMasGrande=tiquet;
            }
            //Repasa todos los tiquets y saca las estadisiticas basicas
            totalCompras += tiquet.getTotalEnCent();
            if (compraMasCara < tiquet.getTotalEnCent()){
                compraMasCara = tiquet.getTotalEnCent();
            }
            //Repasa cada producto para sacar estadisticas concretas
            for (ProductoEnTiquet producto:tiquet.getProductos()) {
                if(!ventasPorProducto.containsKey(producto.getProducto())){
                    //Si no existe lo añade al mapa
                    ventasPorProducto.put(producto.getProducto(),producto.getCantidad());
                }else{
                    //Si existe le suma la cantidad
                    int cantidadAnterior = ventasPorProducto.get(producto.getProducto());
                    int nuevaCantidad = cantidadAnterior + producto.getCantidad();
                    ventasPorProducto.put(producto.getProducto(),nuevaCantidad);
                }
                totalProductosComprados += producto.getCantidad();
            }
        }
        int generadoConMasVendido = 0;
        Producto productoMasVendido = historicoTiquets.getTiquets().get(0).getProductos().get(0).getProducto();
        for (Map.Entry<Producto,Integer> entrada:ventasPorProducto.entrySet()) {
            Producto producto = entrada.getKey();
            int cantidad = entrada.getValue();
            int generadoPorProducto = cantidad*producto.getPrecioCentimos();
            if(generadoConMasVendido<generadoPorProducto){
                generadoConMasVendido=generadoPorProducto;
                productoMasVendido=producto;
            }
        }
        int mediaProductosCompra = totalProductosComprados/cantidadCompras;
        int compraMedia = totalCompras/cantidadCompras;
        String archivoHTML = "<!DOCTYPE html>\n";
        archivoHTML += "<html lang=\"es\">\n";
        //Comienza el HEAD
        archivoHTML += "<head>\n";
        archivoHTML += "<meta charset=\"UTF-8\">\n";
        archivoHTML += "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n";
        archivoHTML += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n";
        archivoHTML += "<title>Informe estadisticas TPV</title>\n";
        archivoHTML += "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x\" crossorigin=\"anonymous\">";
        archivoHTML += "<link rel=\"stylesheet\" href=\"./estilos.css\">";
        archivoHTML += "</head>";
        //Cierra HEAD y comienza BODY
        archivoHTML += "<body>";
        archivoHTML += "<div class=\"container\">";
        archivoHTML += "<div class=\"row mt-4 mb-4\" style=\"text-align: center;\">";
        archivoHTML += "<div class=\"col-12\">";
        archivoHTML += "<img style=\"width: auto;\" src=\"./logo.png\" alt=\"Logo de CentroImpresion\">";
        archivoHTML += "</div>";
        archivoHTML += "</div>";
        archivoHTML += "<div class=\"row\">";
        archivoHTML += "<h1 class=\"mb-4 mt-4 p-2\">Informe estadístico del TPV de CentroImpresion</h1>";
        archivoHTML += "</div>";
        archivoHTML += "<div class=\"row estadisticas p-2\">";
        archivoHTML += "<h3>La compra media ha estado en: "+String.format("%.2f",(double) compraMedia/100)+"€</h3>";
        archivoHTML += "<h3>El producto mas vendido ha sido "+productoMasVendido.getNombre()+" vendiendo un total de "+String.format("%.2f",(double) generadoConMasVendido/100)+"€</h3>";
        archivoHTML += "<h3>La media de productos vendidos por compra ha sido de: "+mediaProductosCompra+"</h3>";
        archivoHTML += "<h3>Han habido un total de "+cantidadCompras+" compras</h3>";
        archivoHTML += "<h3>La compra mas grande fue de "+String.format("%.2f",(double) tiquetMasGrande.getTotalEnCent()/100)+"€ el dia "+UtilidadesFechas.getFechaFormateada(tiquetMasGrande.getFechaActual())+"</h3>";
        archivoHTML += "</div>";
        archivoHTML += "<div class=\"row\">";
        archivoHTML += "<p class=\"mt-2\" style=\"text-align: center;\">Hecho por Rafael Niñoles Parra | Generado: "+UtilidadesFechas.getFechaYHoraFormateada(fechaActual)+"</p>";
        archivoHTML += "</div>";
        archivoHTML += "</div>";
        archivoHTML += "</body>";
        //Acaba BODY;
        archivoHTML += "</html>";
        File archivoACrear = new File("./informes/informe-TPV-"+
                fechaActual.getDayOfMonth()+"-"+fechaActual.getMonthValue()+"-"+fechaActual.getYear()+
                "_"+fechaActual.getHour()+"-"+fechaActual.getMinute()+"-"+fechaActual.getSecond()+".html");
        //TODO revisar las excepciones
        try(FileWriter fwHTML = new FileWriter(archivoACrear)) {
            fwHTML.write(archivoHTML);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(TPVCopisteria.FRAME,"Se ha generado el informe en la carpeta de informes correctamente");
    }

    /**
     * Checkea si existe el logo y el archivo css en la carpeta de informes para la correcta visualizacion del HTML. Si no existe los crea
     */
    private static void checkeaImagenYCSS() {
        File imagenLogo = new File("./informes/logo.png");
        File estilosCSS = new File("./informes/estilos.css");
        try{
            if (!imagenLogo.exists()){
                File aCopiar = new File("resources/logo.png");
                Files.copy(aCopiar.toPath(), (new File("./informes/logo.png")).toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            if (!estilosCSS.exists()){
                File aCopiar = new File("resources/estilos.css");
                Files.copy(aCopiar.toPath(), (new File("./informes/estilos.css")).toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(TPVCopisteria.FRAME,"ERROR AL CREAR EL LOGO Y CSS");
        }
    }

    /**
     * Checkea si existe la carpeta de informes, si no existe la crea
     */
    private static void carpetaInformes() {
        File carpetaInformes = new File("./informes");
        if(!carpetaInformes.exists() || !carpetaInformes.isDirectory()){
            carpetaInformes.mkdir();
        }
    }

    /**
     * Imprime el cierre de la caja actual
     */
    public static void imprimeCierreCaja(){
        String aImprimir="";
        LocalDateTime fechaActual = LocalDateTime.now();
        aImprimir += "Cierre del "+UtilidadesFechas.getFechaFormateada(fechaActual)+" a las "+UtilidadesFechas.getHoraFormateada(fechaActual)+"\n";
        aImprimir += "La caja estuvo abierta de "+UtilidadesFechas.getFechaYHoraFormateada(fechaInicio)+" a "+UtilidadesFechas.getFechaYHoraFormateada(fechaActual)+"\n";
        int totalCobradoEnCents = 0;
        int totalPedidos = tiquetsDelDia.getTiquets().size();
        int totalProductosVendidos = 0;
        for (Tiquet tiquet:tiquetsDelDia.getTiquets()) {
            totalCobradoEnCents += tiquet.getTotalEnCent();
            totalProductosVendidos += tiquet.getCantidadProductos();
        }
        aImprimir+="Se han hecho "+totalPedidos+" pedidos\n";
        aImprimir+="Se han vendido "+totalProductosVendidos+" productos\n";
        aImprimir+="El total cobrado ha sido de "+String.format("%.2f",(double) totalCobradoEnCents/100)+"€";
        imprimeTexto(aImprimir);
    }

    /**
     * Imprime un tiquet
     * @param tiquet Tiquet que se quiere imprimir
     */
    public static void imprimeTicket(Tiquet tiquet){
        imprimeTexto(tiquet.getInforme());
    }

    /**
     * Imprime un texto
     * @param texto Texto que desea imprimir
     */
    private static void imprimeTexto(String texto){
        JTextPane jtp = new JTextPane();
        jtp.setBackground(Color.white);
        jtp.setFont(new Font("Courier New",Font.BOLD,8));
        jtp.setText(texto);
        try {
            jtp.print(null, null, true, null, null, true);
        } catch (java.awt.print.PrinterException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Devuelve el JPanel del panel de productos
     * @return JPanel del panel de productos
     */
    public JPanel getPanelProductos() {
        return panelProductos.getPanel();
    }

    /**
     * Devuelve el JPanel del panel lateral
     * @return JPanel del panel lateral
     */
    public JPanel getPanelLateral() {
        return panelLateral.getPanel();
    }

    /**
     * Crea un TPV completo
     * @throws IOException excepcion si no se pueden leer los productos
     */
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
            historicoTiquets = (HistoricoTiquets) ois.readObject();
            for (Tiquet tiquet: historicoTiquets.getTiquets()) {
                System.out.println(tiquet.getCantidadProductos()+" "+tiquet.getTotalEnCent()+"centimos "+tiquet.getFechaFormateada());
            }
        } catch (ClassNotFoundException | EOFException e) {
            e.printStackTrace();
            historicoTiquets = new HistoricoTiquets();
            LOGGER.severe("Hay algun fallo en el fichero del historico de tiquets, no detecta que sea un objeto correcto");
        } catch (FileNotFoundException notFoundException){
            historicoTiquets = new HistoricoTiquets();
            LOGGER.info("El fichero de historico tiquets aun no existe");
        } catch (InvalidClassException icException){
            icException.printStackTrace();
            LOGGER.info("El fichero del historico de tiquets esta obsoleto, creando uno nuevo");
            historicoTiquets = new HistoricoTiquets();

        }
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
        FRAME.add(tpvCopisteria.getPanelGlobal());
        FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        FRAME.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //Quitado de pantalla completa porque para el jdialog del cobro no me deja si esta undecorated
        //tpvCopisteria.FRAME.setUndecorated(true);
        FRAME.pack();
        FRAME.setVisible(true);
        FRAME.setLocationRelativeTo(null);
    }

    private HashSet<Producto> leeProductos() throws IOException {
        HashSet<Producto> productos = new HashSet<>();
        int columnaNombre=0;
        int columnaPrecioCent=1;
        int columnaUriImagen=2;
        int columnaCategorias=3;
        List<String> lineasCsv = Files.readAllLines(Paths.get("resources/productos.csv"));
        for (String linea:lineasCsv) {
            String[] separadoPorComas = linea.split(",");
            String nombre = separadoPorComas[columnaNombre];
            int precioEnCent = Integer.parseInt(separadoPorComas[columnaPrecioCent]);
            String uriImagen = separadoPorComas[columnaUriImagen];
            Set<CategoriaProducto> categoriasProducto = new HashSet<>();
            String[] categoriasEnTexto = separadoPorComas[columnaCategorias].split(":");
            for (String categoriaEnTexto:categoriasEnTexto) {
                categoriasProducto.add(CategoriaProducto.valueOf(categoriaEnTexto));
            }
            Producto producto = new Producto(nombre,precioEnCent,uriImagen,categoriasProducto);
            productos.add(producto);
        }
        return productos;
    }
}
