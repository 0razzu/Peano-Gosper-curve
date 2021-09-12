package util;


import java.util.Iterator;
import java.util.NoSuchElementException;


public class CyclicIterator<T> implements Iterator<T> {
    private final T[] array;
    private int cur;
    
    
    public CyclicIterator(T[] array) {
        this.array = array;
    }
    
    
    @Override
    public boolean hasNext() {
        return (array != null && array.length != 0);
    }
    
    
    @Override
    public T next() {
        if (!hasNext())
            throw new NoSuchElementException("The array is empty");
        
        T elem = array[cur];
        cur = (cur + 1) % array.length;
        
        return elem;
    }
}
