package org.laban.learning.spring.lessonfinal;

import kotlin.random.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookingGenerator {
    @Test
    public void generateBookingRecordValues() {
        var rooms = List.of(1, 2, 3, 4, 5, 6);
        var startDate = Instant.now().plus(12, ChronoUnit.DAYS);
        var bookingRecords = new ArrayList<BookingRecord>();
        for (var roomId : rooms) {
            var bookingAmount = Random.Default.nextInt(2, 7);
            var bookingStartDate = startDate;
            for (var index = 0 ; index < bookingAmount ; index++) {
                var start = bookingStartDate.plus(Random.Default.nextInt(0, 12), ChronoUnit.DAYS);
                var end = start.plus(Random.Default.nextInt(1, 12), ChronoUnit.DAYS);
                bookingRecords.add(new BookingRecord(start, end, roomId));
                bookingStartDate = end;
            }
        }
        Collections.shuffle(bookingRecords);
        bookingRecords.sort(Comparator.comparing(o -> o.roomId));
        System.out.println(toString(bookingRecords));
    }

    private String toString(List<BookingRecord> bookingRecords) {
        var stringBuilder = new StringBuilder();
        for (var index = 0; index < bookingRecords.size(); index++) {
            var record = bookingRecords.get(index);
            stringBuilder.append(toString(record));
            if (index != bookingRecords.size() - 1) {
                stringBuilder.append(",\n");
            } else {
                stringBuilder.append(";");
            }
        }
        return stringBuilder.toString();
    }

    private String toString(BookingRecord bookingRecords) {
        return MessageFormat.format("(''{0}'', ''{1}'', {2})",
                bookingRecords.start, bookingRecords.end, bookingRecords.roomId);
    }

    @AllArgsConstructor
    @Getter
    private static class BookingRecord {
        private Instant start;
        private Instant end;
        private Integer roomId;
    }
}
