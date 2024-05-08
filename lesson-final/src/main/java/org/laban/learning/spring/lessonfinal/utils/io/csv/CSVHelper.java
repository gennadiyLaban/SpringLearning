package org.laban.learning.spring.lessonfinal.utils.io.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class CSVHelper {
    private CSVHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> void printCsvFile(
            Supplier<List<T>> pageContentProvider,
            RecordPrinter<T> printer,
            OutputStream out,
            String ... header
    ) throws IOException {
        try (var csvPrinter = CSVFormat.MONGODB_CSV.builder()
                .setHeader(header)
                .build()
                .print(new PrintWriter(out))) {

            var contentList = pageContentProvider.get();
            while (!contentList.isEmpty()) {
                for (var record : contentList) {
                    printer.print(csvPrinter, record);
                    csvPrinter.println();
                }
                contentList = pageContentProvider.get();
            }
        }
    }

    public static <T> Supplier<List<T>> pageableContentProvider(
            PagingAndSortingRepository<T, ?> repository,
            int pageSize
    ) {
        return new Supplier<>() {
            private Page<T> page;

            @Override
            public List<T> get() {
                if (page == null) {
                    page = repository.findAll(PageRequest.of(0, pageSize));
                } else if (page.hasNext()) {
                    page = repository.findAll(page.nextPageable());
                } else {
                    return Collections.emptyList();
                }
                return page.getContent();
            }
        };
    }

    @FunctionalInterface
    public interface RecordPrinter<T> {
        void print(CSVPrinter printer, T record) throws IOException;
    }
}
