package rul.structures;

public interface RandomAccessIterator<T> extends BidirectionalIterator<T> {
    void advance(long n);

    RandomAccessIterator<T> add(long n);
}
