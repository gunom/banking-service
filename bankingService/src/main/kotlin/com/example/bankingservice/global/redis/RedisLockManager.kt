package com.example.bankingservice.global.redis

import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisLockManager(
    private val redissonClient: RedissonClient
) {
    fun acquireLock(key: String): RLock {
        val lock = redissonClient.getLock(key)
        lock.lock()
        return lock
    }

    fun releaseLock(lock: RLock) {
        lock.unlock()
    }
}