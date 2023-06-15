package libraries.cleanspring

import Library

object CleanSpring {
    object Log4jApi : Library("org.apache.logging.log4j", "log4j-api", Version.LOG_4_J)
    object Log4jCore : Library("org.apache.logging.log4j", "log4j-core", Version.LOG_4_J)

    object SpringContext : Library("org.springframework", "spring-context", Version.SPRING_CONTEXT)
    object SpringWebMVC : Library("org.springframework", "spring-webmvc", Version.SPRING_CONTEXT)
    object ServletApi : Library("javax.servlet", "javax.servlet-api", Version.SERVLET_API)
    object ThymeleafSpring5 : Library("org.thymeleaf", "thymeleaf-spring5", Version.THYMELEAF_SPRING5)

    object Lombok : Library("org.projectlombok", "lombok", Version.LOMBOK)

    object JavaxAnnotationApi : Library("javax.annotation", "javax.annotation-api", Version.JAVAX_ANNOTATION_API)
    object JavaxValidationsApi : Library("javax.validation", "validation-api", Version.JAVAX_VALIDATION_API)
    object HibernateValidator : Library("org.hibernate.validator", "hibernate-validator", Version.HIBERNATE_VALIDATOR)
    object HibernateValidatorAnnotationProcessor : Library("org.hibernate.validator", "hibernate-validator-annotation-processor", Version.HIBERNATE_VALIDATOR)

    object SpringSecurityCore : Library("org.springframework.security", "spring-security-core", Version.SPRING_SECURITY)
    object SpringSecurityWeb  : Library("org.springframework.security", "spring-security-web", Version.SPRING_SECURITY)
    object SpringSecurityConfig : Library("org.springframework.security", "spring-security-config", Version.SPRING_SECURITY)

    object SpringJDBC : Library("org.springframework", "spring-jdbc", Version.SPRING_CONTEXT)
    object H2DB : Library("com.h2database", "h2", Version.H2_DB)
    object CommonsFileUpload : Library("commons-fileupload", "commons-fileupload", Version.COMMONS_FILE_UPLOAD)
    object CommonsIO : Library("commons-io", "commons-io", Version.COMMONS_IO)

    object Version {
        const val SPRING_CONTEXT = "5.3.27"
        const val SPRING_SECURITY = "5.8.3"
        const val SERVLET_API = "4.0.1"

        const val LOG_4_J = "2.20.0"
        const val THYMELEAF_SPRING5 = "3.1.1.RELEASE"
        const val LOMBOK = "1.18.26"

        const val JAVAX_ANNOTATION_API = "1.3.2"
        const val JAVAX_VALIDATION_API = "2.0.1.Final"
        const val HIBERNATE_VALIDATOR = "6.2.5.Final"

        const val H2_DB = "2.1.214"
        const val COMMONS_FILE_UPLOAD = "1.5"
        const val COMMONS_IO = "2.12.0"
    }
}
