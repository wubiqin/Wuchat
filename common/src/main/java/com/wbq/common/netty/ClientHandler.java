package com.wbq.common.netty;

import com.wbq.common.proto.BaseRequest;
import com.wbq.common.proto.BaseResponse;
import com.wbq.common.util.UUIDGenerator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  * @author biqin.wu
 *  * @since 30 January 2019
 *  
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<BaseResponse.Response> {

    private Channel channel;

    private static Map<Long, BaseResponse.Response> responseMap = new ConcurrentHashMap<>();

    public void send(BaseRequest.Request request) {
        channel.writeAndFlush(request);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseResponse.Response msg) throws Exception {
        log.info("receive msg={}", msg.toString());
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        channel = ctx.channel();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        log.error(cause.getMessage());
        ctx.close();
    }
}
