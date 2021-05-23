package fval.structures;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

@SuppressWarnings("unused")

public class DoublyLinkedList<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    long size = 0;

    // Iterators

    /**
     * @return Iterator pointing to the first element. Or an invalid iterator if the list is empty.
     * @complexity O(1).
     */
    public BidirectionalIterator<T> begin(){
        return new DLLIterator(head);
    }

    /**
     * @return Iterator pointing to the last element. Or an invalid iterator if the list is empty.
     * @complexity O(1).
     */
    public BidirectionalIterator<T> end(){ return new DLLIterator(tail); }

    /**
     * @return Reverse iterator pointing to the first element. Or an invalid iterator if the list is empty.
     * @complexity O(1).
     */
    public BidirectionalIterator<T> reverseEnd(){ return new ReverseIterator<>(new DLLIterator(head)); }

    /**
     * @return Reverse iterator pointing to the last element. Or an invalid iterator if the list is empty.
     * @complexity O(1).
     */
    public BidirectionalIterator<T> reverseBegin(){
        return new ReverseIterator<>(new DLLIterator(tail));
    }


    // Capacity

    /**
     * @return Whether the list is empty or not.
     * @complexity O(1).
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * @return The container's size.
     * @complexity O(1).
     */
    public long size(){
        return size;
    }

    // Element Access

    /**
     * @return The first element of the list.
     * @throws NoSuchElementException if the list is empty.
     * @complexity O(1).
     */
    public T getFront(){
        T t = peekFront();
        if(t == null){
            throw new NoSuchElementException();
        }else{
            return t;
        }
    }

    /**
     * @return The first element of the list or null if list is empty.
     * @complexity O(1).
     */
    public T peekFront(){
        if( head != null ){
            return head.data;
        }else{
            return null;
        }
    }

    /**
     * Sets the first element.
     * @param data The new value.
     * @throws NoSuchElementException If the list is empty.
     * @complexity O(1).
     */
    public void setFront(T data){
        if( head != null ) {
            head.data = data;
        }else{
            throw new NoSuchElementException();
        }
    }

    /**
     * @return The last element of the list.
     * @throws NoSuchElementException if the list is empty.
     * @complexity O(1).
     */
    public T getBack(){
        T t = peekBack();
        if( t == null ){
            throw new NoSuchElementException();
        }else{
            return t;
        }
    }

    /**
     * @return The last element of the list or null if list is empty.
     * @complexity O(1).
     */
    public T peekBack(){
        if( tail != null ){
            return tail.data;
        }else{
            return null;
        }
    }

    // Modifiers

    /**
     * Inserts a new element after the specified iterator.
     *
     * Note that supplying an iterator from another list causes loss of data.
     * @param position The specified iterator.
     * @param t The new element.
     * @return An iterator pointing to the new element.
     * @throws IllegalArgumentException If the supplied iterator isn't a DLLIterator.
     * @complexity O(1).
     */
    public BidirectionalIterator<T> insertAfter(final BidirectionalIterator<T> position, T t){
        Node<T> node = toNode(position);

        link(new Node<>(t,node,node.next));
        return new DLLIterator(node.next);
    }

    /**
     * Inserts elements in the range [first,last] after the specified iterator.
     *
     * Note that supplying a position iterator from another list causes loss of data.
     * @param position The position to add the elements after.
     * @param first The first interval.
     * @param last The second interval.
     * @complexity n, where n is the number of elements between first and last.
     */
    public void insertAfter(BidirectionalIterator<T> position, ForwardIterator<T> first, final ForwardIterator<T> last){
        for(ForwardIterator<T> it = first.clone(); !it.equals(last); it.next()){
            position = insertAfter(position,it.get());
        }
        insertAfter(position,last.get());
    }

    /**
     * Inserts a new element before the specified iterator.
     *
     * Note that supplying an iterator from another list causes loss of data.
     * @param position The specified iterator.
     * @param t The new element.
     * @return An iterator pointing to the new element.
     * @throws IllegalArgumentException If the supplied iterator isn't a DLLIterator.
     * @complexity O(1).
     */
    public BidirectionalIterator<T> insertBefore(final BidirectionalIterator<T> position, T t){
        Node<T> node = toNode(position);

        link(new Node<>(t,node.prev,node));
        return new DLLIterator(node.prev);
    }

    /**
     * Inserts elements in the range [first,last] before the specified iterator.
     *
     * Note that supplying a position iterator from another list causes loss of data.
     * @param position The position to add the elements before.
     * @param first The first interval.
     * @param last The second interval.
     * @complexity n, where n is the number of elements between first and last.
     */
    public void insertBefore(BidirectionalIterator<T> position, ForwardIterator<T> first, final ForwardIterator<T> last){
        for(ForwardIterator<T> it = first.clone(); !it.equals(last); it.next()){
            position = insertBefore(position,it.get());
        }
        insertBefore(position,last.get());
    }

    /**
     * Erases the element pointed by the position iterator.
     *
     * Note that providing a DLLIterator from another list causes data loss.
     * @param position The element to be deleted.
     * @return An iterator pointing to the element after the one specified by position.
     * If position was the tail, then an invalidated iterator is returned.
     * @iterators All but position remain valid.
     * @throws IllegalArgumentException if position isn't a DLLIterator.
     * @complexity O(1).
     */
    public BidirectionalIterator<T> erase(BidirectionalIterator<T> position){
        Node<T> node = toNode(position);

        DLLIterator ret = new DLLIterator(node.next);
        unlink(node);
        return ret;
    }

    /**
     * Erases elements in the range[first,last].
     *
     * Note that providing a range of iterators of another list causes data loss.
     * @param first First interval.
     * @param last Second interval.
     * @return The element after last.
     * @iterators Iterators in the range [first,last] are invalidated, all else remain valid.
     * @complexity n , where n is the number of elements between [first,last].
     */
    public BidirectionalIterator<T> erase(BidirectionalIterator<T> first, BidirectionalIterator<T> last){
        do {
            first = erase(first);
        }while( !first.equals(last) );
        return erase(last);
    }

    /**
     * Removes all of the list elements , leaving the size 0.
     *
     * @iterators All iterators are invalidated.
     * @complexity O(1), Left for garbage collection.
     */
    public void clear(){
        head = tail = null;
        size = 0;
    }

    /**
     * Swaps elements between the two lists.
     * @complexity O(1).
     * @param list The second list to swap elements with.
     * @iterators All iterators remain unchanged,pointing to the exact same elements in the other container.
     */
    public void swap(DoublyLinkedList<T> list){
        Node<T> temp = head;
        head = list.head;
        list.head = temp;

        temp = tail;
        tail = list.tail;
        list.tail = temp;

        long temp1 = size;
        size = list.size;
        list.size = temp1;
    }


    /**
     * Insert element on the front of the list.
     * @complexity O(1).
     * @iterators All iterators remain valid.
     * @param obj The obj to be inserted.
     */
    public void pushFront(T obj){
        link(new Node<>(obj,null,head));
    }

    /**
     * Remove element from the front.
     * @complexity O(1).
     * @iterators All other iterators remain valid.
     * @throws NoSuchElementException if the list is empty.
     */
    public void popFront(){
        if(size > 0){
            unlink(head);
        }else{
            throw new NoSuchElementException();
        }
    }

    /**
     * Insert element on the back of the list.
     * @complexity O(1).
     * @iterators All iterators remain valid.
     * @param obj The obj to be inserted.
     */
    public void pushBack(T obj){
        link(new Node<>(obj,tail,null));
    }

    /**
     * Remove element from the back.
     * @complexity O(1).
     * @iterators All other iterators remain valid.
     * @throws NoSuchElementException if the list is empty.
     */
    public void popBack(){
        if(size > 0){
            unlink(tail);
        }else{
            throw new NoSuchElementException();
        }
    }


    // Operations

    public void splice(BidirectionalIterator<T> position, DoublyLinkedList<T> list){
        size += list.size;

        Node<T> node = toNode(position);
        Node<T> temp = node.next;

        node.next = list.head;
        list.tail.next = temp;
        temp.prev = list.tail;

        list.clear();
    }

    public void splice(BidirectionalIterator<T> position, DoublyLinkedList<T> list, BidirectionalIterator<T> i){
        Node<T> node = toNode(i);
        Node<T> pos = toNode(position);

        list.unlink(node);

        node.prev = pos;
        node.next = pos.next;

        link(node);
    }

    /**
     * **WARNING**
     * This method has extremely dangerous functionality.
     *
     * It in fact joins the "sublist" specified by [first,last] with this list by putting said range after position.
     * Problem though is that we don't really know the distance between [first,last] and thus in order to calculate
     * the new size for both "this" and "list" we have to go ahead and iterate from first to last adding a linear
     * complexity to an initially constant complexity. The fact that someone might not care about the list's size
     * brought me to the decision to actually add another parameter , namely size , that specifies the distance
     * between [first,last]. You are now given the choice to either count the distance yourself and add it to the
     * parameter , or plainly supply a random integer and break this container's size functionality.
     *
     * @param position The position to add the range after.
     * @param list The list from where this range is taken.
     * @param first The first interval.
     * @param last The second interval.
     * @param size The distance between [first,last].
     * @complexity O(1).
     * @iterators All iterators that are moved remain valid , but are now pointing to this list's elements.
     */
    public void splice(BidirectionalIterator<T> position, DoublyLinkedList<T> list, BidirectionalIterator<T> first,
                       BidirectionalIterator<T> last, long size){
        Node<T> pos = toNode(position);
        Node<T> begin = toNode(first);
        Node<T> end = toNode(last);

        //TODO
    }


    private void link(@NotNull Node<T> node){
        if(node.prev != null){
            node.prev.next = node;
        }else{
            head = node;
        }

        if(node.next != null){
            node.next.prev = node;
        }else{
            tail = node;
        }

        size = size + 1;
    }

    private void unlink(@NotNull Node<T> node){
        if(node.prev != null){
            node.prev.next = node.next;
        }else{
            head = node.next;
        }

        if(node.next != null){
            node.next.prev = node.prev;
        }else{
            tail = node.prev;
        }
        node.prev = node.next = null;
        size = size - 1;
    }

    private Node<T> toNode(BidirectionalIterator<T> iterator) {
        Node<T> node;
        if(iterator.getClass() == DLLIterator.class){
            node = ((DLLIterator) iterator).node;
        }else if(iterator.getClass() == ReverseIterator.class){
            node = ((ReverseIterator<T, DLLIterator>) iterator).getIt().node;
        }else{
            throw new IllegalArgumentException();
        }
        return node;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return begin();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
        BidirectionalIterator<T> begin = begin();

        action.accept(getFront());

        while(begin.hasNext()){
            action.accept(begin.next());
        }
    }

    @Override
    public Spliterator<T> spliterator() {
        throw new UnsupportedOperationException("spliterator");
    }

    private class DLLIterator implements BidirectionalIterator<T>{

        private Node<T> node;

        public DLLIterator(Node<T> node){
            this.node = node;
        }

        @Override
        public T get() {
            return node.data;
        }

        @Override
        public void set(T t) {
            node.data = t;
        }

        @Override
        public boolean hasPrevious(){
            return node.prev != null;
        }

        @Override
        public T previous() {
            if( hasPrevious() ){
                node = node.prev;
            }else{
                throw new NoSuchElementException();
            }
            return node.data;
        }

        @Override
        public boolean hasNext(){
            return node.next != null;
        }

        @Override
        public T next() {
            if( hasNext() ){
                node = node.next;
            }else{
                throw new NoSuchElementException();
            }
            return node.data;
        }


        @Override
        public boolean equals(Object s) {
            if(getClass() != s.getClass()){
                return false;
            }else {
                @SuppressWarnings("unchecked") DLLIterator obj = (DLLIterator) s;
                return node == obj.node;
            }
        }

        @Override
        public BidirectionalIterator<T> clone() {
            try {
                super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return new DLLIterator(node);
        }
    }

    private static class Node<T>{
        private T data;
        private Node<T> prev;
        private Node<T> next;

        public Node(T data,Node<T> prev,Node<T> next){
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

}
