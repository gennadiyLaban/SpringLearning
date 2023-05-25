package properties

import org.gradle.api.Project
import java.lang.IllegalStateException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.Properties

open class BaseProjectProperties(
    private val localProperties: Properties,
    private val defaultPorperties: Properties,
) {

    protected fun getProperty(key: String, defaultValue: String? = null): String? {
        return localProperties.getProperty(key, defaultValue)
    }

    fun deployPath(): String = getProperty(KEY_DEPLOY_PATH)

    fun envPath(): String = getProperty(KEY_ENVIRONMENT_PATH)

    fun dbName(): String = getProperty(KEY_DB_NAME)

    fun fullLogFileName(): String = getProperty(KEY_FULL_LOG_FILE_NAME)

    fun userLogFileName(): String = getProperty(KEY_USER_LOG_FILE_NAME)

    fun getProperty(key: String): String {
        return localProperties.getProperty(key)
            ?: defaultPorperties.getProperty(key)
            ?: throw IllegalStateException("$key value is not defined")
    }

    override fun toString(): String {
        return localProperties.toString()
    }

    companion object {
        const val KEY_DEPLOY_PATH = "deploy_path"
        const val KEY_ENVIRONMENT_PATH = "env_path"
        const val KEY_DB_NAME = "db_name"
        const val KEY_FULL_LOG_FILE_NAME = "full_log_file_name"
        const val KEY_USER_LOG_FILE_NAME = "user_log_file_name"

        const val DEFAULT_LOCAL_PROPERTIES = "local.properties"
        const val DEFAULT_PROJECT_PROPERTIES = "default.properties"

        fun readFrom(path: Path, fallback: Boolean): BaseProjectProperties {
            return runCatching {
                BaseProjectProperties(
                    localProperties = Properties().apply {
                        load(Files.newInputStream(path, StandardOpenOption.READ))
                    },
                    defaultPorperties = Properties().apply {
                        load(Files.newInputStream(Paths.get(DEFAULT_PROJECT_PROPERTIES)))
                    }
                )
            }.getOrElse {
                if (fallback) {
                    readFrom(Paths.get(DEFAULT_PROJECT_PROPERTIES), fallback = false)
                } else {
                    throw it
                }
            }
        }

        fun readFrom(path: Path): BaseProjectProperties {
            return readFrom(path, fallback = true)
        }

        fun readFrom(projectDir: String, propertiesFile: String = DEFAULT_LOCAL_PROPERTIES): BaseProjectProperties {
            return readFrom(Paths.get(projectDir, propertiesFile))
        }

        fun readFrom(project: Project, propertiesFile: String = DEFAULT_LOCAL_PROPERTIES): BaseProjectProperties {
            return readFrom(
                projectDir = project.projectDir.toPath().toString(),
                propertiesFile = propertiesFile
            )
        }
    }
}