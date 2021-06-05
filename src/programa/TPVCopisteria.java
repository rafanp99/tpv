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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

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

    public JPanel getPanelGlobal() {
        return panelGlobal;
    }

    public static void guardaHistorico() {
        try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("resources/historico.tiquets")))){
            oos.writeObject(historicoTiquets);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

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
        archivoHTML += "<h3>La compra mas grande fue de "+String.format("%.2f",(double) tiquetMasGrande.getTotalEnCent()/100)+" el dia "+UtilidadesFechas.getFechaFormateada(tiquetMasGrande.getFechaActual())+"</h3>";
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

    private static void carpetaInformes() {
        File carpetaInformes = new File("./informes");
        if(!carpetaInformes.exists() || !carpetaInformes.isDirectory()){
            carpetaInformes.mkdir();
        }
    }

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

    public static void imprimeTicket(Tiquet tiquet){
        imprimeTexto(tiquet.getInforme());
    }
    private static void imprimeTexto(String texto){
        JTextPane jtp = new JTextPane();
        jtp.setBackground(Color.white);
        jtp.setFont(new Font("Courier New",Font.BOLD,8));
        jtp.setText(texto);
        boolean show = true;
        try {
            jtp.print(null, null, show, null, null, show);
        } catch (java.awt.print.PrinterException ex) {
            ex.printStackTrace();
        }
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
        Path fullPath = Paths.get(".").toAbsolutePath();
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
