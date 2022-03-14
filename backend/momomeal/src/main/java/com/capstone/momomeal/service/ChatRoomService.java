package com.capstone.momomeal.service;

import com.capstone.momomeal.domain.*;
import com.capstone.momomeal.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final JoinedChatRoomService joinedChatRoomService;

    /**
     * 채팅방 저장 메서드
     * @param chatRoom 저장할 채팅방 object
     * @return 저장한 채팅방의 id값
     */
    @Transactional
    public Long save(ChatRoom chatRoom){
        chatRoomRepository.save(chatRoom);
        return chatRoom.getId();
    }

    /**
     * 채팅방 생성 메서드
     * @param category  해당 채팅방의 카테고리 object
     * @param member    해당 채팅방 생성자
     * @param requestDTO   채팅방의 정보(타이틀, 최대인원수...)
     * @return 생성한 채팅방(ChatRoom) id값
     */
    @Transactional
    public Long createChatRoom(Category category, Member member, ChatRoomRequestDTO requestDTO){
        ChatRoom chatRoom = new ChatRoom(category, requestDTO.getTitle(), requestDTO.getHostId(),
                requestDTO.getMaxCapacity(), requestDTO.getStoreName(), requestDTO.getPickupPlaceName(),
                requestDTO.getPickupPlaceXCoord(), requestDTO.getPickupPlaceYCoord());
        save(chatRoom);

        // 참여 채팅방 생성하고 현재 회원과 연관관계 설정
        JoinedChatRoom joinedChatRoom = new JoinedChatRoom(chatRoom, MemberStatus.HOST);
        joinedChatRoom.setMember(member);
        joinedChatRoomService.save(joinedChatRoom);
        return chatRoom.getId();

    }
}
