package com.capstone.momomeal.api;

import com.capstone.momomeal.domain.ChatRoom;
import com.capstone.momomeal.service.ChatRoomService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchApiController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/searched-chat-list/{keyword}")
    public ResponseEntity getSearchChatRoomList(@PathVariable String keyword){

        List<ChatRoom> result = chatRoomService.getSearchedChatRooms(keyword);

        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

}
