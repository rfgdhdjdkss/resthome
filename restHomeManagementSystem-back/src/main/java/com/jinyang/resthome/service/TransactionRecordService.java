package com.jinyang.resthome.service;

import com.jinyang.resthome.pojo.TransactionRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author jinyang
* @description 针对表【TransactionRecord】的数据库操作Service
* @createDate 2024-11-05 19:25:58
*/
public interface TransactionRecordService extends IService<TransactionRecord> {

    int insertTransactionRecord(TransactionRecord transactionRecord);

    List selectAllTransactionByUid(Long uid);
}
