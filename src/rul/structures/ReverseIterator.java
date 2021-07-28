package rul.structures;

public class ReverseIterator<T,It extends BidirectionalIterator<T>> implements BidirectionalIterator<T> {

    private final It it;

    public ReverseIterator(It it){
        this.it = it;
    }

    public It getIt(){
        return it;
    }

    @Override
    public T previous() {
        return it.next();
    }

    @Override
    public boolean hasPrevious() {
        return it.hasNext();
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
    public T next() {
        return it.previous();
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
            @SuppressWarnings("unchecked") ReverseIterator<T,It> temp = (ReverseIterator<T, It>) obj;
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
        return new ReverseIterator<>(it);
    }
}
