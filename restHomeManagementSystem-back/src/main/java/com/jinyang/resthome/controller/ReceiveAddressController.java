package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Receiveaddress;
import com.jinyang.resthome.service.ReceiveaddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @ClassName: ReceiveAddressController
 * @Author: jinyang
 * @Date: 2025/2/22 16:16
 * @Version: 1.0
 */
@RestController
@RequestMapping("/receiveAddress")
public class ReceiveAddressController {
    @Autowired
    private ReceiveaddressService receiveaddressService;

    /**
     * 获取某个用户的全部收货地址
     *
     * @param uid
     * @return
     */
    @GetMapping("/getReceiveAddressList/{uid}")
    public Result getReceiveAddressList(@PathVariable Long uid) {
        Result result = receiveaddressService.selectReceiveAddressListByUid(uid);
        return result;
    }

    /**
     * 新增收货地址
     *
     * @param receiveaddress
     * @return
     */
    @PostMapping("/addNewReceiveAddress")
    public Result addNewReceiveAddress(@RequestBody Receiveaddress receiveaddress) {
        Result result = receiveaddressService.insertReceiveAddress(receiveaddress);
        return result;
    }

    /**
     * 获取某个地址的全部信息
     *
     * @param raid
     * @return
     */
    @GetMapping("/getReceiveAddressByRaid/{raid}")
    public Result getReceiveAddressByRaid(@PathVariable Long raid) {
        Result result = receiveaddressService.selectReceiveAddressByRaid(raid);
        return result;
    }

    @PutMapping("/modifyReceiveAddress")
    public Result modifyReceiveAddress(@RequestBody Receiveaddress receiveaddress) {
        Result result = receiveaddressService.UpdateReceiveAddress(receiveaddress);
        return result;
    }

    @DeleteMapping("/deleteReceiveAddress/{raid}")
    public Result deleteReceiveAddress(@PathVariable Long raid) {
        Result result = receiveaddressService.deleteReceiveAddressByRaid(raid);
        return result;
    }
}
