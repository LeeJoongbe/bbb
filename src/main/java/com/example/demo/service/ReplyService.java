package com.example.demo.service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.entity.Reply;

import java.util.List;

public interface ReplyService {


    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    public void modify(ReplyDTO replyDTO);

    public void remove(Long rno);

    PageResponseDTO<ReplyDTO> all(Long bno, PageRequestDTO pageRequestDTO);


}
