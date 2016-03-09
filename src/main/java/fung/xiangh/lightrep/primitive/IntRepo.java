package fung.xiangh.lightrep.primitive;

/**
 * variant of repo for int-mappable keys
 * (might create other dedicated types later...)
 */
public interface IntRepo<V> {
    V get(int key);
}
