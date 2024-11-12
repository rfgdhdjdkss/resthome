package com.jinyang.resthome.pojo.dto;

import lombok.Data;

/**
 * @Description:
 * @ClassName: ElderlyIsCheckinedUpdateRequest
 * @Author: jinyang
 * @Date: 2024/11/12 21:00
 * @Version: 1.0
 */
@Data
public class ElderlyIsCheckinedUpdateRequest {
    private long eid;
    private Integer isCheckined;
}
