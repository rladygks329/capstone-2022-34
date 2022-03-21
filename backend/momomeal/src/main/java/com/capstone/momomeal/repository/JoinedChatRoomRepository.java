package com.capstone.momomeal.repository;

import com.capstone.momomeal.domain.JoinedChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class JoinedChatRoomRepository {
    private final EntityManager em;

    public void save(JoinedChatRoom joinedChatRoom){
        em.persist(joinedChatRoom);
    }

    public void findJoinedChatRoomByMemberIdAndChatRoomId(Long memberId, Long chatRoomId){

    }
}
