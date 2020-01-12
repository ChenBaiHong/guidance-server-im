package com.healthan.liugong.guidance.server.model;

import lombok.Data;

/**
 * @ClassName SendInfo
 * @Description TODO 通讯的json 封装
 * @Author baiHoo.chen
 * @Date 2019/7/1 17:40
 */
@Data
public class SendInfo {


    /** 发送信息的代码 */
    private Integer code;

    /** 信息 */
    private Message message;
}
