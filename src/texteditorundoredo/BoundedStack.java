package texteditorundoredo;

import java.util.ArrayList;

public class BoundedStack<T> {

    private ArrayList<T> items;
    private int capacity;

    public BoundedStack(int capacity) {

        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }

        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    public void push(T item) {

        if (item == null) {
            return;
        }

        if (isFull()) {
            removeOldest();
        }

        items.add(item);
    }

    public T pop() {

        if (isEmpty()) {
            return null;
        }

        return items.remove(items.size() - 1);
    }

    public T peek() {

        if (isEmpty()) {
            return null;
        }

        return items.get(items.size() - 1);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean isFull() {
        return items.size() >= capacity;
    }

    public int size() {
        return items.size();
    }

    public void removeOldest() {

        if (!items.isEmpty()) {
            items.remove(0);
        }
    }

    public void clear() {
        items.clear();
    }

    public ArrayList<T> getItems() {
        return new ArrayList<>(items);
    }
}