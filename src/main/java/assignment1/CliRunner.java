package assignment1;
import java.util.Arrays;

public class CliRunner {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java assignment1.CliRunner <algo> <n>");
            System.out.println("algo = mergesort | quicksort | select");
            return;
        }

        String algo = args[0];
        int n = Integer.parseInt(args[1]);
        int[] arr = new java.util.Random().ints(n).toArray();

        if ("mergesort".equalsIgnoreCase(algo)) {
            mergeSorting.sort(arr);
        } else if ("quicksort".equalsIgnoreCase(algo)) {
            quickSorting.sort(arr);
        } else if ("select".equalsIgnoreCase(algo)) {
            int k = n / 2;
            int result = DeterministicSelect.select(arr, k);
            System.out.println("Median ≈ " + result);
        } else {
            System.out.println("Unknown algo: " + algo);
        }
    }
}
