package com.naman.keyvaluestore.storage;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class Storage {

    private static final Storage INSTANCE = new Storage();
    private final ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> ttl = new ConcurrentHashMap<>();

    private Storage() {
    }

    public static Storage getInstance() {
        return INSTANCE;
    }

    public void set(String key, Object value, long ttlInSec) {
        data.put(key, value);
        if (ttlInSec > 0) {
            ttl.put(key, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(ttlInSec));
        } else {
            ttl.remove(key);
        }
    }

    public Object get(String key) {
        Long expiry = ttl.get(key);
        if (expiry != null && System.currentTimeMillis() > expiry) {
            data.remove(key);
            ttl.remove(key);
            return null;
        }
        return data.get(key);
    }

}
