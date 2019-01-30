package com.wbq.common.util;

import java.util.UUID;

/**
 *  * @author biqin.wu
 *  * @since 30 January 2019
 *  
 */
public class UUIDGenerator {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
