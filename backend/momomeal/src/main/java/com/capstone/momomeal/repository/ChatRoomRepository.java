package com.capstone.momomeal.repository;

import com.capstone.momomeal.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {

    private final EntityManager em;

    public void save(ChatRoom chatRoom){
        em.persist(chatRoom);
    }

    public List<ChatRoom> findAll(){
        return em.createQuery("select cr from ChatRoom cr", ChatRoom.class)
                .getResultList();
    }

    public List<ChatRoom> findExceptParticipatedChatRoom(List<Long> participatedChatRoomIds){
        return em.createQuery("select cr from ChatRoom cr" +
                " where cr.id not in :ids")
                .setParameter("ids", participatedChatRoomIds)
                .getResultList();

    }
}
