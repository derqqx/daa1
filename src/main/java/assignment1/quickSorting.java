package assignment1;

import java.util.Random;

public class quickSorting {
    private static final int INSERTION_SORT_THRESHOLD = 16;
    private static final Random RANDOM = new Random();

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        shuffle(arr);
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int l, int r) {
        while (l < r) {
            if (r - l + 1 <= INSERTION_SORT_THRESHOLD) {
                insertionSort(arr, l, r);
                return;
            }

            int p = partition(arr, l, r);

            if (p - l < r - p) {
                quickSort(arr, l, p - 1);
                l = p + 1;
            } else {
                quickSort(arr, p + 1, r);
                r = p - 1;
            }
        }
    }

    private static int partition(int[] arr, int l, int r) {
        // Рандомизированный pivot для повышения средней производительности
        int randomIndex = l + RANDOM.nextInt(r - l + 1);
        swap(arr, randomIndex, r);

        int pivot = arr[r];
        int i = l - 1;

        for (int j = l; j < r; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, r);
        return i + 1;
    }

    private static void insertionSort(int[] a, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            int key = a[i], j = i - 1;
            while (j >= l && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    private static void shuffle(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            swap(a, i, j);
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}