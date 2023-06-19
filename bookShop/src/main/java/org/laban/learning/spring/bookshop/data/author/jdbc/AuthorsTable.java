package org.laban.learning.spring.bookshop.data.author.jdbc;

import org.laban.learning.spring.utils.jdbc.SqlTable;
import org.springframework.stereotype.Component;

@Component
public class AuthorsTable extends SqlTable {
    public final String id = "id";
    public final String name = "name";

    public AuthorsTable() {
        super("authors");
    }

    public String compositeId() {
        return compositeColumn(id);
    }

    public String compositeName() {
        return compositeColumn(name);
    }
}
