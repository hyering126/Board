package com.example.demo.controller;

import com.example.demo.domain.Board;
import com.example.demo.model.Criteria;
import com.example.demo.model.PageMakerDTO;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 목록 화면
    @GetMapping({"", "/list"})
    public String list(Criteria cri, Model model) {
        int total = boardService.getTotalCount();

        model.addAttribute("boardList", boardService.findBoardListWithCri(cri));
        model.addAttribute("pageMaker", new PageMakerDTO(cri, total)); // pageMaker 전달

        return "board/list";
    }

    // 등록 화면
    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("board", new Board());
        return "board/form";
    }

    // 상세 화면
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("board", boardService.findBoardById(id));
        return "board/detail";
    }

    // 수정 화면
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("board", boardService.findBoardById(id));
        return "board/form";
    }

    // 게시글 등록
    @ResponseBody
    @PostMapping("/api")
    public ResponseEntity<?> createBoard(@RequestBody Board board) {
        boardService.save(board);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // 비밀번호 확인
    @ResponseBody
    @PostMapping("/api/{id}/verify-password")
    public ResponseEntity<?> verifyPassword(@PathVariable("id") Long id, @RequestBody Map<String, String> request) {
        boolean isValid = boardService.checkPassword(id, request.get("password"));
        if(isValid) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // 게시글 수정
    @ResponseBody
    @PutMapping("/api/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable("id") Long id, @RequestBody Board board) {
        boardService.update(id, board);
        return ResponseEntity.ok().build();
    }

    // 게시글 삭제
    @ResponseBody
    @DeleteMapping("/api/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}