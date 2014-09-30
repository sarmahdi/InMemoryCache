/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sarm.inmemorycache.base;

import com.sarm.inmemorycache.base.exception.CacheException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author sarm
 */
public class CacheManagerTest {

    Logger logger = Logger.getLogger(CacheManagerTest.class);

    public CacheManagerTest() {
    }

    /**
     * A test case to test the TTL of a defaultCache with the default TTL and
     * intervalToClean
     *
     * @throws InterruptedException
     */
    @Test
    public void test1TTL() throws InterruptedException {
        try {
            logger.info("Initiating Test 1 for TTL  ");
            CacheManager cacheManager = CacheManager.getInstance();
            Cache<String, String> cache = cacheManager.retrieveCache(CacheType.DEFAULT);

            cache.put("user1", "Syed");
            cache.put("user2", "Mahdi");
             // Adding 3 seconds sleep.. Both above objects will be removed from
            // Cache because of timeToLiveInSeconds value
            Thread.sleep(3000);
            assertEquals(0, cache.size());
            System.out.println("Two objects are added but reached timeToLive. cache.size(): " + cache.size());
        } catch (CacheException ex) {
            logger.log(Level.ERROR, "There was a problem in retrieving the cache", ex);
        }

    }

    @Test
    public void test2TTL() throws InterruptedException {
// Test with timeToLiveInSeconds = 1 second
        // timerIntervalInSeconds = 1 second
        logger.info("Initiating Test 2 for TTL  ");
        CacheManager cacheManager = CacheManager.getInstance(1, 1);
        Cache<String, String> cache = null;
        try {
            cache = cacheManager.retrieveCache(CacheType.DEFAULT);
        } catch (CacheException ex) {
            logger.log(Level.ERROR, "There was a problem in retrieving the cache", ex);
        }
try{
        cache.put("user1", "Syed");
        Thread.sleep(1000);
        cache.put("user2", "Mahdi");

        logger.info("size of cache  " + cache.size());
        assertEquals(1, cache.size());
        System.out.println("Two objects are added but  1 reached timeToLive. cache.size(): " + cache.size());
}catch(NullPointerException npe){
      logger.log(Level.ERROR, "There was a problem in retrieving the cache", npe);
     
}
    }

    /**
     * Test of retrieveCache method, of class CacheManager.
     */
    @Test
    public void testRetrieveCache() {

        logger.info("Initiating Test for retrieving a cache  ");
        System.out.println("retrieveCache");
        CacheType cacheType = null;
        CacheManager instance = CacheManager.getInstance();
        Cache expResult = null;
        Cache result = null;
        try {
            result = instance.retrieveCache(CacheType.DEFAULT);
        } catch (CacheException ex) {
            logger.log(Level.ERROR , "Error in retreiving cache", ex);
        }

        assertTrue(result != null);

    }

    /**
     * Test of getInstance method, of class CacheManager.
     */
    @Test
    public void testGetInstance() {
        logger.info("Initiating Test  for creating a Manager  ");
        System.out.println("getInstance");

        CacheManager result = CacheManager.getInstance();
//        assertEquals(expResult, result);
        assertTrue(result != null);

    }

    /**
     *
     * @throws InterruptedException
     */
    @Test
    public void testCache() throws InterruptedException {
        logger.info("Initiating Test for cache and its cleanup");
        int size = 500;

        // Test with timeToLiveInSeconds = 100 seconds
        // timerIntervalInSeconds = 100 seconds
        // maxItems = 500000
        Cache<String, String> cache = new Cache<String, String>(100, 100);

        for (int i = 0; i < size; i++) {
            String value = Integer.toString(i);
            cache.put(value, value);
        }

        Thread.sleep(200);
        logger.info("size of cache before remove " + cache.size());
        long start = System.currentTimeMillis();
        cache.removeCacheItems();
        double finish = (double) (System.currentTimeMillis() - start) / 1000.0;
        logger.info("size of cache after remove " + cache.size());
        System.out.println("Cleanup times for " + size + " objects are " + finish + " s");

    }

}
