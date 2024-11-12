package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.TransactionRecord;
import com.jinyang.resthome.service.TransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @ClassName: TransactionRecordController
 * @Author: jinyang
 * @Date: 2024/11/5 19:11
 * @Version: 1.0
 */
@RestController
@RequestMapping("/transaction")
public class TransactionRecordController {
    @Autowired
    private TransactionRecordService transactionRecordService;

    @PostMapping("/addIn")
    public Result addTransactionRecord(@RequestBody TransactionRecord transactionRecord) {
        int result= transactionRecordService.insertTransactionRecord(transactionRecord);
        return Result.ok(result);
    }
    @GetMapping("/findAllTransactionRecord/{uid}")
    public Result findAllTransactionRecord(@PathVariable Long uid) {
        List result = transactionRecordService.selectAllTransactionByUid(uid);
        return Result.ok(result);
    }

}
