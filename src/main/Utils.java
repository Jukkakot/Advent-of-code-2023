package main;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Integer> indexesAround(List<?> list, int width, int index) {
        int rows = list.size() / width;
        int cols = width;

        int row = index / width;
        int col = index % width;

        List<Integer> result = new ArrayList<>();

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if ((i >= 0 && i < rows) && (j >= 0 && j < cols) && !(i == row && j == col)) {
                    // Ensure not to wrap around edges
                    if ((i == row - 1 || i == row + 1 || j == col - 1 || j == col + 1) ||
                            (i == row && j == col)) {
                        int newIndex = i * width + j;
                        result.add(newIndex);
                    }
                }
            }
        }

        return result;
    }

    public static void printListWithElementsPerRow(List<? extends Printable> list, int elementsPerRow) {
        int size = list.size();
        int printedElements = 0;

        for (Printable item : list) {
            item.print();
            printedElements++;

            if (printedElements % elementsPerRow == 0 || printedElements == size) {
                System.out.println();
            }
        }
    }
}
