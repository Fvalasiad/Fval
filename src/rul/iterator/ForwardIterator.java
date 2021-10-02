package rul.iterator;

import java.util.Iterator;

public interface ForwardIterator<T> extends Cloneable, Iterator<T> {
    T get();

    void set(T t);

    ForwardIterator<T> inc();

    ForwardIterator<T> clone();
}
