import org.gradle.api.initialization.Settings

fun Settings.init() {
    rootProject.name = "SpringLearning"

    include(Global.Modules.App)
    include(Global.Modules.Lesson1)
    include(Global.Modules.TimeServer)
    include(Global.Modules.BookShop)
    include(Global.Modules.Utils.JDBC)
    include(Global.Modules.Utils.Time)
}
