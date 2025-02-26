package com.jinyang.resthome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinyang.resthome.pojo.Order;
import com.jinyang.resthome.service.OrderService;
import com.jinyang.resthome.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author jinyang
* @description 针对表【order】的数据库操作Service实现
* @createDate 2025-02-25 23:38:32
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




