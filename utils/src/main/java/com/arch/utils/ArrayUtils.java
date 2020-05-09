package com.arch.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by shishoufeng on 2019/12/11.
 * <p>
 * desc : 数组 和 集合 工具类集合
 */
public final class ArrayUtils {

    public static <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }
    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length <= 0;
    }

    public static <E> boolean isEmpty(List<E> list) {
        return list == null || list.size() <= 0;
    }

    public static boolean isEmpty(int[] ints) {
        return ints == null || ints.length <= 0;
    }

    public static boolean isEmpty(float[] floats) {
        return floats == null || floats.length <= 0;
    }

    public static boolean isEmpty(String[] strings) {
        return strings == null || strings.length <= 0;
    }

    public static boolean isEmpty(double[] doubles) {
        return doubles == null || doubles.length <= 0;
    }

    public static boolean isEmpty(boolean[] booleans) {
        return booleans == null || booleans.length <= 0;
    }

    public static <E> int getSize(Collection<E> collection) {
        return isEmpty(collection) ? 0 : collection.size();
    }

    public static <E> E getIndexObject(List<E> list, int index) {
        if (!isEmpty(list)) {
            if (index >= 0 && index < list.size()) {
                return list.get(index);
            }
        }
        return null;
    }

    /**
     * Return the size of array.
     *
     * @param array The array.
     * @return the size of array
     */
    public static int getLength(Object array) {
        if (array == null) return 0;
        return Array.getLength(array);
    }

    /**
     * Return whether the two arrays are equals.
     *
     * @param a  One array.
     * @param a2 The other array.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean equals(Object[] a, Object[] a2) {
        return Arrays.deepEquals(a, a2);
    }

    public static boolean equals(boolean[] a, boolean[] a2) {
        return Arrays.equals(a, a2);
    }

    public static boolean equals(byte[] a, byte[] a2) {
        return Arrays.equals(a, a2);
    }

    public static boolean equals(char[] a, char[] a2) {
        return Arrays.equals(a, a2);
    }

    public static boolean equals(double[] a, double[] a2) {
        return Arrays.equals(a, a2);
    }

    public static boolean equals(float[] a, float[] a2) {
        return Arrays.equals(a, a2);
    }

    public static boolean equals(int[] a, int[] a2) {
        return Arrays.equals(a, a2);
    }

    public static boolean equals(short[] a, short[] a2) {
        return Arrays.equals(a, a2);
    }


    ///////////////////////////////////////////////////////////////////////////
    // reverse
    ///////////////////////////////////////////////////////////////////////////

    /**
     * <p>Reverses the order of the given array.</p>
     *
     * <p>There is no special handling for multi-dimensional arrays.</p>
     *
     * <p>This method does nothing for a <code>null</code> input array.</p>
     *
     * @param array the array to reverse, may be <code>null</code>
     */
    public static <T> void reverse(T[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        T tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(long[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        long tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(int[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        int tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(short[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        short tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(char[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        char tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(byte[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        byte tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(double[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        double tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(float[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        float tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(boolean[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        boolean tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // copy
    ///////////////////////////////////////////////////////////////////////////

    /**
     * <p>Copies the specified array and handling
     * <code>null</code>.</p>
     *
     * <p>The objects in the array are not cloned, thus there is no special
     * handling for multi-dimensional arrays.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array the array to shallow clone, may be <code>null</code>
     * @return the cloned array, <code>null</code> if <code>null</code> input
     */
    public static <T> T[] copy(T[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    public static long[] copy(long[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    public static int[] copy(int[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    public static short[] copy(short[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    public static char[] copy(char[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    public static byte[] copy(byte[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    public static double[] copy(double[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    public static float[] copy(float[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    public static boolean[] copy(boolean[] array) {
        if (array == null) return null;
        return subArray(array, 0, array.length);
    }

    private static Object realCopy(Object array) {
        if (array == null) return null;
        return realSubArray(array, 0, getLength(array));
    }

    ///////////////////////////////////////////////////////////////////////////
    // subArray
    ///////////////////////////////////////////////////////////////////////////

    public static <T> T[] subArray(T[] array, int startIndexInclusive, int endIndexExclusive) {
        //noinspection unchecked
        return (T[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    public static long[] subArray(long[] array, int startIndexInclusive, int endIndexExclusive) {
        return (long[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    public static int[] subArray(int[] array, int startIndexInclusive, int endIndexExclusive) {
        return (int[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    public static short[] subArray(short[] array, int startIndexInclusive, int endIndexExclusive) {
        return (short[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    public static char[] subArray(char[] array, int startIndexInclusive, int endIndexExclusive) {
        return (char[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    public static byte[] subArray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        return (byte[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    public static double[] subArray(double[] array, int startIndexInclusive, int endIndexExclusive) {
        return (double[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    public static float[] subArray(float[] array, int startIndexInclusive, int endIndexExclusive) {
        return (float[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    public static boolean[] subArray(boolean[] array, int startIndexInclusive, int endIndexExclusive) {
        return (boolean[]) realSubArray(array, startIndexInclusive, endIndexExclusive);
    }

    private static Object realSubArray(Object array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        int length = getLength(array);
        if (endIndexExclusive > length) {
            endIndexExclusive = length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        Class type = array.getClass().getComponentType();
        if (newSize <= 0) {
            return Array.newInstance(type, 0);
        }
        Object subArray = Array.newInstance(type, newSize);
        System.arraycopy(array, startIndexInclusive, subArray, 0, newSize);
        return subArray;
    }

    /**
     * Return the string of array.
     *
     * @param array The array.
     * @return the string of array
     */
    public static String toString(Object array) {
        if (array == null) return "null";
        if (array instanceof Object[]) {
            return Arrays.deepToString((Object[]) array);
        } else if (array instanceof boolean[]) {
            return Arrays.toString((boolean[]) array);
        } else if (array instanceof byte[]) {
            return Arrays.toString((byte[]) array);
        } else if (array instanceof char[]) {
            return Arrays.toString((char[]) array);
        } else if (array instanceof double[]) {
            return Arrays.toString((double[]) array);
        } else if (array instanceof float[]) {
            return Arrays.toString((float[]) array);
        } else if (array instanceof int[]) {
            return Arrays.toString((int[]) array);
        } else if (array instanceof long[]) {
            return Arrays.toString((long[]) array);
        } else if (array instanceof short[]) {
            return Arrays.toString((short[]) array);
        }else{
            return "null";
        }
    }

    /**
     *
     * 按照顺序 比较两个集合 数据是否完全一致
     *
     * @param collA A 集合
     * @param collB B 集合
     * @param <E> 数据
     * @return true 数据完全一致 false 数据不一致
     */
    public static <E> boolean containsOrderAll(Collection<E> collA,Collection<E> collB){
        if (collA == null || collB == null){
            return false;
        }
        if (collA.size() != collB.size()){
            return false;
        }
        Iterator<E> iteratorA = collA.iterator();
        Iterator<E> iteratorB = collB.iterator();

        E a,b;
        while (iteratorA.hasNext()){
            a = iteratorA.next();
            b = iteratorB.next();

            if (a == null && b == null){
                continue;
            }
            if (a == null || !a.equals(b)){
                return false;
            }
        }
        return true;
    }


}
