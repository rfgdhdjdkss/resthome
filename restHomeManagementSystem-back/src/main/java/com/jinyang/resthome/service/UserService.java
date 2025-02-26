package com.jinyang.resthome.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinyang.resthome.pojo.dto.loginByUsernameRequest;

import java.util.List;

/**
 * @author jinyang
 * @description 针对表【user】的数据库操作Service
 * @createDate 2024-10-02 20:35:36
 */
public interface UserService extends IService<User> {


    //登录业务
    public Result login(User user);

    //注册业务
    public Result register(User user);

    //查询所有用户信息业务
    public Page<User> findAllUser(Page<User> page);

    //根据id删除用户业务
    public Result deleteUserByUid(Long uid);

    //搜索框模糊查询业务
    public Page<User> selectBySearchValue(Page<User> page, String searchValue);

    //根据username更新数据业务
    public Result updateUserByUsername(User user);

    //根据uid获取余额业务
    public List selectBalance(Long uid);

    //根据uid充值业务
    public int rechargeBalance(long uid, Double recharge);

    //根据uid扣除余额业务
    public int deductBalance(long uid, Double money);

    //根据uid查找用户信息业务
    User selectUserById(Long uid);

    Result updatePassword(Long uid, String originalPassword, String newPassword1, String newPassword2);

    void updateHeadImgUrlByUid(String newFileName, Long uid);

    Result loginByUsername(loginByUsernameRequest user);

    Result loginByPhone(loginByUsernameRequest user);

    Result registerWithApp(User user);

    Result updatePhone(Long uid, String phone);

    Result updateNickname(Long uid, String nickname);

    Result updateSex(Long uid, String sex);
}
