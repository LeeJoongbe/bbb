package com.example.demo.dto;

import com.example.demo.entity.Board;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReplyDTO {



    private Long rno;

    private Long bno;

    private String replyText;

    private String replyer;

    private LocalDateTime regDate;

    private LocalDateTime modDate;


}
