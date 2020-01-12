package com.healthan.liugong.guidance.server.tio;

import lombok.extern.slf4j.Slf4j;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerStarter;

/**
 * @ClassName TioWebsocketStarter
 * @Description TODO TIO 配置文件
 * @Author baiHoo.chen
 * @Date 2019/7/1 16:59
 */
@Slf4j
public class TioWebsocketStarter {

    private WsServerStarter wsServerStarter;
    private ServerGroupContext serverGroupContext;

    /**
     *
     * @Author baihoo.chen
     * @Description TODO
     * @Date 2019/7/1
     * @param port int
     * @param wsMsgHandler TioWsMsgHandler
     * @return
     **/
    public TioWebsocketStarter(int port, TioWsMsgHandler wsMsgHandler) throws Exception {

        // 设置 websocket 启动器
        wsServerStarter = new WsServerStarter(port, wsMsgHandler);
        // 设置 服务组上下文
        serverGroupContext = wsServerStarter.getServerGroupContext();

        // 设置自定义的协议名称
        serverGroupContext.setName(TioServerConfig.PROTOCOL_NAME);
        // 设置 websocket 服务监控实现类
        serverGroupContext.setServerAioListener(ServerAioListener.me);

        //设置ip监控
        serverGroupContext.setIpStatListener(IpListener.me);
        //设置ip统计时间段
        serverGroupContext.ipStats.addDurations(TioServerConfig.IpStatDuration.IPSTAT_DURATIONS);
        //设置心跳超时时间
        serverGroupContext.setHeartbeatTimeout(TioServerConfig.HEARTBEAT_TIMEOUT);
        //证书必须和域名相匹配，否则可能访问不了ssl
//		String keyStoreFile = "classpath:ssl/www.lzihospital.com.jks";
//		String trustStoreFile = "classpath:ssl/www.lzihospital.com.jks";
//		String keyStorePwd = "ihospital";
//		serverGroupContext.useSsl(keyStoreFile, trustStoreFile, keyStorePwd);
    }

    /**
     *
     * @Author baihoo.chen
     * @Description TODO
     * @Date 2019/7/1 23:24
     **/
    public WsServerStarter getWsServerStarter() {
        return this.wsServerStarter;
    }
}
