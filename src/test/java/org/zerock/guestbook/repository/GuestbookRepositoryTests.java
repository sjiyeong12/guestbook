package org.zerock.guestbook.repository;

import java.util.Optional;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@SpringBootTest
public class GuestbookRepositoryTests {
    @Autowired
    private GuestbookRepository repository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 300).forEach(new IntConsumer() {
            @Override
            public void accept(int i) {
                Guestbook gb = Guestbook.builder()
                        .title("Title..." + i)
                        .content("Content" + i)
                        .writer("user" + (i % 10))
                        .build();
                System.out.println(repository.save(gb));
            }
        });
    }

    @Test
    public void updateTest() {
        Optional<Guestbook> result = repository.findById(300L);
        if (result.isPresent()) {
            Guestbook gb = result.get();
            gb.changeTitle("Changed Title....");
            gb.changeCOntent("Changed Content....");
            repository.save(gb);
        }
    }

    @Test
    public void TestQuery1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook; // 1
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder(); // 2 질의하기 위한 객체
        BooleanExpression expression = qGuestbook.title.contains(keyword);
        builder.and(expression); // 4
        Page<Guestbook> result = repository.findAll(builder, pageable);
        result.stream().forEach(gb -> {
            System.out.println(gb);
        });
    }

    @Test
    public void TestQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook; // 1
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder(); // 2 질의하기 위한 객체

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.title.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll); // 4
        builder.and(qGuestbook.gno.gt(0L)); // 4
        Page<Guestbook> result = repository.findAll(builder, pageable);
        result.stream().forEach(gb -> {
            System.out.println(gb);
        });
    }
}