package gameElements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**

 The SortedScoreList class represents a sorted list of integer scores. The list has a fixed maximum size
 of 5 elements, and any element added to the list that exceeds its maximum size will replace the smallest
 element in the list if its value is greater than the smallest element.
 */
public class SortedScoreList {
    private final int maxSize;
    private final ArrayList<Integer> list;

    /**
     * Constructs a SortedScoreList object with a maximum size of 5 elements.
     */
    public SortedScoreList() {
        maxSize = 5;
        list = new ArrayList<>(maxSize);
    }

    /**

     Adds an integer score value to the sorted list. If the list is not at maximum capacity, the value is added directly
     to the list. If the list is at maximum capacity, the smallest value in the list is replaced with the new value if
     the new value is greater than the smallest value, and the list remains sorted in descending order. If the new value is
     smaller than or equal to all existing values in the list, it is not added to the list.
     @param value the integer score value to add to the list
     */
    public void add(Integer value) {
        if (list.size() < maxSize) {
            list.add(value);
        } else {
            int index = -1;
            for (int i = 0; i < list.size() - 1; i++) {
                if (value > list.get(i)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                for (int i = list.size() - 1; i > index; i--) {
                    list.set(i, list.get(i - 1));
                }
                list.set(index, value);
            } else if (value > list.get(list.size() - 1)) {
                list.set(list.size() - 1, value);
            }
        }
        list.sort(Collections.reverseOrder());
    }

    public void clear(){
        list.clear();
        for (int i = 0; i < maxSize; i++){
            list.add(0);
        }
    }


    /**
     * Retrieves the integer score value at the specified index in the list.
     *
     * @param index the index of the score value to retrieve
     * @return the integer score value at the specified index
     */
    public Integer get(int index) {
        return list.get(index);
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public int getMaxSize(){
        return maxSize;
    }

    public int size(){
        return list.size();
    }
}