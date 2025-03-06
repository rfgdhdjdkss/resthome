package com.jinyang.resthome.controller;

import com.jinyang.resthome.common.Result;
import com.jinyang.resthome.pojo.dto.GoodsCommentRequest;
import com.jinyang.resthome.service.GoodsService;
import com.jinyang.resthome.service.GoodscommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 商品评论请求转发类
 * @ClassName: GoodsCommentController
 * @Author: jinyang
 * @Date: 2025/2/23 18:22
 * @Version: 1.0
 */
@RestController
@RequestMapping("/goodsComment")
public class GoodsCommentController {
    @Autowired
    private GoodscommentService goodscommentService;

    @GetMapping("/getAllComment/{gid}")
    public Result getAllComment(@PathVariable Long gid) {
        Result result = goodscommentService.selectAllComment(gid);
        return result;
    }
    @PostMapping("/addComment")
    public Result addComment(@RequestBody GoodsCommentRequest request) {
        Result result = goodscommentService.addGoodsComment(request.getUid(), request.getGid(), request.getContent());
        return result;
    }
}
