package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.Consult;
import com.jinyang.resthome.service.ConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Description: 针对咨询业务的controller
 * @ClassName: ConsultController
 * @Author: jinyang
 * @Date: 2024/10/16 17:45
 * @Version: 1.0
 */
@RestController
@RequestMapping("/consult")
public class ConsultController {

    @Autowired
    private ConsultService consultService;

    /**
     * 新增聊天
     * @param consult
     * @return
     */
    @PostMapping("/addChat")
    public Result addChat(@RequestBody Consult consult) {
        System.out.println(consult.toString());
        Result result = consultService.addChat(consult);
        return result;
    }

    /**
     * 获取某个uid的所有聊天
     * @param uid
     * @return
     */
    @GetMapping("/getChat")
    public Result getChat(@RequestParam(value = "uid") long uid) {
        Result result = consultService.getChat(uid);
        return result;
    }

    /**
     * 管理员或员工获取的所有用户发送的咨询（每个用户为一个咨询）
     * @return
     */
    @GetMapping("/getConsultCollection")
    public Result getConsultCollection() {
        Result result = consultService.getConsultCollection();
        return result;
    }

    /**
     * 更新某个用户的咨询是否已经回复
     * @param whichUidConsult
     * @return
     */
    @PutMapping("/updateIsReply")
    public Result updateIsRead(@RequestParam(value = "whichUidConsult") Long whichUidConsult) {
        System.out.println(whichUidConsult);
        Result result = consultService.updateIsReply(whichUidConsult);
        return Result.ok(null);
    }
}
