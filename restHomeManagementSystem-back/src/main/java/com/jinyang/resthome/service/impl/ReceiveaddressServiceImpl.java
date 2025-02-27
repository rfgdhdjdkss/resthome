package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.common.ResultCodeEnum;
import com.jinyang.resthome.pojo.Receiveaddress;
import com.jinyang.resthome.service.ReceiveaddressService;
import com.jinyang.resthome.mapper.ReceiveaddressMapper;
import com.jinyang.resthome.util.PhoneNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jinyang
 * @description 针对表【receiveAddress】的数据库操作Service实现
 * @createDate 2025-02-22 16:16:23
 */
@Service
public class ReceiveaddressServiceImpl extends ServiceImpl<ReceiveaddressMapper, Receiveaddress>
        implements ReceiveaddressService {
    @Autowired
    private ReceiveaddressMapper receiveaddressMapper;

    @Override
    public Result selectReceiveAddressListByUid(Long uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        List<Receiveaddress> result = receiveaddressMapper.selectList(queryWrapper);
        if (result.size() > 0) {
            return Result.ok(result);
        } else {
            return Result.build(null, ResultCodeEnum.SELECT_EMPTY);
        }
    }

    @Override
    public Result insertReceiveAddress(Receiveaddress receiveaddress) {
        if (PhoneNumberUtils.isValidPhoneNumber(receiveaddress.getReceivePhone())) {
            int result = receiveaddressMapper.insert(receiveaddress);
            if (result > 0) {
                return Result.ok(receiveaddress);
            } else {
                return Result.build(null, ResultCodeEnum.INSERT_FAIL);
            }

        } else {
            return Result.build(null, ResultCodeEnum.PHONE_ILLEGAL);
        }
    }

    @Override
    public Result selectReceiveAddressByRaid(Long raid) {

        Receiveaddress receiveaddress = receiveaddressMapper.selectById(raid);
        if (receiveaddress != null) {
            return Result.ok(receiveaddress);
        } else {
            return Result.build(null, ResultCodeEnum.SELECT_EMPTY);
        }
    }

    @Override
    public Result UpdateReceiveAddress(Receiveaddress receiveaddress) {
        if (PhoneNumberUtils.isValidPhoneNumber(receiveaddress.getReceivePhone())) {
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("receiveAddressId", receiveaddress.getReceiveAddressId());
            int update = receiveaddressMapper.update(receiveaddress, updateWrapper);
            if (update > 0) {
                return Result.ok(receiveaddress);
            } else {
                return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
            }
        } else {
            return Result.build(null, ResultCodeEnum.PHONE_ILLEGAL);
        }
    }

    @Override
    public Result deleteReceiveAddressByRaid(Long raid) {
        int result = receiveaddressMapper.deleteById(raid);
        if (result > 0) {
            return Result.ok(raid);
        } else {
            return Result.build(null, ResultCodeEnum.DELETE_ERROR);
        }
    }

    @Override
    public Result selectReceiveAddressListByRaid(Long raid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("receiveAddressId", raid);
        List<Receiveaddress> result = receiveaddressMapper.selectList(queryWrapper);
        if (result.size() > 0) {
            return Result.ok(result);
        }
        return Result.build(null, ResultCodeEnum.SELECT_EMPTY);
    }


}




