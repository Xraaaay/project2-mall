package com.cskaoyan.config.shiro;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author changyong
 * @since 2022/07/18 11:31
 */
@Data
public class MarketToken extends UsernamePasswordToken {
    String type;

    public MarketToken(String username, String password, String type) {
        super(username, password);
        this.type = type;
    }
}
