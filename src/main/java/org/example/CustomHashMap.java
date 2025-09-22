package org.example;

import java.util.*;

public class CustomHashMap<K, V> {
    private Node<K, V>[] table;
    private int size = 0;
    private int capacity = 16;

    private static class Node<K, V> implements Map.Entry<K, V> {
        final K key;
        V value;
        final Integer hash;

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
    }


    private Node<K, V> getNode(Object key) {
        if (table == null) {
            return null;
        }
        for (Node<K, V> kvNode : table) {
            if (kvNode != null) {
                if (key != null && kvNode.getHash() != null) {
                    if (kvNode.getHash() == key.hashCode()) return kvNode;
                } else if (kvNode.getHash() == null) return kvNode;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public CustomHashMap() {
        this.table = (Node<K, V>[]) new Node[size];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return table.length == 0;
    }

    public V get(Object key) {
        Node<K, V> node = getNode(key);
        if (node != null) {
            return node.getValue();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public V put(K key, V value) {
        Node<K, V> node = getNode(key);
        if (node == null) {
            if (key != null) {
                Integer hash = key.hashCode();
                node = new Node<K, V>(hash, key, value);
            } else {
                node = new Node<K, V>(null, null, value);
            }
            Node<K, V>[] newElement = new Node[]{node};
            Node<K, V>[] oldTable = table;
            if (table[0] != null) size = size + 1;
            table = (Node<K, V>[]) new Node[size];
            System.arraycopy(oldTable, 0, table, 0, oldTable.length);
            oldTable = null;
            System.arraycopy(newElement, 0, table, table.length - 1, newElement.length);
            newElement = null;
            return value;
        } else {
            Integer hash;
            if (key != null) {
                hash = key.hashCode();
                for (int i = 0; i < table.length; i++) {
                    if (table[i].getHash() != null) {
                        if (table[i].getHash().equals(hash)) {
                            V oldValue = table[i].getValue();
                            table[i].setValue(value);
                            Node<K, V>[] oldTable = table;
                            Node<K, V> newNode = table[i];
                            Node<K, V>[] newElement = new Node[]{newNode};
                            table = (Node<K, V>[]) new Node[size];
                            if (i > 0) System.arraycopy(oldTable, 0, table, 0, i);
                            System.arraycopy(newElement, 0, table, i, 1);
                            if (i < table.length - 1)
                                System.arraycopy(oldTable, i + 1, table, i + 1, oldTable.length - i);
                            return oldValue;
                        }
                    }
                }

            } else {
                for (int i = 0; i < table.length; i++) {
                    if (table[i].getHash() == null) {
                        V oldValue = table[i].getValue();
                        table[i].setValue(value);
                        Node<K, V>[] oldTable = table;
                        Node<K, V> newNode = table[i];
                        Node<K, V>[] newElement = new Node[]{newNode};
                        table = (Node<K, V>[]) new Node[size];
                        if (i > 0) System.arraycopy(oldTable, 0, table, 0, i);
                        System.arraycopy(newElement, 0, table, i, 1);
                        if (i < table.length - 1) System.arraycopy(oldTable, i + 1, table, i + 1, oldTable.length - i);
                        return oldValue;
                    }
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        Integer hash;
        if (key != null) {
            hash = key.hashCode();
            for (int i = 0; i < table.length; i++) {
                if (table[i] != null && table[i].getHash() != null) {
                    if (table[i].getHash().equals(hash)) {
                        size = size - 1;
                        Node<K, V>[] oldTable = table;
                        Node<K, V> removeNode = table[i];
                        table = (Node<K, V>[]) new Node[size];
                        System.arraycopy(oldTable, 0, table, 0, i);
                        System.arraycopy(oldTable, i + 1, table, i, oldTable.length - i - 1);
                        oldTable = null;
                        return removeNode.getValue();
                    }
                }

            }
        } else {
            hash = null;
            for (int i = 0; i < table.length; i++) {
                if (table[i].getHash() == null) {
                    size = size - 1;
                    Node<K, V>[] oldTable = table;
                    Node<K, V> removeNode = table[i];
                    table = (Node<K, V>[]) new Node[size];
                    System.arraycopy(oldTable, 0, table, 0, i);
                    System.arraycopy(oldTable, i + 1, table, i, oldTable.length - i - 1);
                    oldTable = null;
                    return removeNode.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash((Object) table);
    }
}

