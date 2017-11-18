package com.packing;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {
        }

        private Node<T> findNext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new TreeSet<>(subSet(root, first(), last(), new TreeSet<>())).
                subSet(fromElement,  toElement);
    }

    public SortedSet<T> subSet(Node<T> root, T fromElement, T toElement, SortedSet<T> setTree) {
        if (root == null) return setTree;
        int start = root.value.compareTo(fromElement);
        int finish = toElement.compareTo(root.value);
        if (start >= 0 && finish > 0) {
            setTree.add(root.value);
            subSet(root.left, fromElement, toElement, setTree);
            subSet(root.right, fromElement, toElement, setTree);
        } else if (start > 0) {
            subSet(root.left, fromElement, toElement, setTree);
        } else if (finish > 0) {
            subSet(root.right, fromElement, toElement, setTree);
        }
        return setTree;
    }



    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return subSet(fromElement, last());
    }


    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    @Override
    public String toString() {
        List<List<Object>> str = toStr(root, 0, new ArrayList<>());
        StringBuilder result = new StringBuilder();
        int i = 5;
        for (List<Object> aList : str) {
            i--;
            result.append(spaceAdd((int) Math.pow(2.0, (double) i - 1)));
            for (Object bList : aList) {
                result.append(bList).append(spaceAdd((int) Math.pow(2.0, (double) i) - 1));
            }
            result.append("\n");
        }
        return result.toString();
    }

    @NotNull
    private String spaceAdd(int i) {
        StringBuilder result = new StringBuilder();
        for (; i > 0; i--)
            result.append(" ");
        return result.toString();
    }

    private void addNull(int depth, List<List<Object>> list) {
        if (depth >= 7) return;
        if (list.size() <= depth) list.add(new ArrayList<>());
        list.get(depth).add(" ");
        addNull(depth + 1, list);
        addNull(depth + 1, list);
    }

    public List<List<Object>> toStr(Node<T> root, int depth, List<List<Object>> list) {
        if (depth >= 5) return list;
        if (list.size() <= depth) list.add(new ArrayList<>());
        if (root == null) {
            list.get(depth).add(" ");
            addNull(depth + 1, list);
            addNull(depth + 1, list);
            return list;
        }
        if (list.size() <= depth) list.add(new ArrayList<>());
        list.get(depth).add(root.value);
        toStr(root.left, depth + 1, list);
        toStr(root.right, depth + 1, list);
        return list;
    }

}
