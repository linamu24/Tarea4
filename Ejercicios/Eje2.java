import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.Queue;

public class Eje2 {

    // Función que realiza BFS para encontrar los componentes conectados a un vertice
    public static List<Integer> bfs(Map<Integer, List<Integer>> graph, Integer start) {
        // Cola para explorar los nodos
        Queue<Integer> queue = new LinkedList<>();
        // Lista para almacenar los vertices ya visitados
        List<Integer> visitados = new ArrayList<Integer>();

        // Inicializar el nodo de inicio
        queue.add(start);
        visitados.add(start);
     
        
        // Mientras haya nodos por explorar
        while (!queue.isEmpty()) {
            // Extraer el nodo actual de la cola
        	Integer current = queue.poll();

            // Explorar los vecinos del nodo actual
            for (Integer neighbor: graph.get(current)) {
                // Si el vecino no ha sido visitado aún
                if (!visitados.contains(neighbor)) { 
                    // Añadir el vecino a la cola
                    queue.add(neighbor);
                    // Añadir el vecino a visitados
                    visitados.add(neighbor);
                    
                }
            }
        }

       return visitados;
    }


    // Función que encuentras los compenentes conectados del grafo de entrada (lista de adyacencia).
    public static List<List<Integer>> encontrarComponentesConectadas(Map<Integer, List<Integer>> graph) {
        
        // Inicializo la respuesta
        List<List<Integer>> respuesta = new ArrayList<>();
        
     // Lista para almacenar los vertices ya visitados
        List<Integer> visitados = new ArrayList<Integer>();
     
        
        
        for (Integer vertice: graph.keySet()) {
              if(!visitados.contains(vertice)) {
            	  List<Integer> lista = bfs(graph,vertice);
            	  visitados.addAll(lista);
            	  respuesta.add(lista);
              }
          
        }

       return respuesta;
    }
    
    

    //Main que convierte el archivo de entrada y ejecuta la funcion encontrarComponentesConectadas
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java GrafoDesdeArchivo <ruta del archivo>");
            return;
        }

        String rutaArchivo = args[0];
        Map<Integer, List<Integer>> grafo = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Suponemos que la línea tiene el formato: "vértice: vecino1, vecino2, ..."
                String[] partes = linea.split(":");
                Integer vertice = Integer.parseInt(partes[0].trim());
                String[] vecinos = partes[1].trim().split(",");

                List<Integer> listaVecinos = new ArrayList<>();
                for (String vecino : vecinos) {
                    listaVecinos.add(Integer.parseInt(vecino.trim()));
                }

                grafo.put(vertice, listaVecinos);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear un número: " + e.getMessage());
        }

        // Imprimir el grafo para verificar que se haya leído correctamente
        System.out.println("Grafo ingresado por parámetros:");
        for (Map.Entry<Integer, List<Integer>> entrada : grafo.entrySet()) {
            System.out.println(entrada.getKey() + " -> " + entrada.getValue());
        }
        
     // Imprimir partes conectadas del grafo
        System.out.println("Componentes conectadas de este grafo:");
        List<List<Integer>> respuesta = encontrarComponentesConectadas(grafo);
        System.out.println(respuesta);
       
    }
}