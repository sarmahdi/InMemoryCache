/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sarm.inmemorycache.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.log4j.Logger;

/**
 * This is the main class for the Cache it self. It also includes an inner class
 * CacheItem which makes every cached value as a cachedItem. 
 * @author sarm
 */
public class Cache<K, V> {
  Logger logger = Logger.getLogger(Cache.class);
    
    /**
     * Idea behind using the Concurrent HashMap was that it has a more finer
     * grained lock mechanism when writing the cached object or looking up Key
     * value pairs. While a Collections.SynchronizedMap(HashMap) will use a
     * single Map wide lock for any CRUD operation. 
     * 
     * Assumption : there will be a lots of cached objects and would be 
     * accessed simultaneously by different threads.
     */
    private ConcurrentMap mapofCachedItems;
    /**
     * a time to live this is same for each cached item
     *
     */
    
    private long ttl;

    protected static Cache instance;

    /**
     *
     * @param ttl - time to live for each item
     * @param timerInterval - interval after which the clean up should occur in
     * a loop in a thread
     */
    public Cache(long ttl, final long timerInterval) {
        this.ttl = ttl * 1000;

        this.mapofCachedItems = new ConcurrentHashMap();

        if (ttl > 0 && timerInterval > 0) {

            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(timerInterval * 1000);
                        } catch (InterruptedException ex) {
                        }
                        removeCacheItems();
                    }
                }
            });
/**
 * Making it Daemon makes it terminate when the main thread terminates
 * technically it runs in background and JVM does not need to care if this 
 * thread is finished and can exit easily.
 */
            t.setDaemon(true);
            t.start();
        }
    }
   

    public void put(K key, V value) {

        mapofCachedItems.put(key, new CacheItem(value));
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {

        CacheItem cachedItem = (CacheItem) mapofCachedItems.get(key);

        if (cachedItem == null) {
            return null;
        } else {
            //resetting the last accessed so that it does not go into removeCacheItems 
            //after 10sec
            cachedItem.lastAccessed = System.currentTimeMillis();
            return cachedItem.value;
        }

    }

    public void remove(K key) {

        mapofCachedItems.remove(key);

    }

    public int size() {

        return mapofCachedItems.size();

    }
/**
 * this method removes any cachedItems that are stale or passed their TTL
 */
    @SuppressWarnings("unchecked")
    public void removeCacheItems() {

       /**
        * This is a list of keys that have passed their TTL
        */
        ArrayList<K> staleKeys = null;

        long currentTime = System.currentTimeMillis();
        Iterator itr;
        itr = this.mapofCachedItems.keySet().iterator();


        
         staleKeys = new ArrayList<K>();
      
          K key = null;
        CacheItem cachedItem = null;

        while (itr.hasNext()) {
            key = (K) itr.next();
            cachedItem = (CacheItem) mapofCachedItems.get(key);

            if (cachedItem != null && (currentTime > (ttl + cachedItem.lastAccessed))) {
                staleKeys.add(key);
            }
        }

        for (K keyToRemove : staleKeys) {
//             logger.info("removing stale keys");
            mapofCachedItems.remove(keyToRemove);
//             logger.info("stalekeys removed");
        }
            Thread.yield();
        }
    


    /**
     * An inner class for cached Item 
     */
    protected class CacheItem {

        long lastAccessed;
        V value;

        protected CacheItem(V value) {
            this.value = value;
        }
    }
}
