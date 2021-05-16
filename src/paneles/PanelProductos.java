package paneles;

import productos.ListaProductos;
import productos.Producto;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PanelProductos {
    private final JPanel panel = new JPanel();
    private final List<Producto> listaProductos;

    public PanelProductos(ListaProductos listaProductos) {
        this.listaProductos = listaProductos.getProductos();
    }

    public List<Producto> getListaProductos() {
        return new ArrayList<>(listaProductos);
    }

    public static void main(String[] args) {
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
        } catch (ClassNotFoundException cnfe){
            System.out.println("No hay coches en el fichero");
            cnfe.printStackTrace();
        }
        for (Producto p:listaProductos.getProductos()
             ) {
            System.out.println(p.getNombre());
        }
    }
}
