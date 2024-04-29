package com.example.demo.repository;

import com.example.demo.entity.Board;
import com.example.demo.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {


    //검색
    List<Board> findByTitleContainingOrderByBnoAsc(String keyword);

    Page<Board> findByTitleContaining (String title, Pageable pageable);
    Page<Board> findByContentContaining (String title, Pageable pageable);
    List<Board> findByTitleContainingOrContentContaining (String title, String content);




}
