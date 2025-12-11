package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic container class used to store items of any type.
 * This class demonstrates the use of Java Generics for the project requirements.
 *
 * @param <T> The type of elements stored in the box.
 */
public class Box<T> {

    /**
     * Internal list to hold the elements.
     */
    private List<T> contents;

    /**
     * Default constructor.
     * Initializes the internal storage.
     */
    public Box() {
        this.contents = new ArrayList<>();
    }

    /**
     * Adds an item to the box.
     *
     * @param item The item to be added.
     */
    public void add(T item) {
        this.contents.add(item);
    }

    /**
     * Retrieves an item from the box at a specific position.
     *
     * @param index The position of the item.
     * @return The item at the specified index.
     */
    public T get(int index) {
        return this.contents.get(index);
    }

    /**
     * Returns the number of items currently in the box.
     *
     * @return The size of the box.
     */
    public int size() {
        return this.contents.size();
    }

    /**
     * Returns the underlying list.
     * Useful for compatibility with legacy methods expecting a List.
     *
     * @return The list of contents.
     */
    public List<T> getContents() {
        return this.contents;
    }
}