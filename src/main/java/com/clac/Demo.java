package com.clac;

public class Demo {

    public static void main(String[] args) {

        int[] arr1={1,6,8,9};
        int[] arr2={2,3,8};
        int[] arr3={3,6};
        int[] arr4={3,5};
        int[] arr5={2,4,5,9};

        for (int i : arr1) {
            for (int j : arr2) {
                for (int k : arr3) {
                    for (int l : arr4) {
                        for (int m : arr5) {
                            int sum = i+j+k+l+m;
                            System.out.println(i+":"+j+":"+k+":"+l+":"+m+":"+sum);
                        }
                    }
                }
            }
        }
    }
}
