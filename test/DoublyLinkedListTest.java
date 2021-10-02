import rul.iterator.BidirectionalIterator;
import rul.container.DoublyLinkedList;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class DoublyLinkedListTest {

    @Test
    public void testPushBack(){
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.pushBack(10);
        list.pushBack(20);
        list.pushBack(30);

        BidirectionalIterator<Integer> it = list.begin();
        assert it.next().equals(10);
        assert it.next().equals(20);
        assert it.next().equals(30);
        assert list.size() == 3;
    }

    @Test
    public void testPushFront(){
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.pushFront(10);
        list.pushFront(20);
        list.pushFront(30);

        BidirectionalIterator<Integer> it = list.begin();
        assert it.next().equals(30);
        assert it.next().equals(20);
        assert it.next().equals(10);
        assert list.size() == 3;
    }

    @Test
    public void testPush(){
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.pushFront(10);
        list.pushBack(20);
        list.pushBack(30);
        list.pushFront(1);

        BidirectionalIterator<Integer> it = list.reverseBegin();
        assert it.next().equals(30);
        assert it.next().equals(20);
        assert it.next().equals(10);
        assert it.next().equals(1);
        assert list.size() == 4;
    }

    @Test
    public void testFrontBackClear(){
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.pushFront(10);

        assert list.getFront() == 10;
        assert list.getBack() == 10;

        list.pushBack(20);

        assert list.getFront() == 10;
        assert list.getBack() == 20;

        assert list.size() == 2;
        list.clear();
        assert list.size() == 0;

        assert list.peekBack() == null && list.peekFront() == null;
    }

    @Test
    public void insertAfterTest(){
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.pushBack(10);
        BidirectionalIterator<Integer> it = list.begin();

        list.insertAfter(it,20);

        DoublyLinkedList<Integer> list2 = new DoublyLinkedList<>();
        list2.pushBack(10);
        list2.pushBack(20);
        list2.pushBack(30);
        list2.pushBack(40);

        list.insertAfter(it,list2.begin(), list2.end());

        list.insertBefore(it,list2.reverseBegin(),list2.reverseEnd());

        BidirectionalIterator<Integer> begin = list.begin();
        assert begin.next() == 10;
        assert begin.next() == 20;
        assert begin.next() == 30;
        assert begin.next() == 40;
        assert begin.next() == 10;
        assert begin.next() == 10;
        assert begin.next() == 20;
        assert begin.next() == 30;
        assert begin.next() == 40;
        assert begin.next() == 20;
        assert !begin.hasNext();

        list.erase(it,list.end());
        list.swap(list2);

        begin = list2.begin();

        assert begin.next() == 10;
        assert begin.next() == 20;
        assert begin.next() == 30;
        assert begin.next() == 40;
        assert !begin.hasNext();
        assert list2.size() == 4;

        begin = list.begin();

        assert begin.next() == 10;
        assert begin.next() == 20;
        assert begin.next() == 30;
        assert begin.next() == 40;
        assert !begin.hasNext();
        assert list.size() == 4;
    }

    @Test
    public void speedComparison(){
        LinkedList<Integer> list1 = new LinkedList<>();
        DoublyLinkedList<Integer> list2 = new DoublyLinkedList<>();

        long start;

        start = System.nanoTime();
        for( int i = 0; i < 10000000; i = i + 1 ){
            list1.push(i);
        }

        for( Integer i : list1 ){
            i++;
        }
        System.out.println();
        System.out.println(System.nanoTime() - start);

        start = System.nanoTime();
        for( int i = 0; i < 10000000; i = i + 1 ){
            list2.pushBack(i);
        }

        for( Integer i : list2 ){
            i++;
        }
        System.out.println();
        System.out.println(System.nanoTime() - start);
    }


}
