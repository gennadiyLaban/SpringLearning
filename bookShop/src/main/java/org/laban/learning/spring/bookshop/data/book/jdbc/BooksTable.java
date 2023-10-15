package org.laban.learning.spring.bookshop.data.book.jdbc;

import org.laban.learning.spring.util.jdbc.SqlTable;
import org.springframework.stereotype.Component;

@Component
public class BooksTable extends SqlTable {

    public final String id = "id";
    public final String authorId = "author_id";
    public final String title = "title";
    public final String priceOld = "price_old";
    public final String price = "price";

    public BooksTable() {
        super("shop", "books");
    }

    public String compositeId() {
        return compositeColumn(id);
    }

    public String aliasId() {
        return columnAlias(id);
    }

    public String compositeAuthorId() {
        return compositeColumn(authorId);
    }

    public String aliasAuthorId() {
        return columnAlias(authorId);
    }

    public String compositeTitle() {
        return compositeColumn(title);
    }

    public String aliasTitle() {
        return columnAlias(title);
    }

    public String compositePriceOld() {
        return compositeColumn(priceOld);
    }

    public String aliasPriceOld() {
        return columnAlias(priceOld);
    }

    public String compositePrice() {
        return compositeColumn(price);
    }

    public String aliasPrice() {
        return columnAlias(price);
    }
}
