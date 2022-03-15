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
     * @param member    해당 채팅방 생성자
     * @param requestDTO   채팅방의 정보(타이틀, 최대인원수...)
     * @return 생성한 채팅방(ChatRoom) id값
     */
    @Transactional
    public Long createChatRoom(Member member, ChatRoomRequestDTO requestDTO){
        // string -> Category enum 타입 변환
        Category category = transferStringToEnum(requestDTO.getCategoryName());

        // 채팅방 생성
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

    /**
     *
     * @param categoryName 안드로이드에서 받은 카테고리 string값
     * @return enum으로 변환한 값
     */
    public Category transferStringToEnum(String categoryName){
        Category category;
        switch (categoryName){
            case "Chicken":
                category = Category.CHICKEN;
                break;
            case "Pizza":
                category = Category.PIZZA;
                break;
            case "Korean":
                category = Category.KOREAN;
                break;
            case "Chinese":
                category = Category.CHINESE;
                break;
            case "Japanese":
                category = Category.JAPANESE;
                break;
            case "Western":
                category = Category.WESTERN;
                break;
            case "Snackbar":
                category = Category.SNACKBAR;
                break;
            case "MidnightSnack":
                category = Category.MIDNIGHTSNACK;
                break;
            case "BoiledPork":
                category = Category.BOILEDPORK;
                break;
            case "CafeAndDesert":
                category = Category.CAFE;
                break;
            case "Fastfood":
                category = Category.FASTFOOD;
                break;
            default:
                category = Category.ETC;
                break;
        }
        return category;
    }
}
