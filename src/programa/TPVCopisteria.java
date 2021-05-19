package programa;

import paneles.PanelProductos;
import productos.ListaProductos;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class TPVCopisteria {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        PanelProductos panelProductos = null;
        try {
            panelProductos = new PanelProductos(leeProductos());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"No se pueden leer los productos");
            throw e;
        }

        JFrame frame = new JFrame();
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gridBagC = new GridBagConstraints();
        gridBagC.gridx=0;
        gridBagC.gridy=0;
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private static ListaProductos leeProductos() throws IOException, ClassNotFoundException {
        ListaProductos listaProductos = null;
        try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("ListaProductos.obj")))){
            while(true) {
                listaProductos=(ListaProductos) ois.readObject();
            }
        }  catch (EOFException eof){
            System.out.println("Termino de leer");
        }
        catch (IOException ioe){
            ioe.printStackTrace();
            throw ioe;
        } catch (ClassNotFoundException cnfe){
            System.out.println("No hay productos en el fichero");
            cnfe.printStackTrace();
            throw cnfe;
        }
        return null;
    }
}
