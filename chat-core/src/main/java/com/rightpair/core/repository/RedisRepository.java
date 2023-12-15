package com.rightpair.core.repository;

import java.util.Optional;

public interface RedisRepository<T> {
    void save(T entity);

    Optional<T> findByKey(String key);

    Boolean deleteByKey(String key);
}
