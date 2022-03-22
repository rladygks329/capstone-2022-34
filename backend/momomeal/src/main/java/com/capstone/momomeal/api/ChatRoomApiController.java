package com.capstone.momomeal.api;

import com.capstone.momomeal.domain.*;
import com.capstone.momomeal.service.ChatRoomService;
import com.capstone.momomeal.service.JoinedChatRoomService;
import com.capstone.momomeal.service.MemberService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final JoinedChatRoomService joinedChatRoomService;

    /**
     * 채팅방 생성 응답 api
     * @param requestDTO 안드로이드에서 받은 채팅방 데이터
     * @return 생성한 채팅방(ChatRoom) id값
     */
    @PostMapping("/chat")
    public CreateChatRoomResponse saveChatRoom(@RequestBody @Valid ChatRoomRequestDTO requestDTO) {

        // 현재 회원 데이터 가져오기
        Member member = memberService.findOne(requestDTO.getHostId());
        System.out.println("member: " + member.getId());

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
     * 사용자가 클릭한 채팅방 데이터(dto) 전송 api
     * @param chatroomId 클릭한 채팅방 id
     * @return 클릭한 채팅방 데이터(dto)
     */
    @GetMapping("/clicked-chat/{chatroomId}")
    public ResponseEntity returnClickedChatRoomData(@PathVariable Long chatroomId){
        // chatRoomId를 통해 해당 채팅방 데이터 조회
        ChatRoom clickedChatRoom = chatRoomService.findById(chatroomId);

        ClickedChatRoomDto result;


        if (clickedChatRoom == null){   // 없는 채팅방 요청 -> 빈 값
            result = new ClickedChatRoomDto();
        } else{
            result = new ClickedChatRoomDto(clickedChatRoom);   // 해당 chatRoom dto로 변환
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(result);

    }

    @Data
    @NoArgsConstructor
    static class ClickedChatRoomDto{
        private Long chatRoomId;
        private String title;
        private String category;
        private int maxCapacity;
        private String storeName;
        private String pickupPlaceName;
        private double pickupPlaceXCoord;
        private double pickupPlaceYCoord;

        public ClickedChatRoomDto(ChatRoom chatRoom) {
            this.chatRoomId = chatRoom.getId();
            this.title = chatRoom.getTitle();
            this.category = chatRoom.getCategory().getName();
            this.maxCapacity = chatRoom.getMaxCapacity();
            this.storeName = chatRoom.getStoreName();
            this.pickupPlaceName = chatRoom.getPickupPlaceName();
            this.pickupPlaceXCoord = chatRoom.getPickupPlaceXCoord();
            this.pickupPlaceYCoord = chatRoom.getPickupPlaceYCoord();
        }
    }

    /**
     * 호스트가 아닌 사용자의 채팅방 참여 응답 api
     * 해당 채팅방 멤버에 해당 사용자 추가함
     */
    @GetMapping("/chat/{testMemberId}/{chatroomId}")
    public CreateJoinedChatRoomResponse enterChatRoom(@PathVariable Long testMemberId,
                                                      @PathVariable Long chatroomId){


        // id값으로 회원 객체 가져오기
        Member findMember = memberService.findOne(testMemberId);
        // id값으로 채팅방 객체 가져오기
        ChatRoom findChatRoom = chatRoomService.findById(chatroomId);

        // joinedChatRoom 생성
        Long joinedChatRoomId = joinedChatRoomService.createJoinedChatRoom(findMember, findChatRoom);

        return new CreateJoinedChatRoomResponse(joinedChatRoomId);

    }

    /**
     * 채팅방 참여 요청 처리 후 응답
     */
    @Data
    @AllArgsConstructor
    static class CreateJoinedChatRoomResponse {
        private Long id;
    }

    /**
     * 채팅방 삭제 응답 api - 참여한 채팅방(JoinedChatRoom) 삭제 - 연관관계 모두 삭제해야 함
     * @param chatroomId 삭제하려는 joinedChatRoom와 연관된 chatRoom id
     * @return deleteCountDto: 삭제한 joinedChatRoom 레코드 수, 삭제한 chatRoom 레코드 수
     */
    @DeleteMapping("/deleted-chat/{chatroomId}/{testMemberId}")
    public deleteCountDto deleteJoinedChatRoom(@PathVariable Long chatroomId,
                                               @PathVariable Long testMemberId){

        Member member = memberService.findOne(testMemberId);
        ChatRoom chatRoom = chatRoomService.findById(chatroomId);
        JoinedChatRoom toDeleteJoinedChatRoom = joinedChatRoomService
                .findByMemberIdAndChatRoomId(member, chatRoom);

        member.deleteJoinChatRoomFromMember(toDeleteJoinedChatRoom);

        int cntDeletedJCRecord = joinedChatRoomService.delete(toDeleteJoinedChatRoom.getId());

        // joinedChatRoom과 연관된 chatRoom없으면 chatRoom도 DB에서 삭제함
        int cntDeleteCRecord = 0;
        int cnt = joinedChatRoomService.countByChatRoom(chatRoom);
        if (cnt == 0) {
            cntDeleteCRecord = chatRoomService.delete(chatroomId);
        }

        return new deleteCountDto(cntDeletedJCRecord, cntDeleteCRecord);

    }

    @Data
    @AllArgsConstructor
    static class deleteCountDto{
        private int cntDeletedJoinedChatRoomRecord;
        private int cntDeletedChatRoomRecord;

    }

}
