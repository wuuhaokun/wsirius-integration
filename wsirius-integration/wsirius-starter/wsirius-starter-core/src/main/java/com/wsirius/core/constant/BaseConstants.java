package com.wsirius.core.constant;

/**
 * 系统级常量类
 *
 * @author bojiangzhou 2017-12-28
 */
public class BaseConstants {

    /**
     * 标识：是/否、启用/禁用等
     */
    public interface Flag {

        Integer YES = 1;

        Integer NO = 0;
    }

    /**
     * 性别
     */
    public interface Sex {
        /**
         * 男
         */
        Integer MALE = 1;
        /**
         * 女
         */
        Integer FEMALE = 0;
    }

}
