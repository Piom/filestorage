package ru.piom.filestorage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class FileStorageServiceImpl implements FileStorageService {
    private static int FILE_NAME_LENGTH_LIMIT = 50;
    private static int DATA_LENGTH_LIMIT = 10;
    private final Object mutex = new Object();

    private DynamicArray nameListIndex;
    private DynamicArray dataArray;
    private AtomicInteger currentSize = new AtomicInteger(0);

    public FileStorageServiceImpl() {
        this.nameListIndex = new DynamicArray();
        this.dataArray = new DynamicArray();
    }

    @Override
    public void add(String fileName, byte[] data) throws IOException {
        checkFileName(fileName);
        if (data.length > DATA_LENGTH_LIMIT) {
            throw new IllegalArgumentException("Data is empty");
        }
        synchronized (mutex) {
            if (findElement(fileName) > -1) {
                throw new FileAlreadyExistsException(fileName);
            }
            int newIndex = Math.max(nameListIndex.length - 1, 0);
            nameListIndex[newIndex] = fileName;
            dataArray[newIndex] = data;
        }

    }

    private void checkFileName(String fileName) {
        if (fileName == null || (fileName.isEmpty() || fileName.length() > FILE_NAME_LENGTH_LIMIT)) {
            throw new IllegalArgumentException("Filename is blank or more then 50");
        }

    }

    @Override
    public void delete(String fileName) throws FileNotFoundException {
        checkFileName(fileName);
        synchronized (mutex) {
            deleteElement(fileName);
        }
    }

    @Override
    public int size() {
        synchronized (mutex) {
            return nameListIndex.length;
        }
    }

    private void deleteElement(String key) throws FileNotFoundException {
        int pos = findElement(key);
        if (pos == -1) {
            throw new FileNotFoundException("Element not found");
        }
        int i;
        for (i = pos; i < nameListIndex.length; i++) {
            nameListIndex[i] = nameListIndex[i + 1];
            dataArray[i] = dataArray[i + 1];
        }

    }


    private int findElement(String key) {
        for (int i = 0; i < nameListIndex.length; i++)
            if (nameListIndex[i] != null && nameListIndex[i].equals(key)) {
                return i;
            }
        return -1;
    }

    public class DynamicArray<E> {
        private final Object[] EMPTY_DATA = {};
        private static final int DEFAULT_CAPACITY = 10;
        private Object[] elementData;
        private static final int MAX_ARRAY_SIZE = 100_000;
        private int size;
        protected transient int modCount = 0;

        public DynamicArray() {
            elementData = EMPTY_DATA;
        }

        private Object[] grow(int minCapacity) {
            return elementData = Arrays.copyOf(elementData,
                    newCapacity(minCapacity));
        }

        private int newCapacity(int minCapacity) {
            // overflow-conscious code
            int oldCapacity = data.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            if (newCapacity - minCapacity <= 0) {
                if (elementData == EMPTY_DATA)
                    return Math.max(DEFAULT_CAPACITY, minCapacity);
                if (minCapacity < 0) // overflow
                    throw new OutOfMemoryError();
                return minCapacity;
            }
            return (newCapacity - MAX_ARRAY_SIZE <= 0)
                    ? newCapacity
                    : hugeCapacity(minCapacity);
        }

        private int hugeCapacity(int minCapacity) {
            if (minCapacity < 0) // overflow
                throw new OutOfMemoryError();
            return (minCapacity > MAX_ARRAY_SIZE)
                    ? Integer.MAX_VALUE
                    : MAX_ARRAY_SIZE;
        }

        public int size() {
            return size;
        }

        public boolean contains(Object o) {
            return indexOf(o) >= 0;
        }

        public int indexOf(Object o) {
            return indexOfRange(o, 0, size);
        }

        int indexOfRange(Object o, int start, int end) {
            Object[] es = elementData;
            if (o == null) {
                for (int i = start; i < end; i++) {
                    if (es[i] == null) {
                        return i;
                    }
                }
            } else {
                for (int i = start; i < end; i++) {
                    if (o.equals(es[i])) {
                        return i;
                    }
                }
            }
            return -1;
        }

        public boolean add(E e) {
            modCount++;
            add(e, elementData, size);
            return true;
        }

        private void add(E e, Object[] elementData, int s) {
            if (s == elementData.length)
                elementData = grow();
            elementData[s] = e;
            size = s + 1;
        }

        public void add(int index, E element) {
            rangeCheckForAdd(index);
            modCount++;
            final int s;
            Object[] elementData;
            if ((s = size) == (elementData = this.elementData).length)
                elementData = grow();
            System.arraycopy(elementData, index,
                    elementData, index + 1,
                    s - index);
            elementData[index] = element;
            size = s + 1;
        }

        public E remove(int index) {
            Objects.checkIndex(index, size);
            final Object[] es = elementData;

            @SuppressWarnings("unchecked") E oldValue = (E) es[index];
            fastRemove(es, index);

            return oldValue;
        }
    }
}
