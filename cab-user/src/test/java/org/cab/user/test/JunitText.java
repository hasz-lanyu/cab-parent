package org.cab.user.test;

import org.cab.api.user.module.UserPageInfo;
import org.cab.api.user.service.UserOperationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JunitText {
    @Autowired
    UserOperationService userOperationService;

    @Test
    public void  pageSqlTest(){
        UserPageInfo c = userOperationService.selectUserByPage(1, 10, null);
//        System.out.println(c.getCount()+"------"+c.getUser().size());
        System.out.println("aaaaaaa");
    }
}
