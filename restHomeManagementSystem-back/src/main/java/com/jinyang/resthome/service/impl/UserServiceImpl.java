package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.common.ResultCodeEnum;
import com.jinyang.resthome.pojo.User;
import com.jinyang.resthome.service.UserService;
import com.jinyang.resthome.mapper.UserMapper;
import com.jinyang.resthome.util.JwtHelper;
import com.jinyang.resthome.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jinyang
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-10-02 20:35:36
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录业务实现代码
     *
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {
        //用户名或密码为空，返回用户名错误
        if (user.getUsername().isEmpty()) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        } else if (user.getPassword().isEmpty()) {
            return Result.build(null, 508, "密码为空");
        } else {
            //条件构造器，去数据库找与用户名相同的记录返回
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", user.getUsername()).eq("permission", user.getPermission());
            List<User> users = userMapper.selectList(wrapper);
            if (!users.isEmpty()) {
                //判断查询记录的密码字段的值与参数user加密后的密码是否相同
                if (users.get(0).getPassword().equals(MD5Util.encrypt(user.getPassword()))) {
                    String token = JwtHelper.createToken(user.getUid(), user.getUsername());
                    //登录成功后将登录时间写入数据库
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    users.get(0).setLastLoginTime(sdf.format(date.getTime()));
                    Map<String, Object> data = new HashMap<>();
                    data.put("uid", users.get(0).getUid());
                    data.put("token", token);
                    data.put("username", users.get(0).getUsername());
                    data.put("nickname", users.get(0).getNickname());
                    data.put("permission", users.get(0).getPermission());
                    data.put("balance", users.get(0).getBalance());
                    data.put("headImgUrl", users.get(0).getHeadImgUrl());
                    return Result.ok(data);
                }
            } else {
                return Result.build(null, 506, "该权限的用户名不存在");
            }
        }
        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     * 注册业务实现代码
     *
     * @param user
     * @return
     */
    @Override
    public Result register(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        List<User> users = userMapper.selectList(wrapper);
        if (users.size() != 0) {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        } else {
            //密码进行MD5加密
            user.setPassword(MD5Util.encrypt(user.getPassword()));
            //将用户名作为昵称传入，后续用户可根据自身情况修改
            user.setNickname(user.getUsername());
            int result = userMapper.insert(user);
            return Result.ok(result);
        }
    }

    /**
     * 查询所有除去管理员用户信息业务实现代码
     *
     * @param page
     * @return
     */
    @Override
    public Page<User> findAllUser(Page<User> page) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //条件构造器，权限不等于管理员
        queryWrapper.ne("permission", "管理员");
        Page<User> users = userMapper.selectPage(page, queryWrapper);
        return users;
    }

    /**
     * 根据id删除用户业务实现代码
     *
     * @param uid
     * @return
     */
    @Override
    public Result deleteUserByUid(Long uid) {
        int result = userMapper.deleteById(uid);
        if (result != 1) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        } else {
            return Result.ok(null);
        }
    }

    /**
     * 搜索框模糊查询业务实现代码
     *
     * @param page
     * @param searchValue
     * @return
     */
    @Override
    public Page<User> selectBySearchValue(Page<User> page, String searchValue) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(User::getUsername, searchValue).or().like(User::getNickname, searchValue);
        Page<User> users = userMapper.selectPage(page, wrapper);
        return users;
    }

    /**
     * 根据uid更新数据业务实现代码
     *
     * @param user
     * @return
     */
    @Override
    public Result updateUserByUsername(User user) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("uid", user.getUid());
        int update = userMapper.update(user,wrapper);
        return Result.ok(null);
    }


    /**
     * //根据uid获取余额业务实现代码
     *
     * @param uid
     * @return
     */
    @Override
    public List selectBalance(Long uid) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        List list = userMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 根据uid充值业务实现代码
     *
     * @param uid
     * @param recharge
     * @return
     */
    @Override
    public int rechargeBalance(long uid, Double recharge) {
        User user = userMapper.selectById(uid);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("uid", uid);
        updateWrapper.set("balance", recharge + user.getBalance());
        int update = userMapper.update(updateWrapper);
        return update;
    }

    /**
     * 根据uid扣除余额业务实现代码
     *
     * @param uid
     * @param money
     * @return
     */
    @Override
    public int deductBalance(long uid, Double money) {
        User user = userMapper.selectById(uid);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("uid", uid);
        updateWrapper.set("balance", user.getBalance() - money);
        int update = userMapper.update(updateWrapper);
        return update;
    }

    /**
     * 根据uid查找用户信息业务实现代码
     * @param uid
     * @return
     */
    @Override
    public User selectUserById(Long uid) {
        User user = userMapper.selectById(uid);
        return user;
    }

    @Override
    public Result updatePassword(Long uid, String originalPassword, String newPassword1, String newPassword2) {
        User user = userMapper.selectById(uid);
        if (user.getPassword().equals(MD5Util.encrypt(originalPassword))) {
            if (newPassword1.equals(newPassword2)) {
                user.setPassword(MD5Util.encrypt(newPassword1));
                int result = userMapper.updateById(user);
                return Result.ok(result);
            }
            else {
                return Result.build(null,510,"两次输入的密码不一致");
            }
        }
        return Result.build(null,511,"原密码错误");

    }

    @Override
    public void updateHeadImgUrlByUid(String newFileName, Long uid) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("uid", uid);
        updateWrapper.set("headImgUrl", newFileName);
        userMapper.update(updateWrapper);
    }


}




