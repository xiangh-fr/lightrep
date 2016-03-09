package fung.xiangh.lightrep.primitive.impl;

import fung.xiangh.lightrep.Repo;
import fung.xiangh.lightrep.primitive.IntRepo;

import java.util.HashMap;
import java.util.Map;

/**
 * a last chance implementation, based on standard Map
 */
public class DefaultIntRepoImpl<V> extends HashMap<Integer,V> implements IntRepo<V> {

    public DefaultIntRepoImpl(Map<Integer,V> source)
    {
        super(source);
    }

    @Override
    public V get(int key) {
        return super.get(key);
    }
}
