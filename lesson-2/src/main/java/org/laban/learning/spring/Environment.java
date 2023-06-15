package org.laban.learning.spring;

import java.io.IOException;
import java.util.Properties;
import java.util.stream.Stream;

import lombok.SneakyThrows;
import org.springframework.lang.NonNull;

public class Environment {
    private static final String ENV_VARIABLES_FILE_RESOURCES = "env.properties";

    private static final String KEY_DEPLOY_PATH = "deploy_path";
    private static final String KEY_DB_NAME = "db_name";
    private static final String KEY_FULL_LOG_FILE_NAME = "full_log_file_name";
    private static final String KEY_USER_LOG_FILE_NAME = "user_log_file_name";

    private static final String KEY_HOME_DIRECTORY = "catalina.home";
    private static volatile Environment INSTANCE = null;



    private final Properties properties;

    // config properties
    public final String DEPLOY_PATH;
    public final String DB_NAME;
    public final String FULL_LOG_FILE_NAME;
    public final String USER_LOG_FILE_NAME;

    // system properties
    public final String HOME_DIRECTORY;

    private Environment(final Properties properties) {
        this.properties = properties;

        checkRequiredProperties();

        DEPLOY_PATH = properties.getProperty(KEY_DEPLOY_PATH);
        DB_NAME = properties.getProperty(KEY_DB_NAME);
        FULL_LOG_FILE_NAME = properties.getProperty(KEY_FULL_LOG_FILE_NAME);
        USER_LOG_FILE_NAME = properties.getProperty(KEY_USER_LOG_FILE_NAME);

        HOME_DIRECTORY = System.getProperty(KEY_HOME_DIRECTORY);
    }

    private void checkRequiredProperties() {
        var emptyProperty = Stream.of(
                        KEY_DEPLOY_PATH,
                        KEY_DB_NAME,
                        KEY_FULL_LOG_FILE_NAME,
                        KEY_USER_LOG_FILE_NAME
                )
                .filter(key -> {
                    var value = properties.getProperty(key);
                    return value == null || value.isEmpty();
                })
                .findAny();
        if (emptyProperty.isPresent()) {
            throw new IllegalStateException(
                    "Property %s is required, but not defined in %s file".formatted(emptyProperty.get(), ENV_VARIABLES_FILE_RESOURCES)
            );
        }
    }

    private static void load() throws IOException {
        if (INSTANCE != null) {
            throw new IllegalStateException("Environmen has already been initialized");
        }

        final var properties = new Properties();
        properties.load(Environment.class.getResourceAsStream("/" + ENV_VARIABLES_FILE_RESOURCES));

        load(properties);
    }
    private static void load(Properties properties) throws IOException { // for tests in future
        if (INSTANCE != null) {
            throw new IllegalStateException("Environmen has already been initialized");
        }

        var environment = new Environment(properties);
        INSTANCE = environment;
    }

    @SneakyThrows
    @NonNull
    public static Environment getInstance() {
        if (INSTANCE == null) {
            synchronized (Environment.class) {
                if (INSTANCE == null) {
                    Environment.load();
                }
            }
        }

        return INSTANCE;
    }
}
