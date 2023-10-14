import org.gradle.api.JavaVersion

object Global {
    object Modules {
        val App = ProjectModule("app")
        val Lesson_2 = ProjectModule("lesson-2")
        val TimeServer = ProjectModule("timeserver")
        val BookShop = ProjectModule("bookShop")
        object Utils {
            private val root = "util"

            val JDBC = ProjectModule("$root:jdbc")
            val Time = ProjectModule("$root:time")
            val Utils = ProjectModule("$root:utils")
        }
    }

    object Plugin {
        val KotlinJVM = Plugin("org.jetbrains.kotlin.jvm", Version.KOTLIN)
        val SpringBoot = Plugin("org.springframework.boot", Version.SPRING_BOOT_PLUGIN)
        val SpringDependencyManagement = Plugin("io.spring.dependency-management", Version.SPRING_DEP_MANAGEMENT_PLUGIN)
    }

    object Library {
        val SpringContext = Library("org.springframework", "spring-context", Version.SPRING_CONTEXT)
        val SpringCore = Library("org.springframework", "spring-core", Version.SPRING_CONTEXT)
        val SpringWebMVC = Library("org.springframework", "spring-webmvc", Version.SPRING_CONTEXT)
        val SpringSecurityCore = Library("org.springframework.security", "spring-security-core", Version.SPRING_SECURITY)
        val SpringSecurityWeb  = Library("org.springframework.security", "spring-security-web", Version.SPRING_SECURITY)
        val SpringSecurityConfig = Library("org.springframework.security", "spring-security-config", Version.SPRING_SECURITY)

        val SpringBootStarter = Library("org.springframework.boot", "spring-boot-starter")
        val SpringBootConfigurationProcessor = Library(
            "org.springframework.boot",
            "spring-boot-configuration-processor",
            Version.SPRING_BOOT_PLUGIN
        )
        val SpringBootThymeleafStarter = Library("org.springframework.boot", "spring-boot-starter-thymeleaf")
        val SpringBootWebStarter = Library("org.springframework.boot", "spring-boot-starter-web")
        val SpringBootJdbcStarter = Library(
            "org.springframework.boot",
            "spring-boot-starter-jdbc",
            Version.SPRING_BOOT_PLUGIN
        )
        val SpringBootDevTools = Library(
            "org.springframework.boot",
            "spring-boot-devtools",
            Version.SPRING_BOOT_PLUGIN
        )

        val ServletApi = Library("jakarta.servlet:", "jakarta.servlet-api", Version.SERVLET_API)

        val ThymeleafSpring5 = Library("org.thymeleaf", "thymeleaf-spring5", Version.THYMELEAF_SPRING5)

        val SpringJDBC = Library("org.springframework", "spring-jdbc", Version.SPRING_CONTEXT)
        val H2DB = Library("com.h2database", "h2", Version.H2_DB)
        val POSTGRESQL = Library("org.postgresql", "postgresql", Version.POSTGRESQL)
        val CommonsFileUpload = Library("commons-fileupload", "commons-fileupload", Version.COMMONS_FILE_UPLOAD)
        val CommonsIO = Library("commons-io", "commons-io", Version.COMMONS_IO)

        val Slf4jApi = Library("org.slf4j", "slf4j-api", Version.SLF_4_J)
        val Log4jApi = Library("org.apache.logging.log4j", "log4j-api", Version.LOG_4_J)
        val Log4jCore = Library("org.apache.logging.log4j", "log4j-core", Version.LOG_4_J)

        val Lombok = Library("org.projectlombok", "lombok", Version.LOMBOK)

        val JakartaAnnotationApi = Library("jakarta.annotation", "jakarta.annotation-api", Version.JAKARTA_ANNOTATION_API)
        val JakartaValidationsApi = Library("jakarta.annotation", "jakarta.validation-api", Version.JAKARTA_VALIDATION_API)
        val HibernateValidator = Library("org.hibernate.validator", "hibernate-validator", Version.HIBERNATE_VALIDATOR)
        val HibernateValidatorAnnotationProcessor = Library(
            "org.hibernate.validator",
            "hibernate-validator-annotation-processor",
            Version.HIBERNATE_VALIDATOR
        )

        val SpringBootTestStarter = Library("org.springframework.boot", "spring-boot-starter-test")
    }


    object Version {
        const val JAVA = "17"
        val JAVA_VERSION = JavaVersion.VERSION_17
        const val KOTLIN = "1.8.21"

        const val SPRING_CONTEXT = "6.0.12"
        const val SPRING_SECURITY = "6.1.2"

        const val SPRING_BOOT_PLUGIN = "3.1.2"
        const val SPRING_DEP_MANAGEMENT_PLUGIN = "1.1.3"

        const val SERVLET_API = "6.0.0"

        const val THYMELEAF_SPRING5 = "3.1.1.RELEASE"

        const val H2_DB = "2.1.214"
        const val POSTGRESQL = "42.6.0"
        const val COMMONS_FILE_UPLOAD = "1.5"
        const val COMMONS_IO = "2.12.0"

        const val SLF_4_J = "2.0.7"
        const val LOG_4_J = "2.20.0"

        const val LOMBOK = "1.18.26"

        const val JAKARTA_ANNOTATION_API = "2.1.1"
        const val JAKARTA_VALIDATION_API = "3.0.2"
        const val HIBERNATE_VALIDATOR = "8.0.0.Final"
    }
}
