package com.achobeta.infrastructure.adapter.repository;

import com.achobeta.domain.render.adapter.repository.IBookRepository;
import com.achobeta.domain.render.model.entity.BookEntity;
import org.springframework.stereotype.Repository;

/**
 * @author chensongmin
 * @description
 * @date 2024/11/4
 */
@Repository
public class BookRepository implements IBookRepository {

    @Override
    public BookEntity queryBook(String bookId) {
        return BookEntity.builder()
                .bookId(bookId)
                .bookName("AchoBeta Polaris 项目介绍")
                .bookContent("这是 AchoBeta Polaris 北极星系统, AchoBeta 6.0 Java 组复试项目")
                .build();
    }

}
