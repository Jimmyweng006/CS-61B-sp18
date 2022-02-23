public class ArrayDeque<T> {
    private T[] items;
    private int _size;
    private int _length;
    private int initFirst = 4;
    private int initLast = 5;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        _size = 0;
        _length = 8;
        items = (T[]) new Object[_length];
        nextFirst = initFirst;
        nextLast = initLast;
    }

    private void resizeSmall(int capacity, int full) {
        int frontLength = calculateFrontLength(full);
        int backLength = calculateBackLength(full);

        int oldInitFirst = initFirst;
        int oldInitLast = initLast;
        int tempLength = _length;

        _length /= 2;
        initFirst /= 2;
        initLast = initFirst + 1;
        T[] a = (T[]) new Object[capacity];

        int mappedIdx = initFirst;
        while (frontLength > 0) {
            a[mappedIdx--] = items[oldInitFirst--];
            frontLength--;

            if (oldInitFirst < 0) {
                oldInitFirst += tempLength;
            }
            if (mappedIdx < 0) {
                mappedIdx += _length;
            }
        }
        nextFirst = mappedIdx;

        mappedIdx = initLast;
        while (backLength > 0) {
            a[mappedIdx++] = items[oldInitLast++];
            backLength--;

            if (oldInitLast == tempLength) {
                oldInitLast %= tempLength;
            }
            if (mappedIdx == _length) {
                mappedIdx %= _length;
            }
        }
        nextLast = mappedIdx;

        items = a;
    }

    private void resizeLarge(int capacity, int full) {
        int frontLength = calculateFrontLength(full);
        int backLength = calculateBackLength(full);

        int oldInitFirst = initFirst;
        int oldInitLast = initLast;
        int tempLength = _length;

        _length *= 2;
        initFirst *= 2;
        initLast = initFirst + 1;
        T[] a = (T[]) new Object[capacity];

        int mappedIdx = initFirst;
        while (frontLength > 0) {
            a[mappedIdx--] = items[oldInitFirst--];
            frontLength--;

            if (oldInitFirst < 0) {
                oldInitFirst += tempLength;
            }
            if (mappedIdx < 0) {
                mappedIdx += _length;
            }
        }
        nextFirst = mappedIdx;

        mappedIdx = initLast;
        while (backLength > 0) {
            a[mappedIdx++] = items[oldInitLast++];
            backLength--;

            if (oldInitLast == tempLength) {
                oldInitLast %= tempLength;
            }
            if (mappedIdx == _length) {
                mappedIdx %= _length;
            }
        }
        nextLast = mappedIdx;

        items = a;
    }

    /** should have enough spaces before adding the item */
    public void addFirst(T item) {
        int full = -1;
        items[nextFirst] = item;
        _size++;
        if (nextFirst - 1 == initFirst) {
            full = 0;
        }
        nextFirst--;

        if (_size == _length) {
            resizeLarge(_length * 2, full);
        } else if (nextFirst < 0) {
            nextFirst = _length - 1;
        }
    }

    public void addLast(T item) {
        int full = -1;
        items[nextLast] = item;
        _size++;
        if (nextLast + 1 == initLast) {
            full = 1;
        }
        nextLast++;

        if (_size == _length) {
            resizeLarge(_length * 2, full);
        } else if (nextLast == _length) {
            nextLast = 0;
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return _size;
    }

    public void printDeque() {
        // _size not equal to _length when printDeque() is called
        // tempNextFirst should smaller than tempNextLast
        int tempNextFirst = nextFirst;
        if (tempNextFirst > initFirst) {
            tempNextFirst -= _length;
        }

        int tempNextLast = nextLast;
        if (tempNextLast < initLast) {
            tempNextLast += _length;
        }

        // first fart
        int curIdx;
        for (int front = tempNextFirst + 1; front <= initFirst; front++) {
            curIdx = front;
            if (curIdx < 0) {
                curIdx += _length;
            }
            System.out.print(items[curIdx] + " ");
        }

        // last part
        for (int back = initLast; back <= tempNextLast - 1; back++) {
            curIdx = back;
            if (curIdx >= _length) {
                curIdx -= _length;
            }
            System.out.print(items[curIdx] + " ");
        }

        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        nextFirst = (nextFirst + 1) % _length;
        T ans = items[nextFirst];
        items[nextFirst] = null;
        _size--;

        double tempSize = size();
        double usageRatio = tempSize / _length;
        if (usageRatio < 0.25 && _length >= 16) {
            resizeSmall(_length / 2, -1);
        }

        return ans;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        nextLast = nextLast - 1 < 0 ? _length - 1 : nextLast - 1;
        T ans = items[nextLast];
        items[nextLast] = null;
        _size--;

        double tempSize = size();
        double usageRatio = tempSize / _length;
        if (usageRatio < 0.25 && _length >= 16) {
            resizeSmall(_length / 2, -1);
        }

        return ans;
    }

    public T get(int index) {
        // _size not equal to _length when get() is called
        if (index >= size()) {
            return null;
        }
        int ansIdx;

        // first part
        int frontLength = calculateFrontLength(-1);
        if (frontLength - 1 >= index) {
            ansIdx = nextFirst + 1 + index;
            if (ansIdx >= _length) {
                ansIdx %= _length;
            }
            return items[ansIdx];
        }

        index -= frontLength;

        // back part
        ansIdx = initLast + index;
        if (ansIdx >= _length) {
            ansIdx %= _length;
        }

        return items[ansIdx];
    }

    private int calculateFrontLength(int full) {
        if (full == 0) {
            return _length;
        }

        int tempNextFirst = nextFirst;
        if (tempNextFirst > initFirst) {
            tempNextFirst -= _length;
        }

        return initFirst - tempNextFirst;
    }

    private int calculateBackLength(int full) {
        if (full == 1) {
            return _length;
        }

        int tempNextLast = nextLast;
        if (tempNextLast < initLast) {
            tempNextLast += _length;
        }

        return tempNextLast - initLast;
    }
}
