package rul.container;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

abstract public class Container<T> implements Iterable<T> {
    protected int size = 0;

    public int size(){ return size; }

    abstract Iterator<T> begin();

    abstract Iterator<T> end();

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return begin();
    }

    /**
     * @return Whether the list is empty or not.
     * @complexity O(1).
     */
    public boolean isEmpty(){
        return size == 0;
    }
}
