package generadores;

import productos.CategoriaProducto;
import productos.ListaProductos;
import productos.Producto;

import java.io.*;

public class CreaListaProductos implements Serializable {
    public static void main(String[] args) {
        ListaProductos listaProductos = new ListaProductos();
        listaProductos.anyadeProducto(new Producto("Fotocopia B/N",10,"test.png", CategoriaProducto.MAS_USADO));
        listaProductos.anyadeProducto(new Producto("Fotocopia Color",50,"test.png", CategoriaProducto.MAS_USADO));
        listaProductos.anyadeProducto(new Producto("Camiseta vinilo",11,"test.png", CategoriaProducto.CAMISETAS));
        listaProductos.anyadeProducto(new Producto("Foto 10x15",10,"test.png", CategoriaProducto.FOTOGRAFIA));

        try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("ListaProductos.obj")))){
            oos.writeObject(listaProductos);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
