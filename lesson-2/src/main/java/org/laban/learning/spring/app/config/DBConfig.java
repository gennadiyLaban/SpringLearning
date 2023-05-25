package org.laban.learning.spring.app.config;

import org.laban.learning.spring.Environment;
import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class DBConfig {
    private final Logger logger = LogFactory.getLogger(DBConfig.class);

    @Bean
    public DataSource dataSource(Environment env) {
        logger.info("configure data source");
        try {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(false)
                    .setName(env.DB_NAME)
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("sql/schema.sql")
                    .addScript("sql/data.sql")
                    .setScriptEncoding("UTF-8")
                    .ignoreFailedDrops(true)
                    .build();
        } catch (Throwable th) {
            logger.error("configure data source error", th);
            throw th;
        }
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(Environment env) {
        logger.info("configure NamedParameterJdbcTemplate");
        return new NamedParameterJdbcTemplate(dataSource(env));
    }
}
