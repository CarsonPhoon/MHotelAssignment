/*
 * BSTInterface - Adapted from course materials (TARUMT DSA)
 */
package mhotelreservationsystem.adt;

/**
 * Generic Binary Search Tree
 *   insert  - O(log n) / O(n)
 *   search  - O(log n) / O(n)
 *   remove  - O(log n) / O(n)
 *   isEmpty - O(1)
 *   clear   - O(1)
 *   getSize - O(1)
 */

public interface BSTInterface<T, K extends Comparable<K>> {

    boolean insert(T data);

    T search(K key);

    boolean remove(K key);

    boolean isEmpty();

    void clear();

    int getSize();
}