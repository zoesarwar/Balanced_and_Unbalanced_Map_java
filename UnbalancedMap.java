//Zoe Sarwar cssc0735
//Stephanie Bekker cssc0754

package edu.sdsu.cs.datastructures;
import java.util.List;
import java.util.LinkedList;

public class UnbalancedMap<K extends Comparable<K>, V> implements IMap<K, V> {
    private int currentSize;
    private Node<K, V> root;
    private K foundKey;
    private K foundAKey;
    private V pastValue;

    private class Node<K, V> {
        K key;
        V value;
        Node<K, V> right;
        Node<K, V> left;

        public Node(K k, V v) {
            key = k;
            value = v;
            left = null;
            right = null;
        }
    }

    public UnbalancedMap() {
        root = null;
        currentSize = 0;
    }

    public UnbalancedMap(IMap<K, V> original) {
        for (K key : original.keyset()) {
            add(key, original.getValue(key));
        }
    }

    @Override
    public boolean contains(K key) {
        if (search(key, root) == null) {
            return false;
        }
        return true;
    }

    public Node search(K key, Node<K, V> node) {
        if (node == null)
            return null;
        if (key.compareTo(node.key) < 0) {
            return search(key, node.left);
        } else if (key.compareTo(node.key) > 0) {
            return search(key, node.right);
        } else {
            return node;
        }
    }

    @Override
    public boolean add(K key, V value) {
        if (contains(key)){
            return false;
        }
        if (root == null) {
            root = new Node<>(key, value);
        } else {
            insert(key, value, root, null, false);
        }
        currentSize++;
        return true;
    }

    private void insert(K k, V v, Node<K, V> n, Node<K, V> parent, boolean wasLeft) {
        if (n == null) {
            if (wasLeft) {
                parent.left = new Node<>(k, v);
            } else {
                parent.right = new Node<>(k, v);
            }
        } else if (k.compareTo(n.key) < 0) {
            insert(k, v, n.left, n, true);
        } else {
            insert(k, v, n.right, n, false);
        }
    }

    @Override
    public V delete(K key) {
        if (isEmpty()) {
            return null;
        } else if (!contains(key)) {
            return null;
        }
        root = deleteNode(root, key);
        currentSize--;
        return pastValue;
    }

    private Node<K, V> deleteNode(Node<K, V> root, K key) {
        if (root.key.compareTo(key) > 0)
            root.left = deleteNode(root.left, key);
        else if (root.key.compareTo(key) < 0)
            root.right = deleteNode(root.right, key);
        else {
            if (root.left != null & root.right != null) {
                pastValue = root.value;
                Node<K, V> tmp = root;
                Node<K, V> successor = findSuccessor(tmp.right);
                root.key = successor.key;
                deleteSuccessorNode(root.right, successor.key);
            }
            else if (root.left != null) {
                pastValue = root.value;
                root = root.left;
            }
            else if (root.right != null) {
                pastValue = root.value;
                root = root.right;
            } else {
                pastValue = root.value;
                root = null;
            }
        }
        return root;
    }

    private Node<K, V> deleteSuccessorNode(Node<K, V> root, K key) {
        if (root.key.compareTo(key) > 0)
            root.left = deleteSuccessorNode(root.left, key);
        else if (root.key.compareTo(key) < 0)
            root.right = deleteSuccessorNode(root.right, key);
        else {
            if (root.left != null & root.right != null) {
                Node<K, V> tmp = root;
                Node<K, V> successor = findSuccessor(tmp.right);
                root.key = successor.key;
                deleteSuccessorNode(root.right, successor.key);
            }
            else if (root.left != null) {
                root = root.left;
            }
            else if (root.right != null) {
                root = root.right;
            } else {
                root = null;
            }
        }
        return root;
    }

    private Node<K, V> findSuccessor(Node<K, V> root) {
        if (root.left == null)
            return root;
        else
            return findSuccessor(root.left);
    }

    @Override
    public V getValue(K key) {
        if (isEmpty())
            return null;
        if (!contains(key))
            return null;
        Node<K, V> found = search(key, root);
        return found.value;
    }

    @Override
    public K getKey(V value) {
        if (isEmpty())
            return null;
        foundKey = null;
        findKey(root, value);
        return foundKey;
    }

    private void findKey(Node<K, V> node, V value) {
        if (node == null)
            return;
        findKey(node.left, value);
        if (value.equals(node.value) == true && foundKey == null) {
            foundKey = node.key;
        }
        findKey(node.right, value);
    }

    @Override
    public Iterable<K> getKeys(V value) {
        List<K> toReturn = new LinkedList();
        if(isEmpty()){
            return null;
        }
        return getKeysHelper(value, toReturn, root);
    }

    private Iterable<K> getKeysHelper(V value, List<K> keys, Node<K,V> node){
        if (node == null){
            return null;
        }
        getKeysHelper(value, keys, node.left);
        if (value.equals(node.value) == true) {
            foundAKey = node.key;
            keys.add(foundAKey);
        }
        getKeysHelper(value, keys, node.right);
        return keys;
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        if (currentSize == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        root = null;
        currentSize = 0;
    }

    @Override
    public Iterable<K> keyset() {
        List<K> toReturn = new LinkedList();
        keysetHelper(root, toReturn);
        return toReturn;
    }
    private Iterable<K> keysetHelper(Node<K,V> node, List<K> keyset){
        if(node == null){
            return null;
        }
        keysetHelper(node.left, keyset);
        keyset.add(node.key);
        keysetHelper(node.right, keyset);
        return keyset;
    }

    @Override
    public Iterable<V> values() {
        List<V> toReturn = new LinkedList();
        valuesHelper(root, toReturn);
        return toReturn;
    }

    private Iterable<V> valuesHelper(Node<K,V> node, List<V> values){
        if(node == null){
            return null;
        }
        valuesHelper(node.left, values);
        values.add(node.value);
        valuesHelper(node.right, values);
        return values;
    }

}