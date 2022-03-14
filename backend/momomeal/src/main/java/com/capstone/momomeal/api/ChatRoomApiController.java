package com.capstone.momomeal.api;

import com.capstone.momomeal.domain.Category;
import com.capstone.momomeal.domain.ChatRoom;
import com.capstone.momomeal.domain.ChatRoomRequestDTO;
import com.capstone.momomeal.domain.Member;
import com.capstone.momomeal.repository.CategoryRepository;
import com.capstone.momomeal.service.CategoryService;
import com.capstone.momomeal.service.ChatRoomService;
import com.capstone.momomeal.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ChatRoomApiController {
    private final ChatRoomService chatRoomService;
    private final MemberService memberService;
    private final CategoryService categoryService;


    /**
     * 채팅방 생성 요청 api
     * @param requestDTO 안드로이드에서 받은 채팅방 데이터
     * @return 생성한 채팅방(ChatRoom) id값
     */
    @PostMapping("/chat")
    public CreateChatRoomResponse saveChatRoom(@RequestBody @Valid ChatRoomRequestDTO requestDTO) {
        // 카테고리 생성
        Category category = new Category();
        category.setCategoryName(requestDTO.getCategoryName());
        categoryService.save(category);


        // 현재 회원 데이터 가져오기
        Member member = memberService.findOne(requestDTO.getHostId());

        // 채팅방 생성
        Long createChatRoomId = chatRoomService.createChatRoom(category, member, requestDTO);

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


}
