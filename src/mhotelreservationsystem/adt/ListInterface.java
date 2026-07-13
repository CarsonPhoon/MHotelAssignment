/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mhotelreservationsystem.adt;

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
}