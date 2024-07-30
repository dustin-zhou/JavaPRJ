package com.clac;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] arr1 = {1, 6, 8, 9};
        int[] arr2 = {2, 3, 8};
        int[] arr3 = {6};
        int[] arr4 = {3, 5};
        int[] arr5 = {2, 4, 5, 9};

        List<int[]> arrays = new ArrayList<>();
        arrays.add(arr1);
        arrays.add(arr2);
        arrays.add(arr3);
        arrays.add(arr4);
        arrays.add(arr5);

        // 存储所有可能的和值及其对应的数组组合
        List<Result> results = new ArrayList<>();

        // 生成所有可能的组合并计算和值
        generateCombinations(arrays, results, new ArrayList<>(), 0);

        // 按和值排序，如果和值相同则按组合的第一个数组排序
        results.sort((a, b) -> {
            if (a.sum != b.sum) {
                return a.sum - b.sum;
            } else {
                return compareArrays(a.arrays.get(0), b.arrays.get(0));
            }
        });

        // 打印结果
        for (Result result : results) {
            System.out.println("Sum: " + result.sum + ", Arrays: " + result.arrays);
        }
    }

    private static void generateCombinations(List<int[]> arrays, List<Result> results, List<int[]> currentCombination, int startIndex) {
        if (!currentCombination.isEmpty()) {
            int sum = currentCombination.stream().flatMapToInt(Arrays::stream).sum();
            results.add(new Result(sum, new ArrayList<>(currentCombination)));
        }
        for (int i = startIndex; i < arrays.size(); i++) {
            currentCombination.add(arrays.get(i));
            generateCombinations(arrays, results, currentCombination, i + 1);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    private static int compareArrays(int[] arr1, int[] arr2) {
        for (int i = 0; i < Math.min(arr1.length, arr2.length); i++) {
            if (arr1[i] != arr2[i]) {
                return arr1[i] - arr2[i];
            }
        }
        return arr1.length - arr2.length;
    }

    static class Result {
        int sum;
        List<int[]> arrays;

        Result(int sum, List<int[]> arrays) {
            this.sum = sum;
            this.arrays = arrays;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int[] arr : arrays) {
                sb.append(Arrays.toString(arr)).append(", ");
            }
            if (!arrays.isEmpty()) {
                sb.setLength(sb.length() - 2);
            }
            sb.append("]");
            return sb.toString();
        }
    }
}