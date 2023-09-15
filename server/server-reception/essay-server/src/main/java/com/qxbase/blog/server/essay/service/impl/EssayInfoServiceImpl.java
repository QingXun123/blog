package com.qxbase.blog.server.essay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxbase.blog.common.exception.ServiceException;
import com.qxbase.blog.data.entity.EssayInfo;
import com.qxbase.blog.server.essay.mapper.EssayInfoMapper;
import com.qxbase.blog.server.essay.service.IEssayInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EssayInfoServiceImpl extends ServiceImpl<EssayInfoMapper, EssayInfo> implements IEssayInfoService {


    @Override
    public List<EssayInfo> getEssayListByType(Integer type) {
        if (type != 1 && type != 2) {
            throw new ServiceException(300, "不存在这个类型的文章");
        }
        List<EssayInfo> list = this.list(new LambdaQueryWrapper<EssayInfo>().eq(EssayInfo::getType, type));
        return list;
    }
}
