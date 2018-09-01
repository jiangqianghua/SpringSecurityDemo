package com.jqh.SpringSecurityDemo;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义加密算法和匹配
 */
public class MyPassordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder.encodePassword(charSequence.toString(),"123456");
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder.isPasswordValid(s,charSequence.toString(),"123456");
    }
}
