package com.jinyang.resthome.pojo.dto;

import lombok.Data;

/**
 * @Description:
 * @ClassName: HealthStatusRequest
 * @Author: jinyang
 * @Date: 2025/3/14 18:16
 * @Version: 1.0
 */
@Data
public class HealthStatusRequest {
    private Long eid;
    private Integer status;

}
