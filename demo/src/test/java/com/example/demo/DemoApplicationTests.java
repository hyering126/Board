package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Board;
import com.example.demo.domain.enums.BoardType;
import com.example.demo.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DataJpaTest
class DemoApplicationTests {

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected BoardRepository boardRepository;

	private final String title = "제목";
	private final String email = "hyering126@naver.com";

	@BeforeEach
	public void init() {
		User user = userRepository.save(User.builder()
				.name("hyeri")
				.password("0000")
				.email(email)
				.build());
		boardRepository.save(Board.builder()
				.title(title)
				.subTitle("부제목")
				.content("내용")
				.boardType(BoardType.Board)
				.user(user)
				.build());
	}

	@Test
	void contextLoads() {
		User user = userRepository.findByEmail(email);
		assertThat(user.getName(), is("hyeri"));
		assertThat(user.getPassword(), is("0000"));
		assertThat(user.getEmail(), is(email));

		Board board = boardRepository.findByUser(user);
		assertThat(board.getTitle(), is(title));
		assertThat(board.getSubTitle(), is("부제목"));
		assertThat(board.getContent(), is("내용"));
		assertThat(board.getBoardType(), is(BoardType.Board));
	}

}
