package org.laban.learning.spring.utils.log.log4j;

import java.net.URI;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.filter.MarkerFilter;
import org.laban.learning.spring.Environment;

@Plugin(name = "CustomConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order(Integer.MAX_VALUE)
public class Log4jConfigurationFactory extends ConfigurationFactory {
    private static final String LOGGER_LAYOUT = "PatternLayout"; // see PatternLayout.class
    private static final String LOG_PATTERN = "%d{HH:mm:ss} %-5p %-30c{2} (%-30F:%L) - %m%n";
    private static final String MARKER_FILTER = "MarkerFilter"; // see MarkerFilter.class

    private static final String FULL_LOG_FILE_APPENDER_NAME = "full_log_file_appender";
    private static final String USER_LOG_FILE_APPENDER_NAME = "user_log_file_appender";
    private static final String FULL_LOG_CONSOLE_APPENDER_NAME = "full_log_console_appender";

    public static final String USR_MARKER = "usr_action";

    private final Environment environment = Environment.getInstance();

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, String name, URI configLocation) {
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        return createConfiguration(name, builder, environment);
    }


    @Override
    protected String[] getSupportedTypes() {
        return new String[] {"*"};
    }

    private static Configuration createConfiguration(
            String name,
            ConfigurationBuilder<BuiltConfiguration> builder,
            Environment environment
    ) {
        builder.setConfigurationName(name);
        builder.setStatusLevel(Level.ALL);

        var pattern = builder.newLayout(LOGGER_LAYOUT).addAttribute("pattern", LOG_PATTERN);

        var fullLogFileAppender = builder
                .newAppender(FULL_LOG_FILE_APPENDER_NAME, FileAppender.PLUGIN_NAME)
                .addAttribute("fileName", environment.FULL_LOG_FILE_NAME)
                .addAttribute("append", false)
                .addAttribute("locking", false)
                .add(pattern);
        var usrActionMarkerFilter = builder
                .newFilter(MARKER_FILTER, Filter.Result.ACCEPT, Filter.Result.DENY)
                .addAttribute(MarkerFilter.ATTR_MARKER, USR_MARKER);
        var userLogFileAppender = builder
                .newAppender(USER_LOG_FILE_APPENDER_NAME, FileAppender.PLUGIN_NAME)
                .addAttribute("fileName", environment.USER_LOG_FILE_NAME)
                .addAttribute("append", false)
                .addAttribute("locking", false)
                .add(pattern)
                .add(usrActionMarkerFilter);
        var consoleAppender = builder
                .newAppender(FULL_LOG_CONSOLE_APPENDER_NAME, ConsoleAppender.PLUGIN_NAME)
                .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT)
                .add(pattern);
        builder.add(fullLogFileAppender);
        builder.add(userLogFileAppender);
        builder.add(consoleAppender);

        var fullLogFileAppenderRef = builder.newAppenderRef(FULL_LOG_FILE_APPENDER_NAME);
        var userLogFileAppenderRef = builder.newAppenderRef(USER_LOG_FILE_APPENDER_NAME);
        var consoleAppenderRef = builder.newAppenderRef(FULL_LOG_CONSOLE_APPENDER_NAME);
        var rootLogger = builder.newRootLogger(Level.ALL)
                .addAttribute("includeLocation", true)
                .addAttribute("additivity", false)
                .add(fullLogFileAppenderRef)
                .add(userLogFileAppenderRef)
                .add(consoleAppenderRef);
        builder.add(rootLogger);

        return builder.build();
    }

}
