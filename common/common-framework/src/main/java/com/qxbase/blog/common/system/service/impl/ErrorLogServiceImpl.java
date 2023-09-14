package com.qxbase.blog.common.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxbase.blog.common.system.ErrorLog;
import com.qxbase.blog.common.system.mapper.ErrorLogMapper;
import com.qxbase.blog.common.system.service.IErrorLogService;
import org.springframework.stereotype.Service;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-06-01
 */
@Service
public class ErrorLogServiceImpl extends ServiceImpl<ErrorLogMapper, ErrorLog> implements IErrorLogService {
}
