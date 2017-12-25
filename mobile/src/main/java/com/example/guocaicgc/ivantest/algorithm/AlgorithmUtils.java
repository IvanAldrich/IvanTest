package com.example.guocaicgc.ivantest.algorithm;

import java.util.List;
import java.util.Timer;

/**
 * Created by guocai.cgc on 2017/8/1.
 */
public class AlgorithmUtils {

    // = = = = = 插入排序 = = = = =

//    public static final List<Integer>
    // = = = = = 插入排序 END = = = = =

    /**
     * 冒泡，从小到大排序
     *
     * @param toSortList
     * @return
     */
    public static final List<Integer> bubblingSort(List<Integer> toSortList) {
        Integer tmp = 0;
        for (int i = toSortList.size(); i > 1; i--) {
            for (int j = 0; j < i - 1; j++) {
                if (toSortList.get(j) > toSortList.get(j + 1)) {
                    tmp = toSortList.get(j);
                    toSortList.set(j, toSortList.get(j + 1));
                    toSortList.set(j + 1, tmp);
                }
            }
        }
        return toSortList;
    }

    public static final List<Integer> bubblingSortBad(List<Integer> toSortList) {
        Integer tmp = 0;
        for (int i = 0, size = toSortList.size(); i < size - 1; i++) {
            for (int j = 0; j < size - i -1; j++) {
                if (toSortList.get(j) > toSortList.get(j + 1)) {
                    tmp = toSortList.get(j);
                    toSortList.set(j, toSortList.get(j + 1));
                    toSortList.set(j + 1, tmp);
                }
            }
        }
        return toSortList;
    }

    public static List<Integer> quickSort(List<Integer> toSortList, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int pivotIndex = partition(toSortList, leftIndex, rightIndex);
            quickSort(toSortList, leftIndex, pivotIndex - 1);
            quickSort(toSortList, pivotIndex + 1, rightIndex);
        }
        return toSortList;
    }

    private static int partition(List<Integer> toSortList, int leftIndex, int rightIndex) {
        int pivotValue = toSortList.get(leftIndex);
        while (leftIndex < rightIndex) {
            while (toSortList.get(rightIndex) >= pivotValue && rightIndex > leftIndex) {
                rightIndex--;
            }
            // 从右边往左获取第一个比枢纽小的数值，将其放到空闲的位置（第一次循环为枢纽的位置）。此后rightIndex位置空闲出来
            toSortList.set(leftIndex, toSortList.get(rightIndex));
            while (toSortList.get(leftIndex) <= pivotValue && leftIndex < rightIndex) {
                leftIndex++;
            }
            // 从左边往右获取第一个比枢纽大的数值，将其放到空闲的位置（即上一次的rightIndex）。此后leftIndex位置空闲出来
            toSortList.set(rightIndex, toSortList.get(leftIndex));
        }
        // 此时leftIndex==rightIndex，即为枢纽所在位置
        toSortList.set(leftIndex, pivotValue);
        return leftIndex;
    }
}
