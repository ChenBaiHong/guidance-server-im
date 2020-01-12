package com.healthan.liugong.guidance.server.model;

import lombok.Data;

/**
 * @ClassName Message
 * @Description TODO
 * @Author baiHoo.chen
 * @Date 2019/7/1 17:40
 */
@Data
public class Message {


    private Long msgId;

    private String username;


    private String avatar;


    private Long toId;


    private Integer chatType;


    private Integer identType;


    private String content;

    private Integer contentType;


    private Boolean mine;


    private Long fromId;


    private Long timestamp;
}
