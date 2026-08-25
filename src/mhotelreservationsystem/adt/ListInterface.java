/*
 * ListInterface - Adapted from course materials (TARUMT DSA)
 */
package mhotelreservationsystem.adt;

import java.util.Iterator;

/**
 *
 * @author phoon
 */
public interface ListInterface<T> {
    
    boolean add(T newEntry);
    
    boolean add(int position, T newEntry);
    
    T remove(int position);
    
    T get(int position);
    
    T replace(int position, T newEntry);
    
    boolean contains(T anEntry);
    
    int getNumberOfEntries();
    
    boolean isEmpty();
    
    void clear();
    
    Iterator<T> getIterator();
}