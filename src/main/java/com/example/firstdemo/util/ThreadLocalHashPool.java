package com.example.firstdemo.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public abstract class ThreadLocalHashPool<TKey, TValue> {
    private final ThreadLocalHashMap<TKey, TValue> threadLocalMap = new ThreadLocalHashMap();

    public ThreadLocalHashPool() {
    }

    public static <K, V> ThreadLocalHashPool<K, V> withInitial(Function<? super K, ? extends V> function) {
        return new ThreadLocalHashPool.FuncThreadLocalHashPool(function);
    }

    protected abstract TValue initialValue(TKey var1);

    public TValue get(TKey key) {
        return this.threadLocalMap.computeIfAbsent(key, this::initialValue);
    }

    public TValue remove(TKey key) {
        return this.threadLocalMap.remove(key);
    }

    public void clear() {
        this.threadLocalMap.clear();
    }

    public Set<TKey> keySet() {
        return this.threadLocalMap.keySet();
    }

    public Collection<TValue> values() {
        return this.threadLocalMap.values();
    }

    public Set<Map.Entry<TKey, TValue>> entrySet() {
        return this.threadLocalMap.entrySet();
    }

    static final class FuncThreadLocalHashPool<TKey, TValue> extends ThreadLocalHashPool<TKey, TValue> {
        private final Function<? super TKey, ? extends TValue> function;

        FuncThreadLocalHashPool(Function<? super TKey, ? extends TValue> function) {
            this.function = (Function) Objects.requireNonNull(function);
        }

        protected TValue initialValue(TKey key) {
            return this.function.apply(key);
        }
    }
}