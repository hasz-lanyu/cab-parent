package org.cab.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import com.alibaba.dubbo.config.annotation.Service;
import org.cab.api.admin.constan.AdminConst;
import org.cab.api.admin.module.UserVo;
import org.cab.api.user.module.User;
import org.cab.api.user.module.UserExample;
import org.cab.api.user.module.UserPageInfo;
import org.cab.api.user.service.UserOperationService;
import org.cab.common.exception.CustomException;
import org.cab.user.component.SnowFlakeComponent;
import org.cab.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Service
public class UserOperationServiceImpl implements UserOperationService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SnowFlakeComponent snowFlakeComponent;

    @Override
    public void updateLoginTimeById(Long id) {
        User user = new User();
        user.setId(id);
        user.setLastLoginTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void addUser(User user) {
        //盐
        String salt = snowFlakeComponent.getId().toString();
        //md5加密 加盐 31次哈希
        String password = new SimpleHash("md5", user.getPassword(), salt, AdminConst.Security
                .PASSWORD_ENCRYPTION_COUNT).toString();
        user.setSalt(salt);
        user.setPassword(password);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setStatus(1);
        user.setWorkId(userMapper.selectMaxWorkId() + 1);
        if (userMapper.insertSelective(user) != 1) {
            throw new CustomException("用户添加失败");
        }
    }

    @Override
    public User selectUserByUserName(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> list = userMapper.selectByExample(example);
        return list == null ? null : list.get(0);
    }

    @Override
    public List<User> selectAll() {
        PageHelper.startPage(1, 1000);
        return userMapper.selectByExample(null);
    }

    @Override
    public UserPageInfo selectUserByPage(Integer pageNumber, Integer pageSize, String key) {
        String like = StringUtils.isBlank(key) ? null : "%" + key + "%";
        PageHelper.startPage(pageNumber, pageSize);
        PageInfo<UserVo> pageInfo = new PageInfo<>(userMapper.selectUserInfo(like));
        UserPageInfo userPageInfo = new UserPageInfo();
        userPageInfo.setCounts(pageInfo.getTotal());
        userPageInfo.setUser(pageInfo.getList());
        return userPageInfo;

    }

    @Override
    public void updateUserById(User user) {
        user.setUpdateTime(new Date());
        if (userMapper.updateByPrimaryKeySelective(user) != 1) {
            throw new CustomException("用户修改失败");
        }
    }

    @Override
    public void deleteUserById(Long id) {
        if (userMapper.deleteByPrimaryKey(id) != 1) {
            throw new CustomException("用户删除失败");
        }
    }
}
