package assignment1;

import java.util.Arrays;

public class DeterministicSelect {
    public static int select(int[] arr, int k) {
        if (arr == null || arr.length == 0) throw new IllegalArgumentException("empty");
        if (k < 0 || k >= arr.length) throw new IllegalArgumentException("k out of range");
        return select(arr, 0, arr.length - 1, k);
    }

    private static int select(int[] a, int l, int r, int k) {
        while (true) {
            if (l == r) return a[l];
            int pivotVal = medianOfMedians(a, l, r);
            int p = partitionAroundValue(a, l, r, pivotVal);
            if (k == p) return a[p];
            if (k < p) r = p - 1; else l = p + 1;
        }
    }

    private static int medianOfMedians(int[] a, int l, int r) {
        int n = r - l + 1;
        if (n <= 5) {
            Arrays.sort(a, l, r + 1);
            return a[l + n / 2];
        }
        int groups = (n + 4) / 5;
        for (int i = 0; i < groups; i++) {
            int s = l + i * 5;
            int e = Math.min(s + 4, r);
            Arrays.sort(a, s, e + 1);
            int medianIdx = s + (e - s) / 2;
            swap(a, l + i, medianIdx);    // перенос медиан групп в начало
        }
        // медиана медиан
        return select(a, l, l + groups - 1, l + groups / 2);
    }

    private static int partitionAroundValue(int[] a, int l, int r, int pivotVal) {
        int i = l, j = r;
        while (i <= j) {
            while (i <= r && a[i] < pivotVal) i++;
            while (j >= l && a[j] > pivotVal) j--;
            if (i <= j) { swap(a, i, j); i++; j--; }
        }
        return i - 1; // одна из позиций для pivot
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}