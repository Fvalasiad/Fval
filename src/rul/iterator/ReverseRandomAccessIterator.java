package rul.iterator;

import rul.iterator.RandomAccessIterator;
import rul.iterator.ReverseBidirectionalIterator;

public class ReverseRandomAccessIterator<T, It extends RandomAccessIterator<T>> extends ReverseBidirectionalIterator<T,It> implements RandomAccessIterator<T> {

    public ReverseRandomAccessIterator(It it){
        super(it);
    }

    @Override
    public void advance(long n) {
        it.advance(-n);
    }

    @Override
    public RandomAccessIterator<T> add(long n) {
        return null;
    }
}
