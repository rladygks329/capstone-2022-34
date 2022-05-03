package com.capstone.momomeal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ChatRoomListDto {
    private Long id;
    private String title;
    private String pickupPlaceName;
    private LocalDateTime createdDate;
    private int distance;
    private String category;

    public ChatRoomListDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.title = chatRoom.getTitle();
        this.pickupPlaceName = chatRoom.getPickupPlaceName();
        this.createdDate = chatRoom.getCreatedDate();
        this.distance = chatRoom.getDistance();
        this.category = chatRoom.getCategory().getName();
    }
}
