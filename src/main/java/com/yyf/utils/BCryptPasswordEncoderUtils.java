package com.yyf.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

/**
加密结果类
这个类只是能生成密码对应的加密串
*/
public class BCryptPasswordEncoderUtils {
    static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public static String encodePassword(String password){
        return  bCryptPasswordEncoder.encode(password);
    }
 
    public static void main(String[] args) {
//输入：5201314
        Scanner in = new Scanner(System.in);
        String s = in.next();
        String password = encodePassword(s);
        System.out.println(password);
//输出：$2a$10$/6kKRthQCnEgNZ3gm5MiwOnGCeJENokZZ4r1zOKjRXmceZe2xzeI2
    }
}