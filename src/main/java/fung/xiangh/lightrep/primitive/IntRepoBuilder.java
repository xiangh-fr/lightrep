package fung.xiangh.lightrep.primitive;

import fung.xiangh.lightrep.Repo;
import fung.xiangh.lightrep.impl.DefaultRepoImpl;
import fung.xiangh.lightrep.primitive.impl.DefaultIntRepoImpl;
import fung.xiangh.lightrep.primitive.impl.DenseIntRepoImpl;

import java.util.HashMap;
import java.util.Map;

public class IntRepoBuilder<V> {
    private static final int DENSE_FACTOR = 3;
    private static final int DENSE_BY_DEFAULT_MAX = 3;

    private Map<Integer,V> storage = new HashMap<Integer, V>();
    private int min;
    private int max;

    public IntRepoBuilder<V> add(int key, V value)
    {
        storage.put(key, value);
        min=key<min?key:min;
        max=key>max?key:max;
        return this;
    }

    public IntRepo<V> build()
    {
        int spread=max-min;
        if(spread < Math.max(DENSE_BY_DEFAULT_MAX, DENSE_FACTOR*storage.size()))
        {
            return new DenseIntRepoImpl<V>(storage);
        }
        return new DefaultIntRepoImpl<V>(storage);
    }
}
