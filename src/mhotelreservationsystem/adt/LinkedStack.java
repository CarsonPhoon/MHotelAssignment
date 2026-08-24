/*
 * LinkedStack - Adapted from course materials (TARUMT DSA)
 */
package mhotelreservationsystem.adt;

public class LinkedStack<T> implements StackInterface<T>{
    
    private Node<T> topNode;
    private int size;

    public LinkedStack(){
        this.topNode = null;
        this.size = 0;
    }

    @Override
    public void push(T newEntry){
        Node<T> newNode = new Node<>(newEntry);
        newNode.next = topNode;
        topNode = newNode;
        size++;
    }

    @Override
    public T pop(){
        if (isEmpty()){
            throw new RuntimeException("Stack is empty, fail remove."); // warn
        }
        T data = topNode.data;
        topNode = topNode.next;
        size--;
        return data;
    }

    @Override
    public T peek(){
        if(isEmpty()){
            throw new RuntimeException("Stack is empty, fail remove."); // warn
        }
        return topNode.data;
    }

    @Override
    public boolean isEmpty(){
        return topNode == null;
    }

    @Override
    public void clear(){
        topNode = null;
        size = 0;
    }

    public int getSize(){
        return size;
    }

    private class Node<T> {
        private T data;
        private Node<T> next;

        private Node(T data){
            this.data = data;
            this.next = null;
        }
    }
}
