package fung.xiangh.lightrep;

import fung.xiangh.lightrep.impl.DefaultRepoImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * The aim is to create an optimized repo, based on fixed content.
 * The repoBuilder is meant for intermediary data collection and its performances are secondary
 * (thus, first  implementation will be backed by a regular hashMap)
 */
public class RepoBuilder<K,V> {

    private Map<K,V> storage = new HashMap<K,V>();

    public RepoBuilder<K,V> add(K key, V value)
    {
        storage.put(key, value);
        return this;
    }

    public Repo build()
    {
        return new DefaultRepoImpl<K,V>(storage);
    }
}
