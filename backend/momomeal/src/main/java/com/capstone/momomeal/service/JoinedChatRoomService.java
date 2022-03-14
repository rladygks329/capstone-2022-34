package com.capstone.momomeal.service;

import com.capstone.momomeal.domain.JoinedChatRoom;
import com.capstone.momomeal.repository.JoinedChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinedChatRoomService {
    private final JoinedChatRoomRepository joinedChatRoomRepository;

    /**
     * 참여한 채팅방 저장 메서드
     * @param joinedChatRoom 저장할 참여한 채팅방 object
     * @return  저장한 참여 채팅방 id값
     */
    @Transactional
    public Long save(JoinedChatRoom joinedChatRoom){
        joinedChatRoomRepository.save(joinedChatRoom);
        return joinedChatRoom.getId();
    }
}
