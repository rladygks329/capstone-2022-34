package com.capstone.momomeal.service;

import com.capstone.momomeal.domain.ChatRoom;
import com.capstone.momomeal.domain.JoinedChatRoom;
import com.capstone.momomeal.domain.Member;
import com.capstone.momomeal.domain.MemberStatus;
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


    /**
     * 참여한 채팅방 생성 메서드 - 호스트가 아닌 사용자가 해당 채팅방에 참여하는 메서드이다.
     * @param member 참여 요청을 한 member
     * @param chatRoom member가 참여하려는 chatRoom
     * @return 생성한 joinedChatRoom id
     */
    @Transactional
    public Long createJoinedChatRoom(Member member, ChatRoom chatRoom){
        JoinedChatRoom joinedChatRoom = new JoinedChatRoom(chatRoom, MemberStatus.MEMBER);
        joinedChatRoom.setMember(member);
        save(joinedChatRoom);
        return joinedChatRoom.getId();

    }


}
