package com.example.demo.service;

import com.example.demo.domain.Board;
import com.example.demo.model.Criteria;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<Board> findBoardList(Pageable pageable) {
        int page = pageable.getPageNumber() < 1 ? 0 : pageable.getPageNumber() - 1;
        return boardRepository.findAll(PageRequest.of(page, 10));
    }

    public Page<Board> findBoardListWithCri(Criteria cri) {
        int page = cri.getPageNum() < 1 ? 0 : cri.getPageNum() - 1;
        return boardRepository.findAll(PageRequest.of(page, cri.getAmount(), Sort.by("createdDate").descending()));
    }

    public int getTotalCount() {
        return (int) boardRepository.count();
    }

    public Board findBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
    }

    @Transactional
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    @Transactional
    public void update(Long id, Board requestBoard) {
        Board board = findBoardById(id);
        board.update(requestBoard.getTitle(), requestBoard.getContent(), requestBoard.getWriter());
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    public boolean checkPassword(Long id, String password) {
        Board board = findBoardById(id);
        return board.getPassword().equals(password);
    }
}