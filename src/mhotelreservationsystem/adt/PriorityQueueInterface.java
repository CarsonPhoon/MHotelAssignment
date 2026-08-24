/*
 * PriorityQueueInterface - Adapted from course materials (TARUMT DSA)
 */
package mhotelreservationsystem.adt;
/**
 *
 * @author zekai
 */

public interface PriorityQueueInterface<T extends Comparable<T>> {
    
    public void enqueue(T newEntry);
    
    public T dequeue();
  
    public T peek();
   
    public boolean isEmpty();
   
    public void clear();
   
    public int getNumberOfElements();
}