package com.qxbase.blog.common.singleton;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @author QingXun123
 * @version 1.0.0
 * @since 2023-08-29
 */
public class SubmitBufferSingleton {

    private static HashMap<String, LocalDateTime> hashMap = new HashMap<>();

    private SubmitBufferSingleton() {
    }

    public static HashMap<String, LocalDateTime> getInstance() {
        return hashMap;
    }
}
