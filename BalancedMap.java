//Zoe Sarwar cssc0735
//Stephanie Bekker cssc0754

package edu.sdsu.cs.datastructures;

import java.util.LinkedList;
import java.util.TreeMap;

public class BalancedMap<K extends Comparable<K>,V> implements IMap<K,V> {

    TreeMap<K,V> contents = new TreeMap<>();

    public BalancedMap(IMap<K,V> source){
        for(K key : source.keyset()){
            contents.put(key, source.getValue(key));
        }
    }

    public BalancedMap(){

    }

    @Override
    public boolean contains(K key) {
        return contents.containsKey(key);
    }

    @Override
    public boolean add(K key, V value) {
        if(contents.containsKey(key)) return false;
        contents.put(key, value);
        return true;
    }

    @Override
    public V delete(K key) {
        return contents.remove(key);
    }

    @Override
    public V getValue(K key) {
        return contents.get(key);
    }

    @Override
    public K getKey(V value) {
        for(K key: contents.keySet()){
            if(value.equals(contents.get(key))){
                return key;
            }
        }
        return null;
    }

    @Override
    public Iterable<K> getKeys(V value) {
        Iterable<K> toReturn = new LinkedList<>();
        for(K key: contents.keySet()){
            if(value.equals(contents.get(key))){
                ((LinkedList<K>) toReturn).add(key);
            }
        }
        return toReturn;
    }

    @Override
    public int size() {
        return contents.size();
    }

    @Override
    public boolean isEmpty() {
        return size()== 0;
    }

    @Override
    public void clear() {
        contents.clear();
    }

    @Override
    public Iterable<K> keyset() {
        Iterable<K> toReturn = new LinkedList<>();
        toReturn = contents.keySet();
        return toReturn;
    }

    @Override
    public Iterable<V> values() {
        Iterable<V> toReturn = new LinkedList<>();
        toReturn = contents.values();
        return toReturn;
    }
}