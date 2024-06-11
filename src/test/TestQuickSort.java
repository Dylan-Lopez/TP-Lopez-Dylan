package test;

import java.util.Arrays;
import funciones.Funciones;

public class TestQuickSort {

    // Define el tamaño del array a ordenar
    private static int size = 100000;

    public static void main(String[] args) {
        // Información de la PC: Obtiene el número de núcleos disponibles
        Runtime runtime = Runtime.getRuntime();
        int numeroNucleos = runtime.availableProcessors();
        System.out.println("Número de procesadores lógicos: " + numeroNucleos + "\n");


        // Genera un array aleatorio para el ordenamiento secuencial
        int[] arraySecuencial = Funciones.generarArrayAleatorio(size, 1, 50000);
        // Genera una copia del array anterior para el ordenamiento concurrente
        int[] arrayConcurrente = Arrays.copyOf(arraySecuencial, arraySecuencial.length);

        // Ordena el array secuencialmente y mide el tiempo que tarda
        long tiempoInicial = System.nanoTime();
        Funciones.quickSort(arraySecuencial, 0, size - 1);
        long tiempoFinal = System.nanoTime() - tiempoInicial;
        // Muestra el tiempo que tardó el ordenamiento secuencial
        System.out.println("El programa secuencial, demoró: " + tiempoFinal + " nanosegundos");

        // Ordena el array concurrentemente y mide el tiempo que tarda
        long tiempoConcurrenteInicial = System.nanoTime();
        quickSortConcurrent(arrayConcurrente, 0, size - 1, numeroNucleos);
        long tiempoConcurrenteFinal = System.nanoTime() - tiempoConcurrenteInicial;
        // Muestra el tiempo que tardó el ordenamiento concurrente
        System.out.println("El programa concurrente, demoró: " + tiempoConcurrenteFinal + " nanosegundos");
    }

    // Método para realizar el ordenamiento concurrente usando QuickSort
    public static void quickSortConcurrent(int[] array, int low, int high, int depth) {
        // Comprueba si el subarray tiene más de un elemento
        if (low < high) {
            // Si la profundidad es menor o igual a 1, realiza QuickSort secuencialmente
            if (depth <= 1) {
                Funciones.quickSort(array, low, high);
            } else {
                // Encuentra el índice del pivote y divide el array en dos partes
                int pivotIndex = Funciones.partition(array, low, high);
                // Crea dos hilos para ordenar cada parte recursivamente
                Thread leftThread = new Thread(() -> quickSortConcurrent(array, low, pivotIndex - 1, depth / 2));
                Thread rightThread = new Thread(() -> quickSortConcurrent(array, pivotIndex + 1, high, depth / 2));
                leftThread.start(); // Inicia el hilo para ordenar la parte izquierda
                rightThread.start(); // Inicia el hilo para ordenar la parte derecha
                try {
                    // Espera a que ambos hilos terminen
                    leftThread.join();
                    rightThread.join();
                } catch (InterruptedException e) {
                    // Muestra la traza de la excepción en caso de interrupción
                    e.printStackTrace();
                }
            }
        }
    }
}