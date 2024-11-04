package com.achobeta.domain.render.adapter.repository;

import com.achobeta.domain.render.model.entity.BookEntity;
import org.springframework.stereotype.Repository;

/**
 * @author chensongmin
 * @description 查询书籍仓储接口
 * @date 2024/11/4
 */
public interface IBookRepository {

    BookEntity queryBook(String bookId);

}
