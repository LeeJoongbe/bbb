package com.example.demo.repositoryTest;


import com.example.demo.dto.ReplyDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Reply;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.ReplyRepository;
import com.example.demo.service.ReplyService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class BoardRepositoryTest {

    @Autowired
    private  BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyService replyService;
    
    @Test
    public void insertTest(){




        for(int i=0; i <100 ; i++) {

        Board board = new Board();
        board.setTitle("타이틀");
        board.setContent("컨텐츠");
        board.setWriter("라이터");

        board.setBno(205L);

            Reply reply = new Reply();
            reply.setBoard(board);
            reply.setReplyText(" 안녕하세요"+i);
            reply.setReplyer("홍길동");
        replyRepository.save(reply);

        }
    }

    @Test
    public void readTest() {

        Optional<Board> board = boardRepository.findById(1L);

        Board board1 = board.orElseThrow();

        log.info(board1);
    }

    @Test
    public void updateTest(){

        Board board = new Board();
        board.setBno(1L);
        board.setTitle("1타이틀");
        board.setContent("1컨텐츠");
        board.setWriter("1라이터");

        boardRepository.save(board);

    }

    @Test
    public void allTest(){

        List<Board> boardList =  boardRepository.findAll();

        boardList.forEach(a -> log.info(a));

    }


    @Test
    public  void testPagging(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        List<Board> boardList = result.getContent();

        boardList.forEach(board -> log.info(board));

        log.info("총갯수 " + result.getTotalElements());
        log.info("현재 페이지 " + (result.getNumber() + 1));
        log.info("총 페이지 수 " + result.getTotalPages());
        log.info("한페이지 사이즈 " + result.getSize());

    }

    @Test
    public void testsearchTestPagging(){

        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").ascending());

        String keyword = "3";
        String[] types = "twc".split("");
        List<Board> boardList = new ArrayList<>();

        if(types.toString().equals("tw")){

            boardList = boardRepository.findByTitleContainingOrContentContaining(keyword, keyword);

        }else if (types.toString().equals("t")){
            boardList = boardRepository.findByTitleContainingOrContentContaining(keyword, "");
        }
        boardList.forEach(board -> log.info(board));


    }

    @Test
    public void testSearch1(){

        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").ascending());

        boardRepository.search1(pageable);

    }

    @Test
    public void testSearchAll(){
        String[] types = {"t", "w"};
        String keyword = "2";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").ascending());
        Page<Board> result = boardRepository.searchAll(types,keyword,pageable);

        List<Board> boardList =  result.getContent();
        log.info("다음장은?" + result.hasNext());
        log.info("이전장은?" + result.hasPrevious());
        boardList.forEach(board -> log.info(board));
    }

    @Test
    public void servicereplyTestinsrt(){

        ReplyDTO replyDTO = ReplyDTO.builder()
                .bno(200L).replyText("하이").replyer("홍길")
                .build();
        log.info(replyService.register(replyDTO));
    }

    @Test
    public void servicereplyTestmod(){

        ReplyDTO replyDTO = ReplyDTO.builder().rno(270L)
                .bno(200L).replyText("1111").replyer("홍길")
                .build();
        replyService.modify(replyDTO);
    }


































}
