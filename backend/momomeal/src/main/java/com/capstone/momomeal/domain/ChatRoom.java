package com.capstone.momomeal.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class ChatRoom extends BaseTimeEntity{
    @Id @GeneratedValue
    @Column(name = "chatroom_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String title;
    private Long hostId;
    private int maxCapacity;
    private String storeName;
    private String pickupPlaceName;
    private double pickupPlaceCoordX;
    private double pickupPlaceCoordY;

    public ChatRoom() {
    }


    public ChatRoom(Category category, String title, Long hostId, int maxCapacity,
                    String storeName, String pickupPlaceName, double pickupPlaceCoordX,
                    double pickupPlaceCoordY) {
        this.category = category;
        this.title = title;
        this.hostId = hostId;
        this.maxCapacity = maxCapacity;
        this.storeName = storeName;
        this.pickupPlaceName = pickupPlaceName;
        this.pickupPlaceCoordX = pickupPlaceCoordX;
        this.pickupPlaceCoordY = pickupPlaceCoordY;
    }

}
