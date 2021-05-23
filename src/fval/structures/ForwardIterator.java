package fval.structures;

import java.util.Iterator;

public interface ForwardIterator<T> extends Cloneable, Iterator<T> {
    T get();

    void set(T t);

    ForwardIterator<T> clone();
}
