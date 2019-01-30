package com.wbq.common.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 *  * @author biqin.wu
 *  * @since 30 January 2019
 *  
 */
@Slf4j
public class Server implements Runnable {

    private final int port;

    private static volatile boolean start = false;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        if (start) {
            log.info("netty server started");
            return;
        }
        start();
    }

    private void start() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ServerChannelInitializer());

        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("netty server start..... port={}", port);
            start = true;
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("netty server fail to start {}", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
