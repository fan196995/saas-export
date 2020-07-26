package com.itheima.web.controller.stat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.stat.StatService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author fanbo
 * @date 2020/7/25 23:42
 */
@Controller
@RequestMapping(value = "/stat")
public class StatController extends BaseController {

    @Reference
    private StatService statService;

    @RequestMapping(value = "/toCharts")
    public String toCharts(String chartsType){
        return "stat/stat-"+chartsType;
    }

    @RequestMapping(value = "/getFactoryData")
    public @ResponseBody List<Map> getFactoryData(){
        return statService.getFactoryData(companyId);
    }

    @RequestMapping(value = "/getSellData")
    public @ResponseBody List<Map> getSellData(){
        return statService.getSellData(companyId);
    }

    @RequestMapping(value = "/getOnlineData")
    public @ResponseBody List<Map> getOnlineData(){
        return statService.getOnlineData(companyId);
    }
}
