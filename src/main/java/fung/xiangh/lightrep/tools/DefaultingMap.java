package fung.xiangh.lightrep.tools;

import java.util.HashMap;

class DefaultingMap<K, V> extends HashMap<K, V> {

    final V defaultValue;

    DefaultingMap(V def) {
        defaultValue = def;
    }

    @Override
    public V get(Object key) {
        V res = super.get(key);
        return res == null ? defaultValue : res;
    }
}
