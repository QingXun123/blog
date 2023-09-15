package com.qxbase.blog.server.essay.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxbase.blog.data.entity.EssayInfo;
import com.qxbase.blog.server.essay.mapper.EssayInfoMapper;
import com.qxbase.blog.server.essay.service.IEssayInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EssayInfoServiceImpl extends ServiceImpl<EssayInfoMapper, EssayInfo> implements IEssayInfoService {

}
