package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.pojo.TransactionRecord;
import com.jinyang.resthome.service.TransactionRecordService;
import com.jinyang.resthome.mapper.TransactionRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
* @author jinyang
* @description 针对表【TransactionRecord】的数据库操作Service实现
* @createDate 2024-11-05 19:25:58
*/
@Service
public class TransactionRecordServiceImpl extends ServiceImpl<TransactionRecordMapper, TransactionRecord>
    implements TransactionRecordService {

    @Autowired
    private TransactionRecordMapper transactionRecordMapper;
    @Override
    public int insertTransactionRecord(TransactionRecord transactionRecord) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        transactionRecord.setTransactionTime(sdf.format(date.getTime()));
        int result = transactionRecordMapper.insert(transactionRecord);
        return result;
    }

    @Override
    public List selectAllTransactionByUid(Long uid) {
        QueryWrapper<TransactionRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        List<TransactionRecord> transactionRecords = transactionRecordMapper.selectList(queryWrapper);
        return transactionRecords;
    }
}




