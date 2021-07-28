package rul.structures;

public interface BidirectionalIterator<T> extends ForwardIterator<T> {
    T previous();

    boolean hasPrevious();

    BidirectionalIterator<T> clone();
}
