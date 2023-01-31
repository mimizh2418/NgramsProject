package sorting;

import java.util.List;

/** This is an implementation of the classic in-place quicksort
 * algorithm, using the Hoare partition algorithm. It selects a pivot
 * by choosing the median of the first, middle, and last element
 * of the sorted list
 */
public class QuickSort implements SortingAlgorithm {

    private <T extends Comparable<T>> T getPivot(List<T> list) {
        T first = list.get(0);
        T middle = list.get(list.size() / 2);
        T last = list.get(list.size() - 1);
        return medianOfThree(first, middle, last);
    }

    private <T extends Comparable<T>> T medianOfThree(T n1, T n2, T n3) {
        if (n1.compareTo(n2) > 0) {
            if (n2.compareTo(n3) > 0) return n2;
            else if (n1.compareTo(n3) > 0) return n3;
            else return n1;
        } else {
            if (n1.compareTo(n3) > 0) return n1;
            else if (n2.compareTo(n3) > 0) return n3;
            else return n2;
        }
    }

    private <T extends Comparable<T>> void swap(List<T> list, int i1, int i2) {
        T temp = list.get(i1);
        list.set(i1, list.get(i2));
        list.set(i2, temp);
    }

    @Override
    public <T extends Comparable<T>> void sort(List<T> list) {
        if (list.size() == 0) return;
        T pivot = getPivot(list);
        int left = 0;
        int right = list.size() - 1;
        while (true) {
            while (list.get(left).compareTo(pivot) < 0) left++;
            while (list.get(right).compareTo(pivot) > 0) right--;
            if (left < right) {
                swap(list, left, right);
                left++;
                right--;
            } else break;
        }
        if (list.size() > 2) {
            sort(list.subList(0, right + 1));
            sort(list.subList(left, list.size()));
        }
    }
}
