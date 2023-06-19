package libraries.cleanspring

import Library

object CleanSpring {
    val KotlinJVM = Global.Plugin.KotlinJVM

    val Log4jApi = Global.Library.Log4jApi
    val Log4jCore = Global.Library.Log4jCore

    val SpringContext = Global.Library.SpringContext
    val SpringWebMVC = Global.Library.SpringWebMVC
    val ServletApi = Global.Library.ServletApi

    val ThymeleafSpring5 = Global.Library.ThymeleafSpring5

    val Lombok = Global.Library.Lombok

    val JavaxAnnotationApi = Global.Library.JavaxAnnotationApi
    val JavaxValidationsApi = Global.Library.JavaxValidationsApi
    val HibernateValidator = Global.Library.HibernateValidator
    val HibernateValidatorAnnotationProcessor = Global.Library.HibernateValidatorAnnotationProcessor

    val SpringSecurityCore = Global.Library.SpringSecurityCore
    val SpringSecurityWeb  = Global.Library.SpringSecurityWeb
    val SpringSecurityConfig = Global.Library.SpringSecurityConfig

    val SpringJDBC = Global.Library.SpringJDBC
    val H2DB = Global.Library.H2DB
    val CommonsFileUpload = Global.Library.CommonsFileUpload
    val CommonsIO = Global.Library.CommonsIO
}
