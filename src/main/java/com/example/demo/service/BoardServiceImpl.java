package com.example.demo.service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{


    private  final ModelMapper modelMapper;
    private final BoardRepository boardRepository;
    @Override
    public Long register(BoardDTO boardDTO) {
        Board board =   modelMapper.map(boardDTO, Board.class);

        Long bno =  boardRepository.save(board).getBno();


        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {

        Optional<Board> board = boardRepository.findById(bno);
        Board board1 = board.orElseThrow();
        BoardDTO boardDTO = modelMapper.map(board1, BoardDTO.class);


        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {

        Board board = modelMapper.map(boardDTO, Board.class);

        boardRepository.save(board);

    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }


    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Board> result = boardRepository.searchAll(types,keyword,pageable);

        List<BoardDTO> dtoList = result.getContent().stream().map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());

        //PageRequestDTO pageRequestDTO, List<E> dtoList, int total
        PageResponseDTO<BoardDTO> x=
         new PageResponseDTO<BoardDTO>(pageRequestDTO, dtoList, (int) result.getTotalElements());

        return x;
    }

    @Override
    public PageResponseDTO<BoardDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<BoardDTO> result = boardRepository.searchAllWithReplyCount(types,keyword,pageable);



        //PageRequestDTO pageRequestDTO, List<E> dtoList, int total
        PageResponseDTO<BoardDTO> x=
                new PageResponseDTO<BoardDTO>(pageRequestDTO, result.getContent(), (int) result.getTotalElements());

        return x;
    }
}
