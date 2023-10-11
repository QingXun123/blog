package com.qxbase.blog.server.essay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxbase.blog.common.exception.ServiceException;
import com.qxbase.blog.data.entity.EssayInfo;
import com.qxbase.blog.data.dto.EssayInfoDto;
import com.qxbase.blog.server.essay.mapper.EssayInfoMapper;
import com.qxbase.blog.server.essay.service.IEssayInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EssayInfoServiceImpl extends ServiceImpl<EssayInfoMapper, EssayInfo> implements IEssayInfoService {

    @Resource
    private EssayInfoMapper essayInfoMapper;

    public List<EssayInfo> getNewEssayList() {
        List<EssayInfo> list = this.list(new LambdaQueryWrapper<EssayInfo>()
                .gt(EssayInfo::getReleaseTime, this.getAfterOneMonthByNowTime())
                .orderBy(true, false, EssayInfo::getReleaseTime));
        return list;
    }

    @Override
    public List<EssayInfo> getEssayListByType(Integer type) {
        if (type != 1 && type != 2) {
            throw new ServiceException(300, "不存在这个类型的文章");
        }
        List<EssayInfo> list = this.list(new LambdaQueryWrapper<EssayInfo>()
                .eq(EssayInfo::getType, type)
                .orderBy(true, false, EssayInfo::getReleaseTime));
        return list;
    }

    @Override
    public IPage<EssayInfoDto> pageOfAuthor(Page page) {
        return essayInfoMapper.pageOfAuthor(page, new QueryWrapper<EssayInfo>());
    }

    @Override
    public IPage<EssayInfoDto> pageOfAuthor(Page page, QueryWrapper<EssayInfo> queryWrapper) {
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

    @Override
    public boolean existsById(Long id) {
        EssayInfo byId = this.getById(id);
        return byId != null;
    }

    private String getAfterOneMonthByNowTime() {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 计算前一个月
        LocalDateTime oneMonthAgo = now.minus(Period.ofMonths(3));

        // 格式化输出
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedOneMonthAgo = oneMonthAgo.format(formatter);

        return formattedOneMonthAgo;
    }
}
