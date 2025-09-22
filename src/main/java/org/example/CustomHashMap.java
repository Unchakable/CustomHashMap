package org.example;

import java.util.*;

public class CustomHashMap<K, V> {
    private Node<K, V>[] table;
    private int size = 0;
    private int capacity = 16;

    @SuppressWarnings("unchecked")
    public CustomHashMap() {
        this.table = (Node<K, V>[]) new Node[capacity];
    }

    private static class Node<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;
        private final Integer hash;


        Node(Integer hash, K key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Integer getHash() {
            return hash;
        }

        public V setValue(V value) {
            this.value = value;
            return value;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node<K, V> node = (Node<K, V>) obj;
            return key == node.key &&
                    Objects.equals(value, node.value) &&
                    Objects.equals(hash, node.hash);
        }

        @Override
        public int hashCode() {
            return Objects.hash((Object) hash, (Object) key, (Object) value);
        }

    }

    private Node<K, V> getNode(K key) {
        if (table == null) return null;
        Integer hash;
        if (key != null) hash = key.hashCode();
        else hash = null;

        if (containsKey(key)) {
            for (Node<K, V> kvNode : table) {
                if (kvNode != null && kvNode.getHash() == hash && kvNode.getKey() == key) {
                    return kvNode;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void addBucket() {
        capacity = capacity * 2;
        table = Arrays.copyOf(table, capacity);
    }

    public boolean containsKey(K key) {
        Integer hash;
        if (key != null) hash = key.hashCode();
        else hash = null;
        if (table == null && size == 0) return false;
        assert table != null;
        for (Node<K, V> kvNode : table) {
            if (kvNode != null) {
                if (kvNode.getHash() == hash && key == kvNode.getKey()) return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public V get(K key) {
        if (containsKey(key)) {
            Node<K, V> node = getNode(key);
            if (node != null) {
                return node.getValue();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public V put(K key, V value) {

        if (!containsKey(key)) {
            Node<K, V> node;
            if (key != null) node = new Node<>((Integer) key.hashCode(), key, value);
            else node = new Node<>(null, null, value);
            if (size == capacity) addBucket();
            table[size] = node;
            size++;
            return value;
        } else {
            Node<K, V> node = getNode(key);
            assert node != null;
            V oldV = node.getValue();
            for (Node<K, V> kvNode : table) {
                if (kvNode != null) {
                    if (key != null) {
                        if (kvNode.getHash().equals(key.hashCode()) && kvNode.getKey().equals(key)) {
                            kvNode.setValue(value);
                            return oldV;
                        }
                    } else if (kvNode.getHash() == null && kvNode.getKey() == null) {
                        kvNode.setValue(value);
                        return oldV;
                    }
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public V remove(K key) {
        Integer hash;
        if (key != null) hash = key.hashCode();
        else hash = null;
        if (!containsKey(key)) return null;
        for (int i = 0; i < size; i++){
            if (table[i] != null && table[i].getHash() == hash && table[i].getKey() == key){
                V removeValue = table[i].getValue();
                Node<K, V>[] oldTable = table;
                table = (Node<K, V>[]) new Node[size];
                if (i > 0) System.arraycopy(oldTable, 0, table, 0, i);
                if (i < size - 1) System.arraycopy(oldTable, i + 1, table, i, size - i - 1);
                size--;
                oldTable = null;
                return removeValue;
            }
        }
        return null;
    }


    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CustomHashMap<K, V> customHashMap = (CustomHashMap<K, V>) obj;
        return table == customHashMap.table;
    }

    @Override
    public int hashCode() {
        return Objects.hash((Object) table);
    }
}

