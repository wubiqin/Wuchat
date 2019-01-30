package com.wbq.common.netty;

import com.wbq.common.proto.BaseRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  * @author biqin.wu
 *  * @since 30 January 2019
 *  
 */
public class Client {
    private static Map<String, Client> clientMap = new ConcurrentHashMap<>();

    private String host;

    private Integer port;

    @Setter
    private Channel channel;

    @Setter
    private EventLoopGroup group;

    public Client(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public static Client getConnect(String host, int port) {
        if (clientMap.containsKey(host + port)) {
            return clientMap.get(host + port);
        }
        Client client = connect(host, port);
        clientMap.put(host + port, client);
        return client;
    }

    private static Client connect(String host, int port) {
        Client client = new Client(host, port);

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.handler(new ClientChannelInitializer());

        ChannelFuture future = bootstrap.connect(host, port);
        Channel c = future.channel();
        client.setChannel(c);
        client.setGroup(group);

        return client;
    }

    public void close() {
        this.group.shutdownGracefully();
    }

    public void send(BaseRequest.Request request) {
        channel.writeAndFlush(request);
    }
}
