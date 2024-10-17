import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Eje3 {
    private int cantidadNodos; 
    private int [] parents;
    private int [] h;
    private List<Arista> grafo;

    public Eje3(){
        cantidadNodos=0;
        grafo= new ArrayList<>();
    }
    private void leerArchivo(String rutaArchivo){

        try (BufferedReader br = new BufferedReader(new FileReader(new File(rutaArchivo)))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.trim().split("\\s+");
                if (partes.length == 3) {
                    int v1 = Integer.parseInt(partes[0]);
                    int v2 = Integer.parseInt(partes[1]);
                    int peso = Integer.parseInt(partes[2]);
                    Arista arista = new Arista(v1,v2, peso);
                    
                    grafo.add(arista);
                    cantidadNodos = Math.max(cantidadNodos, Math.max(v1, v2) + 1);
                } else {
                    System.out.println("Línea no válida: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(grafo, new Comparator<Arista>() {
            public int compare(Arista a1, Arista a2) {
                return Integer.compare(a1.peso, a2.peso);
            }
        });
    }
    public static void main(String[] args) {
        String rutaArchivo = args[0];
        Eje3 segundoPunto = new Eje3();
        segundoPunto.leerArchivo(rutaArchivo);
        segundoPunto.kruskal();
    }
    private void union (int e1, int e2) {
        int s1 = find(e1);
        int s2 = find(e2);
        if (s1==s2) return;
        if(h[s1]<h[s1]) parents[s1]= s2;
        else {
            parents[s2]=s1;
            if(h[s1] == h[s2]) h[s1]++;
        }
    }
    private int find (int e) {
        if (parents[e]==e) return e;
        int p = parents[e];
        int root=  find(p);
        parents[e]= root;
        return root;
        
    }
    private boolean sameSubsets(int e1, int e2) {
        return find(e1)==find(e2);
    }
    private void kruskal(){
        
        parents = new int[cantidadNodos];
        h = new int[cantidadNodos];
        for( int i=0; i<cantidadNodos; i++) {
            parents[i] =i;
            h[i]=1;
        }
        List<Arista> mst = new ArrayList<>();
        int totalPeso = 0;
        for (int i =0; i<grafo.size();i++){
            Arista eje = grafo.get(i);
            Boolean b = sameSubsets(eje.v1, eje.v2);
            if (!b){
                mst.add(eje);
                totalPeso += eje.peso;
                union(eje.v1, eje.v2);

            }
        }
        System.out.println("Vias minimas necesarias:");
        for (Arista arista : mst) {
            System.out.println("Esquina1: " + arista.v1 + ", Esquina2: " + arista.v2 + ", Costo: " + arista.peso);
        }
        System.out.println("Costo total de la construccion de la doble via: " + totalPeso);
    }
}




class Arista {
    int v1;
    int v2;
    int peso;

    public Arista(int v1, int v2, int peso) {
        this.v1 = v1;
        this.v2= v2;
        this.peso = peso;
    }
}

