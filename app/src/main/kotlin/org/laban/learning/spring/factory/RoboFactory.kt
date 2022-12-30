package org.laban.learning.spring.factory

class RoboFactory(
    private val factoryLine: RoboFactoryLine,
    private val orderLength: Int
) {
    fun produce(): List<Robot> {
        return ArrayList<Robot>().apply {
            repeat(orderLength) { add(factoryLine.produce()) }
        }
    }
}