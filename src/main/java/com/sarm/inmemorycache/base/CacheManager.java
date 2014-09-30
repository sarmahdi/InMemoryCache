package com.sarm.inmemorycache.base;


/**
 * A Manager that is going to manage the caches, it is like a factory pattern,
 * it is a singleton. It holds all the types of caches that the application uses
 * and holds only one instance of each type. As there was no need, 
 * cache of type SPECIAL is only used to elaborate the pattern.
 *
 * @author sarm
 */
import com.sarm.inmemorycache.base.exception.CacheException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
public class CacheManager {

   

    Logger logger = Logger.getLogger(CacheManager.class);
    
    private static volatile CacheManager instance = null;

    private CacheType cacheType;
    private Cache defaultCache;

    
    /**
     * depending upon the  requirement/design the special cache will either be 
     * an extention of the Cache as a concrete class or just could differ on TTL
     */
    private Cache specialCache;

    // TTL for cacheItems in default cache
    long defaultTtl = 10;
    long intervalToClean = 1;

    private CacheManager() {
        defaultCache = new Cache(defaultTtl, intervalToClean);
    }
    
    private CacheManager(long ttl, long intervalToClean) {
        defaultCache = new Cache(ttl, intervalToClean);
    }
/**
 * a method to return a cache based on its type.
 * @param cacheType
 * @return
 * @throws CacheException 
 */
    public Cache retrieveCache(CacheType cacheType) throws CacheException {
        try{
        switch (cacheType) {
            case DEFAULT: {
                System.out.println("Returning default cache");
                logger.info("Returning default cache");
                return defaultCache;
            }
            case SPECIAL: {
                System.out.println("Sepcial Cache has not been implemented... "
                        + "returning null");
                 logger.info("Sepcial Cache has not been implemented... "
                        + "returning null");
                return null;
            }
            default:
                return defaultCache;
        }
        }
        catch (Exception e){
           logger.log(Level.ERROR, "There was a problem in retrieving the cache", e);
    
        throw new CacheException();
        }

    }

    /**
     *
     * @return
     */
    public static CacheManager getInstance() {
        if (instance == null) {
            synchronized (CacheManager.class) {
                instance = new CacheManager();
            }
        }
        return instance;
    }
    
     static CacheManager getInstance(long ttl, long intervalToClean) {
       if ( null == instance) {
            synchronized (CacheManager.class) {
                instance = new CacheManager(ttl, intervalToClean);
            }
        }else{ if (instance.defaultTtl!=ttl || instance.intervalToClean != intervalToClean){
        synchronized (CacheManager.class) {
                instance = new CacheManager(ttl, intervalToClean);
            }
       }}
      
        return instance;   }

     
     public Cache getDefaultCache() {
        return defaultCache;
    }

    public void setDefaultCache(Cache defaultCache) {
        this.defaultCache = defaultCache;
    }

    public Cache getSpecialCache() {
        return specialCache;
    }

    public void setSpecialCache(Cache specialCache) {
        this.specialCache = specialCache;
    }
}
