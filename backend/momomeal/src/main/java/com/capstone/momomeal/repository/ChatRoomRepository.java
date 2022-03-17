package com.capstone.momomeal.repository;

import com.capstone.momomeal.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {

    private final EntityManager em;

    public void save(ChatRoom chatRoom){
        em.persist(chatRoom);
    }

    public ChatRoom findById(Long chatRoomId){
        return em.find(ChatRoom.class, chatRoomId);
    }

    public List<ChatRoom> findAll(){
        return em.createQuery("select cr from ChatRoom cr", ChatRoom.class)
                .getResultList();
    }
}
