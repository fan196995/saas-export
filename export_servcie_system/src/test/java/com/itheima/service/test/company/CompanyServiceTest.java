package com.itheima.service.test.company;

import com.itheima.domain.company.Company;
import com.itheima.service.company.CompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author fanbo
 * @date 2020/6/28 15:14
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/applicationContext-*.xml")
public class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Test
    public void testFindAll(){
        List<Company> all = companyService.findAll();
        System.out.println(all.toString());
    }

}
