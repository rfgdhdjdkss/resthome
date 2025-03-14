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
    Result login(User user);                                            //登录业务
    Result register(User user);                                         //注册业务
    Page<User> findAllUser(Page<User> page);                            //查询所有用户信息业务
    Result deleteUserByUid(Long uid);                                   //根据id删除用户业务
    Page<User> selectBySearchValue(Page<User> page, String searchValue);//搜索框模糊查询业务
    Result updateUserByUsername(User user);                             //根据username更新数据业务
    List selectBalance(Long uid);                                       //根据uid获取余额业务
    int rechargeBalance(long uid, Double recharge);                     //根据uid充值业务
    int deductBalance(long uid, Double money);                          //根据uid扣除余额业务
    User selectUserById(Long uid);                                      //根据uid查找用户信息业务
    Result updatePassword(Long uid, String originalPassword, String newPassword1, String newPassword2); //修改密码业务
    void updateHeadImgUrlByUid(String newFileName, Long uid);           //根据id修改用户头像业务
    Result loginByUsername(loginByUsernameRequest user);                //通过用户名登录业务（app端）
    Result loginByPhone(loginByUsernameRequest user);                   //通过手机号登录业务（app端）
    Result registerWithApp(User user);                                  //注册业务（app端）
    Result updatePhone(Long uid, String phone);                         //修改用户绑定的手机号业务
    Result updateNickname(Long uid, String nickname);                   //修改用户昵称业务
    Result updateSex(Long uid, String sex);                             //修改用户性别业务
}
