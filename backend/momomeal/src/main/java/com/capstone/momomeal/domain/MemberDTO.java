package com.capstone.momomeal.domain;

import lombok.Data;

@Data
public class MemberDTO {
    Long userId;
    String email;
    String pwd;
    String name;
    String img_url;

    public MemberDTO(Long userId, String email, String pwd, String name, String img_url) {
        this.userId = userId;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.img_url = img_url;
    }
}
