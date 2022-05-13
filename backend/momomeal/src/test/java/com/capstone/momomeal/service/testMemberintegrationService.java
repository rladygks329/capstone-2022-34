
package com.capstone.momomeal.service;

import com.capstone.momomeal.domain.Members;
import com.capstone.momomeal.repository.MemberRepository;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class testMemberintegrationService {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    public testMemberintegrationService() {
    }
//
    @Test
    @Rollback(value = false)
    public void ck_updateUserInfo(){
        String email = "kimchang123@gmail.com";
        String RealName = "nothing";
        String img_url = "123123";

        Long a = Long.valueOf(1);
        Optional<Members> members = memberService.findById(a);
        Members member = members.get();
        memberService.updateUser(member.getUser_id(), member.getEmail(), member.getRealName(), img_url);
    }
//
}
