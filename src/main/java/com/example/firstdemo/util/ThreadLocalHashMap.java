package com.example.firstdemo.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ThreadLocalHashMap<TKey, TValue> implements Map<TKey, TValue> {
    private final ThreadLocal<Map<TKey, TValue>> maps = ThreadLocal . withInitial(LinkedHashMap::new);
    public ThreadLocalHashMap() {}
    public int size() { return ((Map)this . maps.get()).size(); }
    public boolean isEmpty() { return ( (Map)this . maps.get()). isEmpty(); }
    public boolean containsKey(Object key) { return ( (Map)this . maps . get()). containsKey(key); }
    public boolean containsValue(Object value) { return ((Map)this . maps. get()). containsValue(value); }
    public TValue get(Object key) { return (TValue) ((Map)this.maps.get()).get(key); }
    public TValue put(TKey key, TValue value) { return (TValue) ((Map)this.maps.get()).put(key, value); }
    public TValue remove(Object key) { return (TValue) ( (Map)this.maps.get()).remove(key); }
    public void putAll(Map<? extends TKey, ? extends TValue> m) { ((Map)this . maps . get()). putAll(m); }
    public void clear() { ( (Map)this .maps.get()).clear(); }
    public java.util.Set<TKey> keySet() { return ((Map)this.maps.get()).keySet(); }
    public Collection<TValue> values() { return ( (Map)this.maps.get()).values(); }
    public Set<Entry<TKey, TValue>> entrySet() { return ((Map)this.maps.get()).entrySet(); }
}
