package org.cab.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.cab.admin.anno.CheckParam;
import org.cab.admin.component.OssComponent;
import org.cab.admin.component.SendMessageComponent;
import org.cab.admin.service.MenuService;
import org.cab.admin.utils.WebUtil;
import org.cab.api.admin.module.MenuVo;
import org.cab.api.admin.module.LoginParam;
import org.cab.api.authen.service.UserAuthenService;
import org.cab.api.message.MessageParam;
import org.cab.api.user.module.User;
import org.cab.api.user.module.UserPageInfo;
import org.cab.api.user.service.UserOperationService;
import org.cab.common.exception.CustomException;
import org.cab.common.resp.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private SendMessageComponent sendMessageComponent;
    @Reference
    private UserAuthenService userAuthenService;
    @Autowired
    private OssComponent ossComponent;
    @Reference(cluster = "failfast")
    private UserOperationService userOperationService;
    @Autowired
    private MenuService menuService;

    /**
     * 用户登录
     *
     * @param
     * @param
     */
    @CheckParam
    @ResponseBody
    @PostMapping("/dologin")
    public Result doLogin(@RequestBody @Valid LoginParam loginParam, BindingResult bindingResult) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(loginParam.getUsername(),
                    loginParam.getPassword());
            token.setRememberMe(loginParam.isRememberMe());
            SecurityUtils.getSubject().login(token);
            //修改 登录时间
            sendMessageComponent.send(MessageParam.User.USER_EXCHANGE,
                    MessageParam.User.UPDATELOGINTIME_ROUTINGKEY,
                    ((User) SecurityUtils.getSubject().getPrincipal()).getId());

        } catch (AuthenticationException e) {
            return Result.error("账号密码错误");
        }
        return Result.ok();
    }

    /**
     * 页面跳转到 index 并且获取 菜单栏数据
     *
     * @return
     */
    @GetMapping({"/", "/index"})
    public String toIndex() {
        Result result = getMenu();
        WebUtil.getRequest().setAttribute("r", result);
        Result userInfo = getUserInfo();
        WebUtil.getSession().setAttribute("r", userInfo);
        return "test";
    }

    /**
     * 菜单栏数据
     * 获取
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/menu")
    public Result getMenu() {
        try {
            List<MenuVo> menuVos = menuService.selectMenuWithChildAll();
            return Result.ok(menuVos);
        } catch (Exception e) {
            log.error("菜单数据获取失败[{}]", e.getMessage());

        }
        return Result.error("菜单数据获取失败");
    }


    /**
     * 获取用户信息
     */
    @ResponseBody
    @GetMapping("/userInfo")
    public Result getUserInfo() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //防止用户信息数据修改 查到的还是旧数据 重新查数据库
        user = userAuthenService.selectUserByUserName(user.getUsername());
        user.setPassword("*");
        user.setSalt("*");
        return Result.ok(user);
    }

    /**
     * 用户添加
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/user")
    public Result addUser(@RequestBody User user) {
        try {
            userOperationService.addUser(user);

        } catch (CustomException e) {
            return Result.error(e.getMessage());
        }
        return Result.ok();
    }

    @ResponseBody
    @PutMapping("/user")
    public Result updateUser(@RequestBody User user) {
        try {
            userOperationService.updateUserById(user);
        } catch (CustomException e) {
            return Result.error(e.getMessage());
        }
        return Result.ok();
    }
    @ResponseBody
    @DeleteMapping("/user/{id}")
    public Result deleteUser(@PathVariable("id") Long id) {
        try {
            userOperationService.deleteUserById(id);
        } catch (CustomException e) {
            return Result.error(e.getMessage());
        }
        return Result.ok();
    }

    /**
     * 获取用户信息
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页显示数量
     * @param key        模糊搜索条件
     * @return
     */
    @ResponseBody
    @GetMapping("/user")
    public Result getUser(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                          @RequestParam(value = "key", defaultValue = "") String key) {
        UserPageInfo list = userOperationService.selectUserByPage(pageNumber, pageSize, key);

        return Result.ok(list.getUser(), list.getCounts().intValue());
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/member-add")
    public String toMemberAdd() {
        return "member-add";
    }
    @GetMapping("/user/list")
    public String userList(){
        return "include/common::userList";
    }


}
