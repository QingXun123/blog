package com.qxbase.blog.common.task;

import com.qxbase.blog.common.singleton.SubmitBufferSingleton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author QingXun123
 * @version 1.0.0
 * @since 2023-08-29
 */
@Component
@Slf4j
public class NoRepeatSubmitTask {

    @Scheduled(cron = "0 0 1 * * ?")
    public void start() {
        HashMap<String, Long> hashMap = SubmitBufferSingleton.getInstance();
        Iterator<Map.Entry<String, Long>> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> next = iterator.next();
            String key = next.getKey();
            Long value = next.getValue();
            if (value > Instant.now().toEpochMilli()) {
                hashMap.remove(key);
            }
        }
        // 如果对时间没有特别严格的要求就直接clear
//        hashMap.clear();
    }
}
