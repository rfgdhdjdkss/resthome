package com.jinyang.resthome.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.User;
import com.jinyang.resthome.pojo.dto.UserBalanceUpdateRequest;
import com.jinyang.resthome.pojo.dto.UserPasswordRequest;
import com.jinyang.resthome.pojo.dto.loginByUsernameRequest;
import com.jinyang.resthome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @ClassName: UserController
 * @Author: jinyang
 * @Date: 2024/10/4 17:32
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录功能
     *
     * @param user
     * @return 状态码+data
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        Result result = userService.login(user);
        return result;
    }

    /**
     * app端登录功能----账号密码登录
     * @param user
     * @return
     */
    @PostMapping("/loginByUsername")
    public Result loginByUsername(@RequestBody loginByUsernameRequest user) {
        System.out.println(user.toString());

        Result result =userService.loginByUsername(user);
        return result;
    }

    /**
     * app端登录功能----手机号验证码方式登录
     * @param user
     * @return
     */
    @PostMapping("/loginByPhone")
    public Result loginByPhone(@RequestBody loginByUsernameRequest user) {
        System.out.println(user.toString());
        Result result =userService.loginByPhone(user);
        return result;
    }

    /**
     * web管理端注册功能
     *
     * @param user
     * @return 状态码+data
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        Result result = userService.register(user);
        return result;
    }

    /**
     * app端注册功能
     * @param user
     * @return
     */
    @PostMapping("/registerWithApp")
    public Result registerWithApp(@RequestBody User user) {
        Result result = userService.registerWithApp(user);
        return result;
    }
    /**
     * 用户管理页，查询用户信息
     *
     * @return 所有用户信息
     */
    @GetMapping("/selectAllUser")
    public Result<Object> selectAllUser(
            @RequestParam(value = "currentPage") long currentPage,
            @RequestParam(value = "pageSize") long pageSize
    ) {
        Page<User> page = new Page<>(currentPage, pageSize);
        Page<User> userPage = userService.findAllUser(page);
        System.out.println(userPage.getRecords());
        Map<String, Object> data = new HashMap<>();
        data.put("records", userPage.getRecords());
        data.put("total", userPage.getTotal());
        data.put("pages", userPage.getPages());
        data.put("pageSize", userPage.getSize());
        data.put("currentPage", currentPage);
        data.put("hasNext", userPage.hasNext());
        data.put("hasPrevious", userPage.hasPrevious());
        return Result.ok(data);
    }

    /**
     * 根据ID删除用户信息
     *
     * @param uid
     * @return
     */
    @DeleteMapping("/deleteUser/{uid}")
    public Result deleteUser(@PathVariable("uid") Long uid) {

        Result result = userService.deleteUserByUid(uid);
        return result;
    }

    /**
     * 根据搜索框模糊查询
     *
     * @param currentPage
     * @param pageSize
     * @param searchValue
     * @return
     */
    @GetMapping("/selectForSearch")
    public Result<Object> selectForSearch(
            @RequestParam(value = "currentPage") long currentPage,
            @RequestParam(value = "pageSize") long pageSize,
            @RequestParam(value = "searchValue") String searchValue
    ) {
        Page<User> page = new Page<>(currentPage, pageSize);
        Page<User> userPage = userService.selectBySearchValue(page, searchValue);
        Map<String, Object> data = new HashMap<>();
        data.put("records", userPage.getRecords());
        data.put("total", userPage.getTotal());
        data.put("pages", userPage.getPages());
        data.put("pageSize", userPage.getSize());
        data.put("currentPage", currentPage);
        data.put("hasNext", userPage.hasNext());
        data.put("hasPrevious", userPage.hasPrevious());
        return Result.ok(data);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody User user) {
        System.out.println(user);
        Result result = userService.updateUserByUsername(user);
        return Result.ok(result);
    }

    /**
     * 根据uid获取用户账户余额
     *
     * @param uid
     * @return
     */
    @GetMapping("/selectBalance/{uid}")
    public Result<Object> selectBalance(@PathVariable("uid") Long uid) {
        List user = userService.selectBalance(uid);
        return Result.ok(user.get(0));
    }

    /**
     * 根据uid充值，向用户账户增加余额
     *
     * @param request
     * @return
     */
    @PutMapping("/rechargeBalance")
    public Result updateBalance(@RequestBody UserBalanceUpdateRequest request
    ) {
        int result = userService.rechargeBalance(request.getUid(), request.getMoney());
        return Result.ok(result);
    }

    /**
     * 根据uid，扣除余额
     *
     * @param request
     * @return
     */
    @PutMapping("/deductBalance")
    public Result deductBalance(@RequestBody UserBalanceUpdateRequest request
    ) {
        int result = userService.deductBalance(request.getUid(), request.getMoney());
        return Result.ok(result);
    }

    /**
     * 根据uid查找用户信息
     *
     * @param uid
     * @return
     */
    @GetMapping("/findUser/{uid}")
    public Result<Object> findUser(@PathVariable("uid") Long uid) {
        User user = userService.selectUserById(uid);
        return Result.ok(user);
    }

    @PutMapping("/modifyPwd")
    public Result<Object> updatePassword(@RequestBody UserPasswordRequest request) {
        Result result = userService.updatePassword(request.getUid(), request.getOriginalPassword(), request.getNewPassword1(), request.getNewPassword2());
        return result;
    }
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
