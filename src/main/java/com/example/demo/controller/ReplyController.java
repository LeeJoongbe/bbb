package com.example.demo.controller;


import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.entity.Reply;
import com.example.demo.service.BoardService;
import com.example.demo.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyController {


    private final ReplyService replyService;





    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@Valid @RequestBody  ReplyDTO replyDTO,
                                                          BindingResult bindingResult )throws  BindException {


        log.info(replyDTO);

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>();

        Long rno = replyService.register(replyDTO);
        resultMap.put("rno", rno);


        return resultMap;
    }


    @GetMapping(value = "/list/{bno}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public  PageResponseDTO<ReplyDTO> getList(@PathVariable("bno") Long bno,
                                              PageRequestDTO pageRequestDTO) {
        log.info("댓글번호:"+bno);

        log.info("현재페이지 " +pageRequestDTO);

        return replyService.all(bno, pageRequestDTO);
    }




    @GetMapping(value = "/lista/{bno}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponseDTO<ReplyDTO>> getLista(@PathVariable("bno") Long bno,
                                   PageRequestDTO pageRequestDTO) {
        log.info("댓글번호:"+bno);

        log.info("현재페이지 " +pageRequestDTO);


        return new ResponseEntity<>(replyService.all(bno, pageRequestDTO), HttpStatus.OK);
    }


    @GetMapping(value ="/{rno}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReplyDTO getReplyDTO(@PathVariable("rno") Long rno){

        ReplyDTO replyDTO = replyService.read(rno);
        log.info("컨트롤러 리드"+ replyDTO);

        return replyDTO;

    }

    @PutMapping(value ="/{rno}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modify(@PathVariable("rno") Long rno,
                                    @RequestBody ReplyDTO replyDTO){

        System.out.println(rno + "  " + replyDTO);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno", rno);
        return resultMap;
    }

    @DeleteMapping("/{rno}")
    public Map<String, Long> remove(@PathVariable("rno") Long rno){
        replyService.remove(rno);

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno", rno);
        return resultMap;

    }


}
