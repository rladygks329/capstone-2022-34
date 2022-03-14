package com.capstone.momomeal.domain;

import lombok.Cleanup;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class JoinedChatRoom {

    @Id @GeneratedValue
    @Column(name = "joined_chatroom_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @Enumerated(value = EnumType.STRING)
    private MemberStatus status;

    public JoinedChatRoom(ChatRoom chatRoom, MemberStatus status) {
        this.chatRoom = chatRoom;
        this.status = status;
    }

    // ======연관관계 편의 메서드=====

    /**
     * JoinedChatRoom의 member 세팅 && member의 JoinChatRoom List에 채팅방 추가
     * @param member 이 채팅방에 참여한 멤버
     */
    public void setMember(Member member){
        this.member = member;
        member.getJoinedChatRooms().add(this);
    }

//    public void setChatRoom(ChatRoom chatRoom){
//        this.chatRoom = chatRoom;
////        chatRoom.getJoinMeetings().add(this);
//
//    }

}
