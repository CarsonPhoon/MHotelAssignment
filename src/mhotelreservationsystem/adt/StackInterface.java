/*
 * StackInterface - Adapted from course materials (TARUMT DSA)
 */
package mhotelreservationsystem.adt;

/**
 *
 * @author user
 */
public interface StackInterface<T> {
    public void push(T newEntry);
    public T pop();
    public T peek();
    public boolean isEmpty();
    public void clear();
}
