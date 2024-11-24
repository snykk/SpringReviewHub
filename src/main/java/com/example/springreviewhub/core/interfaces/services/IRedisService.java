package com.example.springreviewhub.core.interfaces.services;

import java.util.concurrent.TimeUnit;

/**
 * Interface for Redis Service.
 * <p>
 * This interface defines the contract for interacting with a Redis data store.
 * It provides methods for storing, retrieving, and deleting data in Redis,
 * with support for setting expiration times for stored values.
 * </p>
 */
public interface IRedisService {

    /**
     * Store a value in Redis with an optional expiration time.
     * <p>
     * This method allows storing a value associated with a specified key in Redis.
     * An expiration time can be set to automatically remove the entry after the specified duration.
     * </p>
     *
     * @param key     the key under which the value will be stored
     * @param value   the value to store
     * @param timeout the expiration time for the entry; if zero or negative, the entry will not expire
     * @param unit    the time unit of the expiration time (e.g., seconds, minutes)
     */
    void set(String key, String value, long timeout, TimeUnit unit);

    /**
     * Retrieve a value from Redis by its key.
     * <p>
     * This method fetches the value associated with the specified key from Redis.
     * If the key does not exist, it returns {@code null}.
     * </p>
     *
     * @param key the key of the value to retrieve
     * @return the value associated with the key, or {@code null} if the key does not exist
     */
    String get(String key);

    /**
     * Delete a key and its associated value from Redis.
     * <p>
     * This method removes the specified key and its value from Redis,
     * freeing up storage and ensuring the key is no longer accessible.
     * </p>
     *
     * @param key the key to delete from Redis
     */
    void del(String key);
}
