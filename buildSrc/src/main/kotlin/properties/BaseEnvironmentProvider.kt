package properties

import org.gradle.api.Project
import java.io.PrintWriter
import java.lang.IllegalStateException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

open class BaseEnvironmentProvider(private val properties: BaseProjectProperties) {

    open fun writeEnvValues(project: Project) {
        checkEnvValues()

        val envPath = properties.envPath()
        Files.newOutputStream(
            Path.of(project.projectDir.toPath().toString(), envPath),
            StandardOpenOption.WRITE,
            StandardOpenOption.CREATE,
        ).let(::PrintWriter).use { writer ->
            writer.println("# this file was generated by ${BaseEnvironmentProvider::class.java.name}")

            getEnvKeys()
                .stream()
                .filter { key -> !key.equals(BaseProjectProperties.KEY_ENVIRONMENT_PATH) }
                .forEach { key ->
                    writer.println("$key=${properties.getProperty(key)}")
                }
            writer.flush()
            writer.close()
        }
    }

    private fun checkEnvValues() {
        getEnvKeys().stream()
            .filter { key ->
                properties.getProperty(key).isNullOrEmpty()
            }
            .findAny()
            .ifPresent {
                throw IllegalStateException("Property $it is required, but not defined in projecte properties, please check ${BaseProjectProperties.DEFAULT_LOCAL_PROPERTIES} or ${BaseProjectProperties.DEFAULT_PROJECT_PROPERTIES}")
            }
    }

    protected open fun getEnvKeys(): List<String> {
        return listOf(
            BaseProjectProperties.KEY_ENVIRONMENT_PATH,
            BaseProjectProperties.KEY_DEPLOY_PATH,
            BaseProjectProperties.KEY_DB_NAME,
            BaseProjectProperties.KEY_FULL_LOG_FILE_NAME,
            BaseProjectProperties.KEY_USER_LOG_FILE_NAME,
        )
    }
}