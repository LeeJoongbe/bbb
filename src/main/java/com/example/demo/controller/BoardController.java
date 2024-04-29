package com.example.demo.controller;


import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.entity.Board;
import com.example.demo.service.BoardService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(Model model, PageRequestDTO pageRequestDTO){

        PageResponseDTO<BoardDTO> responseDTO = boardService.listWithReplyCount(pageRequestDTO);
        //responseDTO.getDtoList();

        model.addAttribute("responseDTO", responseDTO);

    }

    @GetMapping("/register")
    public void registerGET(){

    }


    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        log.info("board post register...");
        log.info(boardDTO);
        if(bindingResult.hasErrors()){
            log.info("has errorssssssssssssssss");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/register";
        }

        log.info(boardDTO);

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";

    }

    @GetMapping({"/read", "/modify"})
    public void read (Long bno, PageRequestDTO pageRequestDTO, Model model){

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);


    }

    @PostMapping("/modify")
    public String modifyPost(PageRequestDTO pageRequestDTO,
            @Valid BoardDTO boardDTO, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){


        log.info(boardDTO);
        if(bindingResult.hasErrors()){
            log.info("has errorssssssssssssssss");

            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno", boardDTO.getBno());
            return "redirect:/board/modify?"+link;
        }

        log.info(boardDTO);

        boardService.modify(boardDTO);

        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO.getBno());


        return "redirect:/board/read";



    }

    @PostMapping("/remove")
    public String  remove(Long bno, RedirectAttributes redirectAttributes){

        log.info("삭제할 번호" +bno);
        boardService.remove(bno);

        redirectAttributes.addFlashAttribute("result","removed");
        return "redirect:/board/list";

    }








}
