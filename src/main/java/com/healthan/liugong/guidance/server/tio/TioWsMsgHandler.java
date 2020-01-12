package com.healthan.liugong.guidance.server.tio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthan.liugong.guidance.server.model.Message;
import com.healthan.liugong.guidance.server.model.SendInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseStatus;
import org.tio.utils.lock.SetWithLock;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

/**
 * @ClassName TioWsMsgHandler
 * @Description TODO websocket 处理函数
 * @Author baiHoo.chen
 * @Date 2019/7/1 17:01
 */
@Component
@Slf4j
public class TioWsMsgHandler implements IWsMsgHandler {


    /**
     * @param httpRequest    HttpRequest
     * @param httpResponse   HttpResponse
     * @param channelContext ChannelContext
     * @return HttpResponse
     * @Author baihoo.chen
     * @Description TODO 握手时走这个方法，业务可以在这里获取cookie，request参数等
     * @Date 2019/7/1
     **/
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) {
        // 1. 获取 聊天识别的唯一id
        String unionId = httpRequest.getParam("unionId");
        try {
            // 4. 绑定用户
            Tio.bindUser(channelContext, unionId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("通信握绑定唯一识别ID异常：{}", e.getMessage());
            httpResponse.setStatus(HttpResponseStatus.C401);
        }
        return httpResponse;
    }

    /**
     * @param httpRequest    HttpRequest
     * @param httpResponse   HttpResponse
     * @param channelContext ChannelContext
     * @return
     * @throws Exception Exception
     * @Author baihoo.chen
     * @Description TODO 握手之后走这个方法
     * @Date 2019/7/1 17:30
     **/
    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {

    }

    /**
     * @param wsRequest      WsRequest
     * @param bytes          byte[]
     * @param channelContext ChannelContext
     * @return Object
     * @Author baihoo.chen
     * @Description TODO 字节消息（binaryType = arraybuffer）过来后会走这个方法
     * @Date 2019/7/1 17:23
     **/
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * @param wsRequest      WsRequest
     * @param bytes          byte[]
     * @param channelContext ChannelContext
     * @return Object
     * @Author baihoo.chen
     * @Description TODO 当客户端发close flag时，会走这个方法
     * @Date 2019/7/1 17:25
     **/
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        Tio.remove(channelContext, "receive close flag");


        return null;
    }

    /**
     * @param wsRequest      WsRequest
     * @param text           String
     * @param channelContext ChannelContext
     * @return
     * @Author baihoo.chen
     * @Description TODO  字符消息（binaryType = blob）过来后会走这个方法
     * @Date 2019/7/1 17:31
     **/
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        try {
            // 1. 定义映射对象字节解析器
            ObjectMapper objectMapper = new ObjectMapper();
            // 2. 读取字符串文本解析成对象
            SendInfo sendInfo = objectMapper.readValue(text, SendInfo.class);
            // 3. 心跳检测包
            if (0 == sendInfo.getCode()) {
                WsResponse wsResponse = WsResponse.fromText(text, TioServerConfig.CHARSET);
                Tio.send(channelContext, wsResponse);
            }
            // 4. 真正的消息
            else if (1 == sendInfo.getCode()) {
                // 4.1. 取得消息
                Message message = sendInfo.getMessage();
                // 4.2. 是否被人发送，false
                message.setMine(false);
                // 4.4. 在线诊聊
                SetWithLock<ChannelContext> channelContextSetWithLock = Tio.getChannelContextsByUserid(
                        channelContext.groupContext,
                        message.getToId() + "");
                //用户没有登录，存储到离线文件
                if (channelContextSetWithLock == null || channelContextSetWithLock.size() == 0) {
                    // 回写消息给来源用户
                    WsResponse wsResponse2 = WsResponse.fromText(objectMapper.writeValueAsString(sendInfo), TioServerConfig.CHARSET);
                    Tio.sendToUser(channelContext.groupContext, message.getFromId() + "", wsResponse2);
                }
                //用户在线登陆，储存到及时回复文件
                else {
                    WsResponse wsResponse = WsResponse.fromText(objectMapper.writeValueAsString(sendInfo), TioServerConfig.CHARSET);
                    Tio.sendToUser(channelContext.groupContext, message.getToId() + "", wsResponse);
                }
            }
        } catch (Exception e) {
            log.error("发送消息出现异常：{}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
