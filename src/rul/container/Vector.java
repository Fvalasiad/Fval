package rul.container;

import rul.iterator.ForwardIterator;
import rul.iterator.RandomAccessIterator;

import java.util.Iterator;

public class Vector<T> extends Container<T> {
    private Object[] arr;
    private int capacity;

    public Vector(){
        this(1);
    }

    public Vector(int capacity){
        this.capacity = capacity;
        arr = new Object[capacity];
    }

    public Vector(ForwardIterator<T> first, ForwardIterator<T> last){
        this();

        for(var it = first.clone(); !it.equals(last); it.inc()){
            //pushBack(it.get());
        }
    }

    @Override
    Iterator<T> begin() {
        return null;
    }

    @Override
    Iterator<T> end() {
        return null;
    }

    //Capacity

    public int capacity(){
        return capacity;
    }

    /**
     * Requests that the vector capacity be at least enough to contain n elements.
     *
     * If n is greater than the current vector capacity, the function causes the container to reallocate its
     * storage increasing its capacity to n.
     *
     * In all other cases, the function call does not cause a reallocation and the vector capacity is not affected.
     *
     * This function has no effect on the vector size and cannot alter its elements.
     * @param n Requested capacity.
     * @complexity If a reallocation happens O(n), constant otherwise.
     */
    public void reserve(int n){
        if(n > this.capacity){
            reallocate(n);
        }
    }

    /**
     * Requests the container to reduce its capacity to fit its size.
     * This may cause a reallocation, but has no effect on the vector size and cannot alter its elements.
     *
     * @complexity If a reallocation happens O(n), constant otherwise.
     */
    public void shrinkToFit(){
        if(capacity > size){
            reallocate(size);
        }
    }

    public void reallocate(int capacity){
        Object[] newArr = new Object[capacity];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        arr = newArr;
    }

    //Element Access

    public T get(int index){
        @SuppressWarnings("unchecked") var obj = (T) arr[index];
        return obj;
    }

    public void set(int index, T data){
        arr[index] = data;
    }

    //Modifiers

    void insert(int index, T data){
        Object temp;
        Object insert = data;
        for(int i = index; i < size; i = i + 1){
            temp = arr[i];
            arr[i] = insert;
            insert = temp;
        }
        arr[size++] = insert;
    }

    void pushBack(T data){
        if(capacity == size){
            reallocate(capacity * 2);
        }
        arr[size++] = data;
    }



    class VIterator implements RandomAccessIterator<T> {

        private int index;

        private VIterator(int index){
            this.index = index;
        }

        @Override
        public T previous() {
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public RandomAccessIterator<T> dec() {
            --index;
            return this;
        }

        @Override
        public T get() {
            @SuppressWarnings("unchecked") var obj = (T) arr[index];
            return obj;
        }

        @Override
        public void set(T t) {
            arr[index] = t;
        }

        @Override
        public RandomAccessIterator<T> inc() {
            ++index;
            return this;
        }

        @Override
        public RandomAccessIterator<T> clone() {
            //super.clone();
            return new VIterator(index);
        }

        @Override
        public void advance(long n) {

        }

        @Override
        public RandomAccessIterator<T> add(long n) {
            return null;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }
    }
}
