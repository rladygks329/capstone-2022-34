package com.capstone.momomeal.api;

import com.capstone.momomeal.domain.*;
import com.capstone.momomeal.service.ChatRoomService;
import com.capstone.momomeal.service.JoinedChatRoomService;
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
    private final JoinedChatRoomService joinedChatRoomService;

    /**
     * 채팅방 생성 요청 api
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
     * 호스트가 아닌 사용자의 채팅방 요청 api
     * 해당 채팅방 멤버에 해당 사용자 추가함
     */
    @GetMapping("/chat/{memberId}/{chatroomId}")
    public CreateJoinedChatRoomResponse enterChatRoom(@PathVariable Long memberId,
                                                      @PathVariable Long chatroomId){

        // id값으로 회원 객체 가져오기
        Member findMember = memberService.findOne(memberId);
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


}
