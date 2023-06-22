package org.laban.learning.spring.bookshop.data.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private Integer id;
    private String author;
    private String title;
    private String priceOld;
    private String price;
}
