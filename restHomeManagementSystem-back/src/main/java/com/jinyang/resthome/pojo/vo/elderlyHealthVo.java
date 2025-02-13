package com.jinyang.resthome.pojo.vo;

import lombok.Data;

/**
 * @Description:
 * @ClassName: eldersListHealthVo
 * @Author: jinyang
 * @Date: 2025/2/13 15:19
 * @Version: 1.0
 */
@Data
public class elderlyHealthVo {
    private Long hid;
    private Long eid;
    private String elderlyName;
    private String elderlyAge;
    private String heartRate;
    private String bloodPressure;
    private String temperature;
    private String bedroom;
    private String status;

    public elderlyHealthVo(Long hid, Long eid, String elderlyName, String elderlyAge, String heartRate, String bloodPressure, String temperature, String bedroom, String status) {
        this.hid = hid;
        this.eid = eid;
        this.elderlyName = elderlyName;
        this.elderlyAge = elderlyAge;
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
        this.bedroom = bedroom;
        this.status = status;
    }

    public elderlyHealthVo(Long hid, Long eid, String elderlyName, String elderlyAge, String heartRate, String bloodPressure, String temperature, String bedroom) {
        this.hid = hid;
        this.eid = eid;
        this.elderlyName = elderlyName;
        this.elderlyAge = elderlyAge;
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
        this.bedroom = bedroom;
    }

    public elderlyHealthVo(Long hid, Long eid, String elderlyName, String elderlyAge, String heartRate, String bloodPressure, String temperature) {
        this.hid = hid;
        this.eid = eid;
        this.elderlyName = elderlyName;
        this.elderlyAge = elderlyAge;
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "eldersListHealthVo{" +
                "hid=" + hid +
                ", eid=" + eid +
                ", elderlyName='" + elderlyName + '\'' +
                ", elderlyAge='" + elderlyAge + '\'' +
                ", heartRate='" + heartRate + '\'' +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", temperature='" + temperature + '\'' +
                '}';
    }
}
