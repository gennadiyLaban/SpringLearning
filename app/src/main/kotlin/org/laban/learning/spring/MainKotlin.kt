package org.laban.learning.spring

import org.laban.learning.spring.factory.RoboFactory
import org.laban.learning.spring.utils.log.LogFactoryProvider
import org.laban.learning.spring.utils.log.Logger

fun main(args: Array<String>) {
    println("some %s some1 %s some2 %s".formatted("10", "20", "%s"))
    val logger: Logger = LogFactoryProvider.logCreator().createLogger(RoboFactory::class.java)
    logger.info("Hello logger 2!")
}