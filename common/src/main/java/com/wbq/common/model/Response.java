package com.wbq.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: biqin.wu
 * @Date: 2019/1/13
 * @Time: 15:08
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private int code;

    private String msg;

    private T data;
}
