/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sarm.inmemorycache.base;

/**
 * Ideally cache needs to be divided based on types. With items TTL being only
 * 10 seconds it means its not User related.  Each Type should have its own 
 * cache. Here we have only one cache type. 
 * @author sarm
 */
public enum CacheType {
    DEFAULT,SPECIAL
}

