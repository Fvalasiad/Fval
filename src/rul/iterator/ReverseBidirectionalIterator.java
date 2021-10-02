package rul.iterator;

public class ReverseBidirectionalIterator<T,It extends BidirectionalIterator<T>> implements BidirectionalIterator<T> {

    protected final It it;

    public ReverseBidirectionalIterator(It it){
        this.it = it;
    }

    public It getIt(){
        return it;
    }

    @Override
    public T previous() {
        return it.inc().get();
    }

    @Override
    public boolean hasPrevious() {
        return it.hasNext();
    }

    @Override
    public ReverseBidirectionalIterator<T,It> dec() {
        it.inc();
        return this;
    }

    @Override
    public T get() {
        return it.get();
    }

    @Override
    public void set(T t) {
        it.set(t);
    }

    @Override
    public ReverseBidirectionalIterator<T,It> inc() {
        it.dec();
        return this;
    }

    @Override
    public T next() {
        T data = it.get();
        it.dec();
        return data;
    }

    @Override
    public boolean hasNext() {
        return it.hasPrevious();
    }

    @Override
    public boolean equals(Object obj){
        if(getClass() != obj.getClass()){
            return false;
        }else{
            @SuppressWarnings("unchecked") ReverseBidirectionalIterator<T,It> temp = (ReverseBidirectionalIterator<T, It>) obj;
            return temp.it.equals(it);
        }
    }

    @Override
    public BidirectionalIterator<T> clone() {
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new ReverseBidirectionalIterator<>(it);
    }
}
