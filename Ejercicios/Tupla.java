public class Tupla {
    public int destino;
    public int peso;

    public Tupla(int destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public int getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        // Modificar el método toString para devolver un formato legible
        return "(" + destino + ", " + peso + ")";
    }
}
