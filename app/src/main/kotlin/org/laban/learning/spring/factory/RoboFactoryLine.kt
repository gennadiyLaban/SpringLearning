package org.laban.learning.spring.factory

interface RoboFactoryLine {
    fun produce(): Robot

    class CleanerFactoryLine : RoboFactoryLine {
        override fun produce(): Robot = Robot.Cleaner()
    }

    class CurrierFactoryLine : RoboFactoryLine {
        override fun produce(): Robot = Robot.Currier()
    }
}