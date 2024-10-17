Trabajo realizado por: Lina Muñoz 202310172 y Ximena Lopez 202312848

Al interior del comprimido se encuentran estas 4 cosas:
1. Ejercicios: Carpeta con 4 archivos .java cada uno con el programa para resolver los 4 problemas
2. data: Archivos .txt que reprensentan los casos de prueba que entran por parametro al correr los ejercicios.
3. Informe: Archivo .pdf con el análisis de tiempos para la parte 1
4. README

Para probar cada uno de los programas es necesario tener en cuenta las siguientes cosas:

1. Ejercicio 1:
   
Para este ejercicio si se corre desde la terminal se debe de compilar la clase PrimerPunto.java, junto a la ejecución.

Entradas: Tiene 2, la primera es la ruta del archivo y la segunda es el algoritmo a ejecutar. Para este ultimo, 1 es dijkstra, el 2 es Bellmand-ford y 3 si se quiere floyd Warshall.

Su salida es una matriz de lista de adyacencias donde se ve el peso de camino minimo para llegar desde cada nodo a los otros. 

3. Ejercicio 2: BFS para encontrar componentes conectados.

Para probar este programa es necesario correr el archivo Eje2.java, el main requiere un argumento: args[0]: ruta del archivo con el caso.

Entradas: Los casos de prueba en este caso son el grafo de entrada representado como lista de adyacencia. Para este ejercicio se pueden usar los siguientes argumentos: data/eje2Grafo1.txt data/eje2Grafo2.txt

Salida: Por consola se imprime el grafo ingresado, y los componentes conectados como una lista de listas. Ej: [[0,1], [2,3], [4]].

3. Ejercicio 3:

Para probar este ejercicio es necesario correr el archivo TercerPunto.java, y su ejecución que requiere un argumento, el cual es la ruta del archivo. 

Entradas: Un archivo txt que contiene 3 números los cuales son el primero un vértice, el segundo el otro vértice con el que conecta y finalmente el peso de esta arista.

Salida, la salida son las aristas que entran para el recubrimiento mínimo y el costo total mínimo que tiene. 


5. Ejercicio 4: Libros que se pueden transportar como máximo en un dia desde las fábricas hasta las librerías.

Para probar este programa es necesario correr el archivo Eje4.java, el main requiere un argumento: args[0]: ruta del archivo con el caso.

Entradas: Los casos de prueba en este caso son archivos txt, donde la primera linea contiene 2 enteros, el primero es la cantidad de fabricas y el segundo la cantidad de librerias. Las siquientes lineas son el grafo de entrada representado como matriz de capacidades, donde cada entrada a la matriz [i][j] representa la capacidad del camión entre el punto i y el punto j, y en caso de que no exista eje entre los dos vertices la capacidad en la matriz sera 0. Es importante mencionar que de esta matriz las primeras lineas son siempre fabricas (fuentes) y las últimas lineas son siempre librerias (sumideros).
Para este ejercicio se pueden usar los siguientes argumentos: data/eje4Grafo1.txt data/eje4Grafo2.txt

Salida: Por consola se imprime el grafo ingresado, y la cantidad de libros que se pueden transportar por esa red.
