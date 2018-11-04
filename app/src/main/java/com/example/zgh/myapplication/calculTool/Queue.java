package com.example.zgh.myapplication.calculTool;

public class Queue<T> {
    private int N = 0;
    private Node head;
    private Node now;

    private class Node {
        T data;
        Node next;
    }

    public void enqueue(T data) {
        Node old = now;
        now = new Node();
        now.data = data;
        now.next = null;
        if (head == null)
            head = now;
        else
            old.next = now;
        N++;
    }

    public void appendInFirst(T data) {
        Node newHead = new Node();
        newHead.data = data;
        newHead.next = head;
        head = newHead;
    }

    public T dequeue() {
        T o = head.data;
        head = head.next;
        if (isEmpty())
            now = null;
        N--;
        return o;
    }

    public boolean contains(T key) {
        Node temp = head;
        while (temp != null) {
            if (temp.data.equals(key))
                return true;
            temp = temp.next;
        }
        return false;
    }

    public void clean() {
        this.head = null;
        this.now = null;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

}
