package com.wsirius.core.redis;

/**
 * redis database ThreadLocal
 *
 * @author bojiangzhou 2018/08/28
 */
public class RedisDatabaseThreadLocal {

    private RedisDatabaseThreadLocal() { }

    private static final ThreadLocal<Integer> THREAD_DB = new ThreadLocal<>();

    /**
     * 更改当前线程 RedisTemplate db
     * 
     * @param db set current redis db
     */
    public static void set(int db) {
        THREAD_DB.set(db);
    }

    /**
     * @return get current redis db
     */
    public static Integer get() {
        return THREAD_DB.get();
    }

    /**
     * 清理
     */
    public static void clear() {
        THREAD_DB.remove();
    }

}
