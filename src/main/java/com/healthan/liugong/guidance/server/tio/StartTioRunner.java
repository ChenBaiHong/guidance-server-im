package com.healthan.liugong.guidance.server.tio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName StartTioRunner
 * @Description TODO 开启类
 * @Author baiHoo.chen
 * @Date 2019/7/1 16:53
 */
@Component
public class StartTioRunner implements CommandLineRunner {

    @Resource
    private TioWsMsgHandler tioWsMsgHandler;

    private TioWebsocketStarter appStarter;

    /**
     *
     * @Author baihoo.chen
     * @Description TODO 启动 webSocket 服务
     * @Date 2019/7/1 23:39
     * @param args String[]
     * @return void
     **/
    @Override
    public void run(String... args) throws Exception {
        this.appStarter = new TioWebsocketStarter(TioServerConfig.SERVER_PORT, tioWsMsgHandler);
        appStarter.getWsServerStarter().start();
    }
}
