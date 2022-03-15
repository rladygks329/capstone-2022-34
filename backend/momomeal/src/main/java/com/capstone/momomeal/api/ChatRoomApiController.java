package com.capstone.momomeal.api;

import com.capstone.momomeal.domain.*;
import com.capstone.momomeal.service.ChatRoomService;
import com.capstone.momomeal.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChatRoomApiController {
    private final ChatRoomService chatRoomService;
    private final MemberService memberService;

    /**
     * 채팅방 생성 요청 api
     * @param requestDTO 안드로이드에서 받은 채팅방 데이터
     * @return 생성한 채팅방(ChatRoom) id값
     */
    @PostMapping("/chat")
    public CreateChatRoomResponse saveChatRoom(@RequestBody @Valid ChatRoomRequestDTO requestDTO) {

        // 현재 회원 데이터 가져오기
        Member member = memberService.findOne(requestDTO.getHostId());

        // 채팅방 생성
        Long createChatRoomId = chatRoomService.createChatRoom(member, requestDTO);

        return new CreateChatRoomResponse(createChatRoomId);
    }

    /**
     * 채팅방 생성 요청 처리 후 응답
     */
    @Data
    static class CreateChatRoomResponse {
        private Long id;

        public CreateChatRoomResponse(Long id) {
            this.id = id;
        }
    }

    /**
     * 해당 카테고리별 채팅방 데이터(dto) 전송
     * @param categoryName 사용자가 선택한 카테고리명
     * @return
     */
    @GetMapping("/chat/{categoryName}")
    public List<ChatRoomListDto> returnCategoryList(@PathVariable String categoryName){
        // string -> Category enum 타입 변환
        TransStringToEnum te = new TransStringToEnum();
        Category selectedCategory = te.transferStringToEnum(categoryName);

        // 모든 채팅방 가져옴
        List<ChatRoom> chatRooms = chatRoomService.findAll();

        // 해당 카테고리의 dto만 뽑음
        List<ChatRoomListDto> result = chatRooms.stream().filter(c -> c.getCategory().equals(selectedCategory))
                .map(c -> new ChatRoomListDto(c))
                .collect(Collectors.toList());

        return result;

    }

    @GetMapping("/chat")
    public List<ChatRoomListDto> returnAllList() {

        // 모든 채팅방 가져옴
        List<ChatRoom> chatRooms = chatRoomService.findAll();

        // 모든 채팅방의 dto만 뽑음
        List<ChatRoomListDto> result = chatRooms.stream().map(c -> new ChatRoomListDto(c))
                .collect(Collectors.toList());

        return result;

    }

    @Data
    static class ChatRoomListDto{
        private Long id;
        private String title;
        private String pickupPlaceName;
        private LocalDateTime createdDate;
        private double pickupPlaceXCoord;
        private double pickupPlaceYCoord;

        public ChatRoomListDto(ChatRoom chatRoom) {
            this.id = chatRoom.getId();
            this.title = chatRoom.getTitle();
            this.pickupPlaceName = chatRoom.getPickupPlaceName();
            this.createdDate = chatRoom.getCreatedDate();
            this.pickupPlaceXCoord = chatRoom.getPickupPlaceXCoord();
            this.pickupPlaceYCoord = chatRoom.getPickupPlaceYCoord();
        }
    }


}
