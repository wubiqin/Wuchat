package com.wbq.common.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author: biqin.wu
 * @Date: 2019/1/13
 * @Time: 15:04
 * @Description:
 */
@Data
@Builder
public class User {

    private String userName;

    private String password;
}
