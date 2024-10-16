
import java.io.*;
import java.util.*;
import java.util.LinkedList;

class Eje4 {

 /* Devuelve verdadero si hay un camino desde la fuente 's' al sumidero 't' 
    en el grafo residual. También llena parent[] para almacenar el camino */
 public static boolean bfs(int rGraph[][], int s, int t, int parent[]) {
     // Crear un arreglo de visitados y marcar todos los vértices como no visitados
	 int V =rGraph.length;
     boolean visited[] = new boolean[V];
     for (int i = 0; i < V; ++i)
         visited[i] = false;

     // Crear una cola, encolar el vértice fuente y marcarlo como visitado
     LinkedList<Integer> queue = new LinkedList<Integer>();
     queue.add(s);
     visited[s] = true;
     parent[s] = -1;

     // Bucle estándar de BFS
     while (queue.size() != 0) {
         int u = queue.poll();

         for (int v = 0; v < V; v++) {
             if (visited[v] == false && rGraph[u][v] > 0) {
                 // Si encontramos una conexión al nodo sumidero,
                 // ya no hay necesidad de continuar con el BFS.
                 // Solo tenemos que establecer su padre y podemos devolver verdadero
                 if (v == t) {
                     parent[v] = u;
                     return true;
                 }
                 queue.add(v);
                 parent[v] = u;
                 visited[v] = true;
             }
         }
     }

     // No llegamos al sumidero en el BFS comenzando desde la fuente,
     // así que devolvemos falso
     return false;
 }

 // Devuelve el flujo máximo desde s hasta t en el grafo dado
 public static int fordFulkerson(int graph[][], int s, int t) {
	 int V =graph.length;
	 int u, v;

     // Crear un grafo residual y llenar el grafo residual con las capacidades
     // dadas en el grafo original como capacidades residuales en el grafo residual
     int rGraph[][] = new int[V][V];

     for (u = 0; u < V; u++)
         for (v = 0; v < V; v++)
             rGraph[u][v] = graph[u][v];

     // Este arreglo se llena por BFS y almacena el camino
     int parent[] = new int[V];

     int max_flow = 0; // No hay flujo inicialmente

     // Aumentar el flujo mientras haya un camino desde la fuente al sumidero
     while (bfs(rGraph, s, t, parent)) {
         // Encontrar la capacidad mínima residual de los bordes
         // a lo largo del camino llenado por BFS.
         int path_flow = Integer.MAX_VALUE;
         for (v = t; v != s; v = parent[v]) {
             u = parent[v];
             path_flow = Math.min(path_flow, rGraph[u][v]);
         }

         // Actualizar las capacidades residuales de los bordes y
         // los bordes inversos a lo largo del camino
         for (v = t; v != s; v = parent[v]) {
             u = parent[v];
             rGraph[u][v] -= path_flow;
             rGraph[v][u] += path_flow;
         }

         // Añadir el flujo del camino al flujo total
         max_flow += path_flow;
     }

     // Devolver el flujo total
     return max_flow;
 }
 
 //Modifica el grafo de entrada para que tenga una fuente y un sumidero.
 public static int [][] modificarGrafo (int graph[][], int s, int t){
	
	 //Inicializa el nuevo grafo con 2 vertices más
	 int [][] newGraph = new int [graph.length +2][graph[0].length +2];
	 
	//Llenar el grafo con las capacidades del grafo inicial o con infinito.
	 for (int i = 0; i < newGraph.length; i++) {
         for (int j = 0; j < newGraph.length; j++) {
             
        	 if ((i==0 && j==0) || (i==graph.length+1 && j==graph.length+1)) newGraph[i][j]=0;
        	
        	 else if ((i==0 && j<=s) || (i<=s && j==0)) newGraph[i][j]=Integer.MAX_VALUE;
        	 
        	 else if ((i==graph.length+1 && graph.length+1-j<=t) || (graph.length+1-i<=t && j==graph.length+1)) newGraph[i][j]=Integer.MAX_VALUE;
        	 
        	 else if(i==0 || j==0 || i==graph.length+1 || j==graph.length+1) newGraph[i][j] = 0;
        	 
        	 else newGraph[i][j] = graph[i-1][j-1];
        	 
         }
     }
	 return newGraph;
	 
 }
 
 public static int LibrosMaximos(int graph[][], int Ns, int Nt) {
	 
	 int [][] newGraph = modificarGrafo(graph, Ns, Nt);
	 
	 return fordFulkerson(newGraph,0,newGraph.length-1);
	 
 }
		 
 public static void main(String[] args) {

     if (args.length < 1) {
         System.out.println("Por favor, proporciona el nombre del archivo como argumento.");
         return;
     }

     String filename = args[0]; // Nombre del archivo pasado como argumento
     List<int[]> matrixList = new ArrayList<>();
     int cantidadS= 0;
     int cantidadT= 0;
     
     try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
         String line = br.readLine();
         String[] cantidad = line.trim().split("\\s+");
         cantidadS= Integer.parseInt(cantidad[0]);
         cantidadT= Integer.parseInt(cantidad[1]);
         
         while ((line = br.readLine()) != null) {
             String[] values = line.trim().split("\\s+");
             int[] row = new int[values.length];
             for (int i = 0; i < values.length; i++) {
                 row[i] = Integer.parseInt(values[i]);
             }
             matrixList.add(row);
         }
     } catch (IOException e) {
         e.printStackTrace();
         return; 
     }

     int[][] grafo = new int[matrixList.size()][];
     for (int i = 0; i < matrixList.size(); i++) {
    	 grafo[i] = matrixList.get(i);
     }

     System.out.println("La matriz de adyacencia ingresada con "+cantidadS+" fabricas y "+cantidadT+" librerias es:");
     for (int[] row : grafo) {
         System.out.println(Arrays.toString(row));
     }
     System.out.println("La máxima cantidad de libros que se puede transportar en un día es: "+ LibrosMaximos(grafo,cantidadS,cantidadT));
     
 }
}
