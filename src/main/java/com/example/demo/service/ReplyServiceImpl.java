package com.example.demo.service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Reply;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private  final ModelMapper modelMapper;

    @Override
    public Long register(ReplyDTO replyDTO) {

        Reply reply = modelMapper.map(replyDTO, Reply.class);
        Board board = new Board();
        board.setBno(replyDTO.getBno());
        reply.setBoard(board);
        log.info(replyDTO);
        log.info(reply.getBoard());
        Long rno = replyRepository.save(reply).getRno();
        return rno;
    }

    @Override
    public ReplyDTO read(Long rno) {
        Optional<Reply> r= replyRepository.findById(rno);
        Reply r1 = r.orElseThrow();
        log.info("변환전" +r1);
        ReplyDTO x =modelMapper.map(r1,ReplyDTO.class);
        log.info("변환후"+x);
        return x;
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        System.out.println(replyDTO);

        replyRepository.save(modelMapper.map(replyDTO,Reply.class));
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public PageResponseDTO<ReplyDTO> all(Long bno, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0? 0: pageRequestDTO.getPage()-1, pageRequestDTO.getSize(), Sort.by("rno").ascending());
        Page<Reply> r =  replyRepository.listOfBoard(bno, pageable);

        List<ReplyDTO>  dtoList = r.getContent().stream().map(reply -> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());

        PageResponseDTO<ReplyDTO> responseDTO = new PageResponseDTO<>(pageRequestDTO, dtoList, (int) r.getTotalElements());

        return responseDTO;
    }

}
