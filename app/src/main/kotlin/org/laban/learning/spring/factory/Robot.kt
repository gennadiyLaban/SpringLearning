package org.laban.learning.spring.factory

interface Robot {
    val name: String

    class Cleaner : Robot {
        override val name: String
            get() = "Cleaner"
    }

    class Currier : Robot {
        override val name: String
            get() = "Currier"
    }
}