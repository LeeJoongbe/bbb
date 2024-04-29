package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "Reply", indexes = {
        @Index(name = "idx_reply_board_bno", columnList = "board_bno")
})
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;


    @ManyToOne(fetch =  FetchType.LAZY)
    private  Board board;



    private String replyText;


    private String replyer;



}
