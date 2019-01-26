package com.wbq.common.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author: biqin.wu
 * @Date: 2019/1/27
 * @Time: 0:54
 * @Description:
 */
@Component
@Slf4j
public class HttpUtils {

    @Resource
    private OkHttpClient httpClient;

    public void sendMsg(String url, String msg) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), msg);
        Request request = new Request.Builder().url(url).post(body).build();

        Response response;
        try {
            response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                log.info("msg has been received");
            }
        } catch (IOException e) {
            log.error("fail when send msg with http");
            throw new RuntimeException("fail to send msg");
        }
    }
}
