package com.yanger.shiro.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

@SuppressWarnings("unchecked")
public class ShiroCacheManager<K, V> extends AbstractCacheManager implements Cache<K, V> {

	private RedisTemplate<String, Object> redisTemplate;
	
	private String prefix = "shiro_redis:";
	
	public ShiroCacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate){
		this.redisTemplate = redisTemplate;
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}
	
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void clear() throws CacheException {
		redisTemplate.getConnectionFactory().getConnection().flushDb();
	}

	@Override
	public V get(K k) throws CacheException {
		if (k == null) {
			return null;
		}
		return (V) redisTemplate.opsForValue().get(k);
	}

	@Override
	public Set<K> keys() {
		Set<K> keys = (Set<K>) redisTemplate.keys((String) (prefix + "*"));
		Set<K> sets = new HashSet<>();
		for (K key : keys) {
			sets.add(key);
		}
		return sets;
	}

	@Override
	public V put(K k, V v) throws CacheException {
		if (k == null || v == null) {
			return null;
		}
		redisTemplate.opsForValue().set((String) k, v);
		return v;
	}

	@Override
	public V remove(K k) throws CacheException {
		if (k == null) {
			return null;
		}
		V v = (V) redisTemplate.opsForValue().get(k);
		redisTemplate.delete((String) k);
		return v;
	}

	@Override
	public int size() {
		return redisTemplate.getConnectionFactory().getConnection().dbSize().intValue();
	}

	@Override
	public Collection<V> values() {
		Set<K> keys = keys();
		List<V> values = new ArrayList<V>(keys.size());
		for (K k : keys) {
			values.add(get(k));
		}
		return values;
	}

	@Override
	protected Cache createCache(String name) throws CacheException {
		this.prefix = name;
		return this;
	}

}
