public class LinkedListDeque<T> {
    private class ListNode {
        private T val;
        private ListNode pre;
        private ListNode next;

        ListNode(T i, ListNode p, ListNode n) {
            val = i;
            pre = p;
            next = n;
        }
    }
    private int _size;
    private final ListNode sentinel;

    public LinkedListDeque() {
        _size = 0;
        sentinel = new ListNode(null, null, null);
        sentinel.pre = sentinel;
        sentinel.next = sentinel;
    }

    public void addFirst(T item) {
        ListNode firstNode = sentinel.next;
        ListNode addedNode = new ListNode(item, sentinel, firstNode);

        sentinel.next = addedNode;
        firstNode.pre = addedNode;
        _size++;
    }

    public void addLast(T item) {
        ListNode lastNode = sentinel.pre;
        ListNode addedNode = new ListNode(item, lastNode, sentinel);

        sentinel.pre = addedNode;
        lastNode.next = addedNode;
        _size++;
    }

    public boolean isEmpty() {
        return _size == 0;
    }

    public int size() {
        return _size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        ListNode cur = sentinel.next;

        while (cur != sentinel) {
            System.out.print(cur.val + " ");
            cur = cur.next;
        }
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        ListNode removedNode = sentinel.next == sentinel ? null : sentinel.next;

        ListNode behindNode = removedNode.next;
        sentinel.next = behindNode;
        behindNode.pre = sentinel;
        _size--;

        return removedNode.val;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        ListNode removedNode = sentinel.pre == sentinel ? null : sentinel.pre;

        ListNode beforeNode = removedNode.pre;
        sentinel.pre = beforeNode;
        beforeNode.next = sentinel;
        _size--;

        return removedNode.val;
    }

    public T get(int index) {
        if (index >= _size) {
            return null;
        }

        int curIdx = 0;
        ListNode cur = sentinel.next;

        while (curIdx != index) {
            cur = cur.next;
            curIdx++;
        }

        return cur.val;
    }

    public T getRecursive(int index) {
        if (index >= size()) {
            return null;
        }

        return getRecursive(sentinel.next, index);
    }

    private T getRecursive(ListNode cur, int idx) {
        if (idx == 0) {
            return cur.val;
        }

        return getRecursive(cur.next, idx - 1);
    }
}
