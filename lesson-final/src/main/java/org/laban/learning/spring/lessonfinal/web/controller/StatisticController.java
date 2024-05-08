package org.laban.learning.spring.lessonfinal.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.service.StatisticService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stat")
public class StatisticController {
    private final StatisticService statisticService;

    @Value("${app.storage.statistic.files.userRegistrations}")
    private String filesUserRegistrations;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/user/registrations")
    public ResponseEntity<Resource> userRegistrations() throws IOException {
        var resource = statisticService.generateAndLoadUserRegistrationStatistic();
        return ResponseEntity.ok()
                .headers(httpHeaders -> httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + filesUserRegistrations))
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(resource);
    }
}
