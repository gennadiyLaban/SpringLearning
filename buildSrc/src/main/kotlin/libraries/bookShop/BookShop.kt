package libraries.bookShop

import Library
import Plugin

object BookShop {
    val pluginSpringBoot = Global.Plugin.SpringBoot
    val pluginSpringDependencyManagement = Global.Plugin.SpringDependencyManagement

    val libLombok = Global.Library.Lombok

    val libSpringBootStarter = Global.Library.SpringBootStarter
    val libSpringBootConfigurationProcessor = Global.Library.SpringBootConfigurationProcessor
    val libSpringBootThymeleafStarter = Global.Library.SpringBootThymeleafStarter
    val libSpringBootWebStarter = Global.Library.SpringBootWebStarter

    val libH2Database = Global.Library.H2DB
    val libSpringBootJdbcStarter = Global.Library.SpringBootJdbcStarter

    val testLibSpringBootTestStarter = Global.Library.SpringBootTestStarter
}
