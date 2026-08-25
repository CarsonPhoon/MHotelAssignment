/*
 * LinkedQueue - Adapted from course materials (TARUMT DSA)
 */
package mhotelreservationsystem.adt;

public class LinkedQueue<T> implements QueueInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;

    public LinkedQueue(){
        firstNode = null;
        lastNode = null;
    }

    @Override
    public void enqueue(T newEntry){
        Node newNode = new Node(newEntry, null);
        
        if (isEmpty()){
            firstNode = newNode;
        } else {
            lastNode.next = newNode;
        }

        lastNode = newNode;
    }

    @Override
    public T dequeue(){
        T front = null;

        if(!isEmpty()){
            front = firstNode.data;
            firstNode = firstNode.next;
            if (firstNode == null){
                lastNode = null;
            }
        }

        return front;
    }

    @Override
    public T getFront(){
        T front = null;

        if (!isEmpty()){
            front = firstNode.data;
        }

        return front;
    }

    @Override
    public boolean isEmpty(){
        return (firstNode == null) && (lastNode == null);
    }

    @Override
    public void clear() {
        firstNode = null;
        lastNode = null;
    }

    private class Node<T> {
        private T data;
        private Node next;

        private Node(T data){
            this.data = data;
            this.next = null;
        }

        private Node(T data, Node next){
            this.data = data;
            this.next = next;
        }
    }
}
