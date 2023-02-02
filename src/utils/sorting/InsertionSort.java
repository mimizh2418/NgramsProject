package utils.sorting;

import java.util.List;

/** This class implements a classic insertion sort algorithm.
 */
public class InsertionSort implements SortingAlgorithm {

    @Override
    public <T extends Comparable<T>> void sort(List<T> list) {
        for (int i = 1; i < list.size(); i++) {
            T temp = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).compareTo(temp) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, temp);
        }
    }
}
