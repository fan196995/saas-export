package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ContractExample;
import com.itheima.service.cargo.ContractService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @author fanbo
 * @date 2020/7/13 21:40
 */

@Controller
@RequestMapping(value = "/cargo/contract")
public class ContractController extends BaseController {

    @Reference
    private ContractService contractService;

    @RequestMapping(value = "/list",name = "合同列表查询")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);

        if (loginUser.getDegree()==3){
            //主管，增加创建部门条件
            criteria.andCreateDeptEqualTo(loginUser.getDeptId());
        }else if (loginUser.getDegree()==4){
            //普通员工，增加创建人条件
            criteria.andCreateByEqualTo(loginUser.getId());
        }else if (loginUser.getDegree()==2){
            //管理部门以及下属成员
            criteria.andCreateDeptLike(loginUser.getDeptId()+"%");
        }

        PageInfo pageInfo = contractService.findAll(example, page, size);
        request.setAttribute("page",pageInfo);
        return "cargo/contract/contract-list";
    }

    @RequestMapping(value = "/toAdd")
    public String toAdd(){
        return "cargo/contract/contract-add";
    }

    @RequestMapping(value = "/edit")
    public String edit(Contract contract){
        contract.setCompanyId(companyId);
        contract.setCompanyName(companyName);
        contract.setState(0);

        if (StringUtil.isEmpty(contract.getId())){

            //新增写入创建人和创建部门
            contract.setCreateBy(loginUser.getId());
            contract.setCreateDept(loginUser.getDeptId());

            contract.setCreateTime(new Date());
            contract.setUpdateTime(new Date());
            contractService.save(contract);
        }else {
            contract.setUpdateTime(new Date());
            contractService.update(contract);
        }

        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping(value = "/toUpdate")
    public String toUpdate(String id){
        Contract contract = contractService.findById(id);
        request.setAttribute("contract", contract);
        return "cargo/contract/contract-update";
    }

    @RequestMapping(value = "/delete")
    public String delete(String id){
        contractService.delete(id);
        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping(value = "/submit")
    public String submit(String id){
        Contract contract = contractService.findById(id);
        contract.setState(1);
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping(value = "/cancel")
    public String cancel(String id){
        Contract contract = contractService.findById(id);
        contract.setState(0);
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }

}
