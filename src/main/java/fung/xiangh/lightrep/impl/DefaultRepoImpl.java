package fung.xiangh.lightrep.impl;

import fung.xiangh.lightrep.Repo;

import java.util.HashMap;
import java.util.Map;

/**
 * a last chance implementation, based on standard Map
 */
public class DefaultRepoImpl<K,V> extends HashMap<K,V> implements Repo<K,V> {

    public DefaultRepoImpl(Map<K,V> source)
    {
        super(source);
    }
}
