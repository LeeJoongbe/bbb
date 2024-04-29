package com.example.demo.service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;

public interface BoardService {

    public Long register(BoardDTO boardDTO);

    public BoardDTO readOne(Long bno);

    public void modify (BoardDTO boardDTO);
    public void remove (Long bno);

    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    public PageResponseDTO<BoardDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);
}
