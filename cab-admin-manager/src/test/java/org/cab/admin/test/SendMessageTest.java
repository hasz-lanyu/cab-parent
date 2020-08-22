package org.cab.admin.test;

import org.cab.admin.component.OssComponent;
import org.cab.admin.component.SendMessageComponent;
import org.cab.admin.service.MenuService;
import org.cab.api.admin.module.MenuVo;
import org.cab.api.message.MessageParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SendMessageTest {

    @Autowired
    SendMessageComponent sendMessageComponent;

    @Autowired
    OssComponent  ossComponent;

    @Autowired
    MenuService menuService;


    @Test
    public void  sendMessageTest(){
        sendMessageComponent.send("testExchange","testRoutingKey","222",null);
        sendMessageComponent.send(MessageParam.User.USER_EXCHANGE,"testFail2Queue","fail",null);
    }
    @Test
    public void imgUploadTest() throws IOException {
        File file = new File("C:\\Users\\Administrator\\Desktop\\proj\\unnamed.png");
        FileInputStream fis = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("unnamed.png","unnamed.png","image/png",fis);
        String zhanguoheng = ossComponent.uploadImg( multipartFile);
        System.out.println(zhanguoheng);
    }

    @Test
    public void testMenu() throws Exception {

            List<MenuVo> menuVos = menuService.selectMenuWithChildAll();

    }
}
