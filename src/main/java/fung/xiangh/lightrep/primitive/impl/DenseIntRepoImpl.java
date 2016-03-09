package fung.xiangh.lightrep.primitive.impl;

import fung.xiangh.lightrep.Repo;
import fung.xiangh.lightrep.primitive.IntRepo;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class DenseIntRepoImpl<V> implements IntRepo {

    private final int base;
    private final Object[] data;

    public DenseIntRepoImpl(Map<Integer, V> source) {
        Collection<Integer> keys = source.keySet();
        base = Collections.min(keys);
        int size = Collections.max(keys) - base + 1;
        data = new Object[size];
        for (Map.Entry<Integer, V> entry : source.entrySet()) {
            data[entry.getKey() - base] = entry.getValue();
        }
    }

    @Override
    public V get(int key) {
        int offset = key - base;
        if (offset < 0 || offset >= data.length) {
            return null;
        }
        return (V) data[offset];
    }
}
