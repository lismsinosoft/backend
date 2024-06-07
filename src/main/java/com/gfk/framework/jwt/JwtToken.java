package com.gfk.framework.jwt;
 
import org.apache.shiro.authc.AuthenticationToken;

/**
 * token对象
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
public class JwtToken implements AuthenticationToken {
	
	private String token;
 
    public JwtToken(String token) {
        this.token = token;
    }
 
    @Override
    public Object getPrincipal() {
        return token;
    }
 
    @Override
    public Object getCredentials() {
        return token;
    }
}
