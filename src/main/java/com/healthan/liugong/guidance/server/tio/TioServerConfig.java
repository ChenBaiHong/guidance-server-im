package com.healthan.liugong.guidance.server.tio;

import org.tio.utils.time.Time;

/**
 * @ClassName TioServerConfig
 * @Description TODO TIO 配置文件
 * @Author baiHoo.chen
 * @Date 2019/7/1 16:54
 */
public class TioServerConfig  {

    /** 协议名字(可以随便取，主要用于开发人员辨识) */
    public static final String PROTOCOL_NAME = "wswx";

    /** 字符集 */
    public static final String CHARSET = "utf-8";

    /** 监听端口 */
    public static final int SERVER_PORT = 29326;

    /** 心跳超时时间，单位：毫秒 */
    public static final int HEARTBEAT_TIMEOUT = 1000 * 60;

    /** ip数据监控统计，时间段  */
    public  interface IpStatDuration {
        Long DURATION_1 = Time.MINUTE_1 * 5;
        Long[] IPSTAT_DURATIONS = new Long[]{DURATION_1};
    }
}
