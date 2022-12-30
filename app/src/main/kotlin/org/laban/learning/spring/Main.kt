package org.laban.learning.spring

import org.laban.learning.spring.factory.RoboFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

fun main(args: Array<String>) {

    val context = ClassPathXmlApplicationContext("beans.xml")
    context.getBean<RoboFactory>("factory").produce().forEachIndexed { index, robot ->
        println("robot number ${index}: ${robot.name}")
    }
}

private inline fun <reified T: Any> ApplicationContext.getBean(name: String): T {
    return getBean(name) as T
}