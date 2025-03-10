package Task2;

import task2.DynamicIntArray;

import java.util.Arrays;

public class DinamicArray implements DynamicIntArray {
    private int size = 0;
    private int array[];

    public DinamicArray() {
        array = new int[10];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(int value) {
        return indexOf(value) != -1;
    }

    @Override
    public boolean add(int e) {
        if (size == array.length) {
            array = Arrays.copyOf(array, array.length * 2);
        }
        array[size] = e;
        size++;
        return true;
    }

    @Override
    public boolean containsAll(DynamicIntArray c) {
        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(DynamicIntArray c) {
        for (int i = 0; i < c.size(); i++) {
            add(c.get(i));
        }
        return true;
    }

    @Override
    public boolean addAll(int index, DynamicIntArray c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        int numNewElements = c.size();
        if (size + numNewElements > array.length) {
            array = Arrays.copyOf(array, size + numNewElements);
        }

        System.arraycopy(array, index, array, index + numNewElements, size - index);

        for (int i = 0; i < numNewElements; i++) {
            array[index + i] = c.get(i);
        }
        size += numNewElements;
        return true;
    }

    @Override
    public boolean removeAll(DynamicIntArray c) {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (c.contains(array[i])) {
                remove(i);
                i--;
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(DynamicIntArray c) {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                remove(i);
                i--;
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void sort() {
        Arrays.sort(array, 0, size);
    }

    @Override
    public void clear() {
        size = 0;
        array = new int[10];
    }

    @Override
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return array[index];
    }

    @Override
    public int set(int index, int element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        int old = array[index];
        array[index] = element;
        return old;
    }

    @Override
    public void add(int index, int element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        if (size == array.length) {
            array = Arrays.copyOf(array, array.length * 2);
        }

        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    @Override
    public long remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        int removedElement = array[index];

        System.arraycopy(array, index + 1, array, index, size - index - 1);

        size--;
        return removedElement;
    }

    @Override
    public int indexOf(int value) {
        for (int i = 0; i < size; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(int element) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1;
    }
}