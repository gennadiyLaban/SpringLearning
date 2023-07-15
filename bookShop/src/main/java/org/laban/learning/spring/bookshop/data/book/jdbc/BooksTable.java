package org.laban.learning.spring.bookshop.data.book.jdbc;

import org.laban.learning.spring.util.jdbc.SqlTable;
import org.springframework.stereotype.Component;

@Component
public class BooksTable extends SqlTable {

    public final String id = "id";
    public final String authorId = "author_id";
    public final String title = "title";
    public final String priceOld = "priceOld";
    public final String price = "price";

    public BooksTable() {
        super("books");
    }

    public String compositeId() {
        return compositeColumn(id);
    }

    public String compositeAuthorId() {
        return compositeColumn(authorId);
    }

    public String compositeTitle() {
        return compositeColumn(title);
    }

    public String compositePriceOld() {
        return compositeColumn(priceOld);
    }

    public String compositePrice() {
        return compositeColumn(price);
    }
}
