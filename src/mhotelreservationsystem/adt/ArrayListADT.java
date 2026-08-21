/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.adt;

import java.util.Iterator;

/**
 *
 * @author phoon
 */
public class ArrayListADT<T> implements ListInterface<T> {

    private static final int DEFAULT_CAPACITY = 20;
    private T[] array;
    private int numberOfEntries;
    @SuppressWarnings("unchecked")
    
    public ArrayListADT() {

        array = (T[]) new Object[DEFAULT_CAPACITY];
        numberOfEntries = 0;
    }
    
    @Override
    public boolean add(T newEntry) {

        ensureCapacity();
        array[numberOfEntries] = newEntry;
        numberOfEntries++;
        return true;
    }
    
    @Override
    public boolean add(int position, T newEntry) {

        if(position < 0 || position > numberOfEntries){
            return false;
        }

        ensureCapacity();

        for(int i = numberOfEntries; i > position; i--){
            array[i] = array[i-1];
        }

        array[position] = newEntry;
        numberOfEntries++;
        return true;
    }
    
    @Override
    public T remove(int position) {

        if(position<0 || position>=numberOfEntries){
            return null;
        }

        T removed=array[position];
        for(int i=position;i<numberOfEntries-1;i++){
            array[i]=array[i+1];
        }
        array[numberOfEntries-1]=null;
        numberOfEntries--;
        return removed;
    }
    
    @Override
    public T replace(int position, T newEntry) {

        if(position<0 || position>=numberOfEntries){
            return null;
        }
        T oldEntry=array[position];
        array[position]=newEntry;
        return oldEntry;
    }
    
    @Override
    public boolean contains(T anEntry) {

        for(int i=0;i<numberOfEntries;i++){
            if(array[i].equals(anEntry)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public T get(int position) {

        if (position < 0 || position >= numberOfEntries) {
            return null;
        }

        return array[position];
    }

    @Override
    public int getNumberOfEntries() {

        return numberOfEntries;
    }
    
    @Override
    public boolean isEmpty() {

        return numberOfEntries == 0;
    }

    @Override
    public void clear() {

        for(int i=0;i<numberOfEntries;i++){
            array[i]=null;
        }
        numberOfEntries=0;
    }
    
    public void display() {

        if(isEmpty()){
            System.out.println("List is empty.");
            return;
        }

        for(int i=0;i<numberOfEntries;i++){
            System.out.println(array[i]);
        }
    }
   
    // Returns a copy of the internal array (up to numberOfEntries)
    @SuppressWarnings("unchecked")
    public T[] toArray() {

        T[] copy = (T[]) new Object[numberOfEntries];
        for (int i = 0; i < numberOfEntries; i++) {
            copy[i] = array[i];
        }
        return copy;
    }
    
    // Doubles the array capacity when the array is full and copies all existing elements to a new array of double the size. 
    @SuppressWarnings("unchecked")
    private void ensureCapacity() {

        if (numberOfEntries >= array.length) {

            T[] newArray = (T[]) new Object[array.length * 2];
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }
    }
    
    @Override
    public Iterator<T> getIterator() {
        return new ArrayListIterator();
    }
    
    private class ArrayListIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < numberOfEntries;
        }

        @Override
        public T next() {
            if (!hasNext()) return null;
            T element = array[currentIndex];
            currentIndex++;
            return element;
        }
    }
}