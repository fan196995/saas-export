package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.SysLog;

/**
 * @author fanbo
 * @date 2020/7/4 19:58
 */
public interface SysLogService {

    //分页查询所有
    PageInfo findAll(String companyId, int page, int size);

    int save(SysLog sysLog);

}
