package com.itheima.service.stat;

import java.util.List;
import java.util.Map;

/**
 * @author fanbo
 * @date 2020/7/25 23:09
 */
public interface StatService {

    //厂家销售数据
    List<Map> getFactoryData(String companyId);

    //商品销售数据
    List<Map> getSellData(String companyId);

    //系统访问压力  每小时的访问次数
    List<Map> getOnlineData(String companyId);
}
