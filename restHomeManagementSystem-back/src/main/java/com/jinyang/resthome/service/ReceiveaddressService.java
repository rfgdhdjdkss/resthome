package com.jinyang.resthome.service;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Receiveaddress;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jinyang
* @description 针对表【receiveAddress】的数据库操作Service
* @createDate 2025-02-22 16:16:23
*/
public interface ReceiveaddressService extends IService<Receiveaddress> {

    Result selectReceiveAddressListByUid(Long uid);

    Result insertReceiveAddress(Receiveaddress receiveaddress);

    Result selectReceiveAddressByRaid(Long raid);

    Result UpdateReceiveAddress(Receiveaddress receiveaddress);

    Result deleteReceiveAddressByRaid(Long raid);
}
