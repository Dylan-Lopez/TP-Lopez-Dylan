package funciones;

import java.util.Random;

public class Funciones {

	 // Genera un array de enteros aleatorios dentro de un rango especificado
    public static int[] generarArrayAleatorio(int n, int min, int max) {
        // Declaración del array
        int[] arr = new int[n];
        
        // Generación de números aleatorios
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(max - min + 1) + min;
        }
        
        return arr;
    }
    
    public static void quickSort(int[] arr, int low, int high) {
		if (low < high) {
			int pi = partition(arr, low, high);
			quickSort(arr, low, pi - 1);
			quickSort(arr, pi + 1, high);
		}
	}

	public static int partition(int[] arr, int low, int high) {
		int pivot = arr[high];
		int i = low - 1;
		for (int j = low; j < high; j++) {
			if (arr[j] < pivot) {
				i++;
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}
		int temp = arr[i + 1];
		arr[i + 1] = arr[high];
		arr[high] = temp;
		return i + 1;
	}
	
	
	
}

