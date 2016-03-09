package fung.xiangh.lightrep;

/**
 * This interface is meant to represent an immutable constant repository
 */
public interface Repo<K,V> {
    V get(K key);
}
