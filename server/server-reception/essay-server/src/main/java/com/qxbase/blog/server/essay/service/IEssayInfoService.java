package com.qxbase.blog.server.essay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxbase.blog.data.entity.EssayInfo;

import java.util.List;

public interface IEssayInfoService extends IService<EssayInfo> {

    List<EssayInfo> getEssayListByType(Integer type);

}
