package com.example.guocaicgc.ivantest;

import android.support.test.runner.AndroidJUnit4;

import com.example.guocaicgc.ivantest.algorithm.AlgorithmUtils;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by guocai.cgc on 2017/8/2.
 */
@RunWith(AndroidJUnit4.class)
public class AlgorithmUtilsTest {

    @Test
    public void testBubblingSort() {
        List<Integer> toSortList = createListWithMinEnd();
        toSortList = AlgorithmUtils.bubblingSort(toSortList);
        checkSorted(toSortList);

        toSortList = createListWithMaxStart();
        toSortList = AlgorithmUtils.bubblingSort(toSortList);
        checkSorted(toSortList);

        toSortList = createRandomList();
        toSortList = AlgorithmUtils.bubblingSort(toSortList);
        checkSorted(toSortList);
    }

    @Test
    public void testBubblingSortBad() {
        List<Integer> toSortList = createListWithMinEnd();
        toSortList = AlgorithmUtils.bubblingSortBad(toSortList);
        checkSorted(toSortList);

        toSortList = createListWithMaxStart();
        toSortList = AlgorithmUtils.bubblingSortBad(toSortList);
        checkSorted(toSortList);

        toSortList = createRandomList();
        toSortList = AlgorithmUtils.bubblingSortBad(toSortList);
        checkSorted(toSortList);
    }

    @Test
    public void testQuickSort() {
        List<Integer> toSortList = createListWithMinEnd();
        toSortList = AlgorithmUtils.quickSort(toSortList, 0, toSortList.size() - 1);
        checkSorted(toSortList);

        toSortList = createListWithMaxStart();
        toSortList = AlgorithmUtils.quickSort(toSortList, 0, toSortList.size() - 1);
        checkSorted(toSortList);

        toSortList = createRandomList();
        toSortList = AlgorithmUtils.quickSort(toSortList, 0, toSortList.size() - 1);
        checkSorted(toSortList);
    }

    private List<Integer> createListWithMinEnd() {
        List<Integer> toSortList = new ArrayList<>();
        toSortList.add(22);
        toSortList.add(1);
        toSortList.add(2);
        toSortList.add(4);
        toSortList.add(0);
        toSortList.add(99);
        toSortList.add(-1);
        return toSortList;
    }

    private List<Integer> createListWithMaxStart() {
        List<Integer> toSortList = new ArrayList<>();
        toSortList.add(100);
        toSortList.add(1);
        toSortList.add(2);
        toSortList.add(4);
        toSortList.add(0);
        toSortList.add(99);
        toSortList.add(-1);
        return toSortList;
    }

    private List<Integer> createRandomList() {
        List<Integer> toSortList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i <50; i++) {
            toSortList.add(random.nextInt());
        }
        return toSortList;
    }

    private void checkSorted(List<Integer> list) {
        boolean isSorted = true;
        Iterator<Integer> it = list.iterator();
        int lastValue = Integer.MIN_VALUE;
        int curValue;
        while (it.hasNext()) {
            curValue = it.next();
            if (lastValue > curValue) {
                // 上一个值比当前值大， 说明没有排序成功
                isSorted = false;
                break;
            }
            lastValue = curValue;
        }
        Assert.assertTrue(isSorted);
    }
}
