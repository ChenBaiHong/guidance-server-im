package com.healthan.liugong.guidance.server.tio;

import lombok.extern.slf4j.Slf4j;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.intf.Packet;
import org.tio.core.stat.IpStat;
import org.tio.core.stat.IpStatListener;
import org.tio.utils.json.Json;

/**
 * @ClassName IpStatListener
 * @Description TODO IP 监控
 * @Author baiHoo.chen
 * @Date 2019/7/1 16:10
 */
@Slf4j
public class IpListener implements IpStatListener {

    public static IpListener me = new IpListener();

    private IpListener(){}

    @Override
    public void onExpired(GroupContext groupContext, IpStat ipStat) {
        if (log.isInfoEnabled()) {
            log.debug("可以把统计数据入库\r\n{}", Json.toFormatedJson(ipStat));
        }
    }

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean b, boolean b1, IpStat ipStat) throws Exception {
        if (log.isInfoEnabled()) {
            log.debug("onAfterConnected\r\n{}", Json.toFormatedJson(ipStat));
        }
    }

    @Override
    public void onDecodeError(ChannelContext channelContext, IpStat ipStat) {
        if (log.isInfoEnabled()) {
            log.debug("onDecodeError\r\n{}", Json.toFormatedJson(ipStat));
        }
    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean b, IpStat ipStat) throws Exception {
        if (log.isInfoEnabled()) {
            log.debug("onAfterSent\r\n{}\r\n{}", packet.logstr(), Json.toFormatedJson(ipStat));
        }
    }

    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int i, IpStat ipStat) throws Exception {
        if (log.isInfoEnabled()) {
            log.debug("onAfterDecoded\r\n{}\r\n{}", packet.logstr(), Json.toFormatedJson(ipStat));
        }
    }

    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int i, IpStat ipStat) throws Exception {
        if (log.isInfoEnabled()) {
            log.debug("onAfterReceivedBytes\r\n{}", Json.toFormatedJson(ipStat));
        }
    }

    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, IpStat ipStat, long l) throws Exception {
        if (log.isInfoEnabled()) {
            log.debug("onAfterHandled\r\n{}\r\n{}", packet.logstr(), Json.toFormatedJson(ipStat));
        }
    }
}
