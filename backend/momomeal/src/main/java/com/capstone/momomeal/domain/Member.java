package com.capstone.momomeal.domain;


import lombok.Getter;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<JoinedChatRoom> joinedChatRooms = new ArrayList<>();

    // Member에서 joinChatRoom 삭제
    public void deleteJoinChatRoomFromMember(JoinedChatRoom joinedChatRoom){
        joinedChatRooms.remove(joinedChatRoom);

    }

    public boolean belongsToMember(JoinedChatRoom joinedChatRoom){
        return joinedChatRooms.contains(joinedChatRoom);
    }


}
