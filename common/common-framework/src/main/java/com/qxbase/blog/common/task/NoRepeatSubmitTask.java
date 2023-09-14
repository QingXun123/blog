package com.qxbase.blog.common.task;

import com.qxbase.blog.common.singleton.SubmitBufferSingleton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;

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
        HashMap<String, LocalDateTime> hashMap = SubmitBufferSingleton.getInstance();
        hashMap.clear();
    }
}
