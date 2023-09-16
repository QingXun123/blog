package com.qxbase.blog.server.essay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxbase.blog.common.exception.ServiceException;
import com.qxbase.blog.data.entity.EssayInfo;
import com.qxbase.blog.data.vo.EssayInfoOutPutVo;
import com.qxbase.blog.server.essay.mapper.EssayInfoMapper;
import com.qxbase.blog.server.essay.service.IEssayInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EssayInfoServiceImpl extends ServiceImpl<EssayInfoMapper, EssayInfo> implements IEssayInfoService {

    @Resource
    private EssayInfoMapper essayInfoMapper;

    @Override
    public List<EssayInfo> getEssayListByType(Integer type) {
        if (type != 1 && type != 2) {
            throw new ServiceException(300, "不存在这个类型的文章");
        }
        List<EssayInfo> list = this.list(new LambdaQueryWrapper<EssayInfo>().eq(EssayInfo::getType, type));
        return list;
    }

    @Override
    public IPage<EssayInfoOutPutVo> pageOfAuthor(Page page) {
        return essayInfoMapper.pageOfAuthor(page, new QueryWrapper<EssayInfo>());
    }

    @Override
    public IPage<EssayInfoOutPutVo> pageOfAuthor(Page page, QueryWrapper<EssayInfo> queryWrapper) {
        return essayInfoMapper.pageOfAuthor(page, queryWrapper);
    }

    @Override
    public boolean readEssay(Long essayId) {
        EssayInfo essayInfo = this.getById(essayId);
        if (essayInfo == null) {
            throw new ServiceException(300, "不存在这个文章");
        }
        essayInfo.setReadingQuantity(essayInfo.getReadingQuantity() + 1);
        return this.updateById(essayInfo);
    }
}
