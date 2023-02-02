package utils.sorting;

import java.util.List;

/** This class is an implementation of a hybrid utils.sorting algorithm:
 * this is a recursive divide-and-conquer algorithm that performs
 * {@link QuickSort} on longer lists ({@code size > 17}) and uses
 * insertion sort otherwise.
 */
public class HybridSort extends QuickSort {

    @Override
    public <T extends Comparable<T>> void sort(List<T> list) {
        if (list.size() > 17) {
            super.sort(list);
        } else {
            SortingAlgorithm sorter = new InsertionSort();
            sorter.sort(list);
        }
    }
}
