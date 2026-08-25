/*
 * QueueInterface - Adapted from course materials (TARUMT DSA)
 */
package mhotelreservationsystem.adt;

/**
 *
 * @author user
 */
public interface QueueInterface<T> {
    public void enqueue(T newEntry);
    public T dequeue();
    public T getFront();
    public boolean isEmpty();
    public void clear();
}
