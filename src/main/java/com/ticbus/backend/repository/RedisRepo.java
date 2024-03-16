package com.ticbus.backend.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author AnhLH
 */
@Repository
public class RedisRepo {

  private static String KEY;
  private RedisTemplate<String, String> redisTemplate;
  private HashOperations<String, Integer, String> hashOperations;

  public String getItem(int itemId) {
    return hashOperations.get(KEY, itemId);
  }

  public void addItem(String item, int id) {
    hashOperations.put(KEY, id, item);
  }

  public void deleteItem(int id) {
    hashOperations.delete(KEY, id);
  }

  public void updateItem(String item, int id) {
    addItem(item, id);
  }
}
