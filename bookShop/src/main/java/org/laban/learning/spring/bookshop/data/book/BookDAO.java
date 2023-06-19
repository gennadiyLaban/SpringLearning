package org.laban.learning.spring.bookshop.data.book;

import java.util.List;

public interface BookDAO {
    List<Book> retrieveAll();
}
