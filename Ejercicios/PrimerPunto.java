import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class primerPunto {
    private Map<Integer, List<Tupla>> grafo;
    private List<List<Integer>> caminosMinimos;
    public primerPunto(){
        grafo = new HashMap<>();
        caminosMinimos = new ArrayList<>();
    }
    public void leerArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(rutaArchivo)))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.trim().split("\\s+");
                if (partes.length == 3) {
                    int origen = Integer.parseInt(partes[0]);
                    int destino = Integer.parseInt(partes[1]);
                    int peso = Integer.parseInt(partes[2]);
                    Tupla tupla = new Tupla(destino, peso);
                    if (grafo.get(origen)==null){
                        grafo.put(origen,new ArrayList<>());
                    }
                    
                    grafo.get(origen).add(tupla);
                } else {
                    System.out.println("Línea no válida: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarGrafo() {
        System.out.println("Grafo (Lista de adyacencia):");
        for (Map.Entry<Integer, List<Tupla>> entrada : grafo.entrySet()) {
            System.out.print(entrada.getKey() + " -> ");
            for (Tupla tupla : entrada.getValue()) {
                System.out.print(tupla + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String rutaArchivo = args[0];
        
        //String rutaArchivo = "data/distances5.txt";
        
        primerPunto primerPunto = new primerPunto();

        primerPunto.leerArchivo(rutaArchivo);

        String algoritmo = args[1];
        if ("1".equals(algoritmo)){
            primerPunto.dijkstra();
            primerPunto.mostrarCaminosMinimos();

        }
        else if ("2".equals(algoritmo)){
            primerPunto.bellmanFord(); 
            primerPunto.mostrarCaminosMinimos();

        }
        else if ("3".equals(algoritmo)){
            primerPunto.floydWarschall();
        }

    }
    public void dijkstra(){
        //Inicializar los caminos minimos con infinito menos el del nodo fuente al nodo fuente 
        int numeroDeNodos = grafo.keySet().size();
        for (int i = 0; i < numeroDeNodos; i++) {
            // Inicializa la lista de costos mínimos para el nodo fuente 'i'
            List<Integer> fila = new ArrayList<>();
            for (int n = 0; n < numeroDeNodos; n++){
                if (n==i){
                    fila.add(0);
                }
                else {
                    fila.add(Integer.MAX_VALUE);
                }
            }
            caminosMinimos.add(fila);
            dijkstra(i);  // Ejecutar Dijkstra desde el nodo 'i'
        }
        
    }

    public void dijkstra(int fuente){

        Set<Integer> visitados = new HashSet<>();
        PriorityQueue<Tupla> colaPrioridad =  new PriorityQueue<>(new ComparadorPorPeso());
        colaPrioridad.add(new Tupla(fuente, 0));

        while (!colaPrioridad.isEmpty()) {

            //Aquí obtengo y elimino el elemento
            Tupla actual = colaPrioridad.poll();
            int nodoActual = actual.destino;
            
            // Si el nodo ya fue visitado, saltarlo
            if (visitados.contains(nodoActual)) {
                continue;
            }
            //Añado el nodo en el que estoy a visitados
            visitados.add(nodoActual);
            
            // Acá evisar los vecinos del nodo actual
            List<Tupla> vecinos = grafo.get(nodoActual);

            //Aqui me aseguro de que tenga vecinos
            if (vecinos != null) {
                for (Tupla vecino : vecinos) {

                    int nuevoPeso = caminosMinimos.get(fuente).get(nodoActual) + vecino.peso;
                    //  REVISAR BIEN ACÁ
                    // Si el nuevo camino es más corto, actualiza el peso y añade el vecino a la cola
                    //Acá reviso si el nuevoo peso es menor al  que ya estaba
                    if (nuevoPeso < caminosMinimos.get(fuente).get(vecino.destino)) {
                        caminosMinimos.get(fuente).set(vecino.destino, nuevoPeso);
                        colaPrioridad.add(new Tupla(vecino.destino, nuevoPeso));
                    }
                }
            }
        }

    }
    public void mostrarCaminosMinimos() {
        System.out.println("Caminos mínimos (costos):");
        for (int i = 0; i < caminosMinimos.size(); i++) {
            System.out.println("Desde el nodo " + i + ": " + caminosMinimos.get(i));
        }
    }

    //Iniciamos con Bellman Ford

    public void bellmanFord(){
        int numeroDeNodos = grafo.keySet().size();
        for (int i = 0; i < numeroDeNodos; i++) {
            // Inicializa la lista de costos mínimos para el nodo fuente 'i'
            List<Integer> fila = new ArrayList<>();
            for (int n = 0; n < numeroDeNodos; n++){
                if (n==i){
                    fila.add(0);
                }
                else {
                    fila.add(Integer.MAX_VALUE);
                }
            }
            caminosMinimos.add(fila);
            bellmanFord(i);  // Ejecutar Dijkstra desde el nodo 'i'
        }
        
    }

    public void bellmanFord(int fuente){
        int numeroDeNodos = grafo.keySet().size();

        //Recuerda: N-1 iteraciones, n es el número de vertices, nodos
        for (int i=1; i<numeroDeNodos; i++){
            //Recorre por cada vertice todos la saristas
            //Lo hace con las adyacencias de cada uno de los n
            for(int u=0; u<numeroDeNodos; u++){
                List<Tupla> vecinos = grafo.get(u);
                if (vecinos != null) {
                    for (Tupla vecino : vecinos) {
                        int v = vecino.destino; 
                        int peso = vecino.peso; 

                        //Relajar la arista
                        //Acá se verifica si el camino ya es conocido o no, si esta en infinito es porque no, es decir se sigue 
                        //con las otras aristas, despues de conocerse se van a seguir actualizando
                        //Si ya se conoce el camino y además, 
                        if (caminosMinimos.get(fuente).get(u) != Integer.MAX_VALUE && 
                        caminosMinimos.get(fuente).get(u) + peso < caminosMinimos.get(fuente).get(v)) {
                            //Acá inserta en la lista de la fuente en la posicion del vertice el nuevo peso que es el de el actual mas el peso para llegar a ella 
                            caminosMinimos.get(fuente).set(v, caminosMinimos.get(fuente).get(u) + peso);
                        }
                    }
                }
            }
        }
    }

    //Se inicia Floyd Warschall

    private void floydWarschall(){
        
        int numeroDeNodos = grafo.size();
        int[][] m = new int[numeroDeNodos][numeroDeNodos];
    
        int[][] matrizAdyacencia = new int[numeroDeNodos][numeroDeNodos];
        for (int i = 0; i < numeroDeNodos; i++) {
            for (int j = 0; j < numeroDeNodos; j++) {
                if (i == j) {
                    matrizAdyacencia[i][j] = 0;  
                    m [i][j]=0;
                } else {
                    matrizAdyacencia[i][j] = Integer.MAX_VALUE; 
                    m[i][j]= Integer.MAX_VALUE;
                }
            }
        }
        
        for (Map.Entry<Integer, List<Tupla>> entrada : grafo.entrySet()) {
            int nodoOrigen = entrada.getKey();
            List<Tupla> adyacentes = entrada.getValue();
    
            for (Tupla tupla : adyacentes) {
                int nodoDestino = tupla.destino;
                int peso = tupla.peso;
                matrizAdyacencia[nodoOrigen][nodoDestino] = peso; 
                m[nodoOrigen][nodoDestino]=peso;
            }

        }
        //Cuidado, acá el k va desde 1, porque cuando es 0 ya se resolvio arriba
        for(int k= 1; k<=numeroDeNodos; k++){
            for (int i = 0 ; i<numeroDeNodos; i++){
                for (int j = 0; j<numeroDeNodos; j++){
                    if (m[i][k-1] != Integer.MAX_VALUE && m[k-1][j] != Integer.MAX_VALUE) {
                        m[i][j] = Math.min(m[i][j], m[i][k-1] + m[k-1][j]);
                    }
                }
            }
        }
        //muestra la matriz
        for (int i = 0 ; i<numeroDeNodos; i++){
            List<Integer> caminos = new ArrayList<>();
            for (int j=0; j< numeroDeNodos; j++){
                caminos.add(m[i][j]);
            }
            System.out.println("para el nodo "+i+"->"+caminos);
        }


    }
}

    class ComparadorPorPeso implements Comparator<Tupla> {
    @Override
    public int compare(Tupla t1, Tupla t2) {
        return Integer.compare(t1.peso, t2.peso); // Ordenar por peso ascendente
    }


}


