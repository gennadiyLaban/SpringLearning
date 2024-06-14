package org.laban.learning.spring.lessonfinal.service;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@RequiredArgsConstructor
@Service
public class FileSystemStatisticStorageService {
    @Value("${app.storage.statistic.files.root}")
    private String storageRoot;
    @Nullable
    private Path rootPath;

    public OutputStream openOutputStreamFor(@Nonnull String filename) throws IOException {
        var desitnationPath = root().resolve(filename).normalize().toAbsolutePath();
        if (!root().toAbsolutePath().equals(desitnationPath.getParent().toAbsolutePath())) {
            throw new IllegalArgumentException("Cannot store file outside of root directory");
        }
        return Files.newOutputStream(desitnationPath,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
    }

    public Resource loadAsResource(@Nonnull String filename) {
        var file = root().resolve(filename);
        var resource = new FileSystemResource(file);
        if (resource.exists() || resource.isReadable()) {
            return resource;
        }
        throw new IllegalArgumentException("Cannot load file '" + filename + "'");
    }

    @SneakyThrows
    @Nonnull
    private Path root() {
        if (rootPath == null) {
            rootPath = Paths.get(storageRoot);
            if (Files.notExists(rootPath)) {
                Files.createDirectories(rootPath);
            }
        }
        return rootPath;
    }
}
