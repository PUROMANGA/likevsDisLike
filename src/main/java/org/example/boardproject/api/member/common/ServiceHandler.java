package org.example.boardproject.api.member.common;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ServiceHandler {

    public String createCode() {
        SecureRandom random = new SecureRandom();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < 6; i++){
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }
}
