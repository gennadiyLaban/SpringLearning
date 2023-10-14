package org.laban.learning.spring.bookshop.data.author.jdbc;

import org.laban.learning.spring.util.jdbc.SqlTable;
import org.springframework.stereotype.Component;

@Component
public class AuthorsTable extends SqlTable {
    public final String id = "id";
    public final String first_name = "first_name";
    public final String last_name = "last_name";

    public AuthorsTable() {
        super("shop", "authors");
    }

    public String compositeId() {
        return compositeColumn(id);
    }

    public String aliasId() {
        return columnAlias(id);
    }

    public String compositeFirstName() {
        return compositeColumn(first_name);
    }

    public String aliasFirstName() {
        return columnAlias(first_name);
    }

    public String compositeLastName() {
        return compositeColumn(last_name);
    }

    public String aliasLastName() {
        return columnAlias(last_name);
    }
}
