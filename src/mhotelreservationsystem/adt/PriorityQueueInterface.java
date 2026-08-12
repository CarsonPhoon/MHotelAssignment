/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
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