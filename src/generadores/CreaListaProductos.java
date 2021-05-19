package generadores;

import productos.CategoriaProducto;
import productos.ListaProductos;
import productos.Producto;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreaListaProductos implements Serializable {
    public static void main(String[] args) throws IOException {
        ListaProductos listaProductos = new ListaProductos();
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
            listaProductos.anyadeProducto(producto);
        }

        try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("ListaProductos.obj")))){
            oos.writeObject(listaProductos);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
