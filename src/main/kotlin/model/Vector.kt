package edu.austral.ingsis.starships.model

data class Vector(
    val speed: Double,
    val rotation: Double
) {
    fun accelerate(): Vector {
        return if (speed < 5) {
            copy(speed = speed + 0.5)
        } else {
            this
        }
    }

    fun stop(): Vector {
        return if (speed > -4) {
            copy(speed = speed - 0.5)
        } else {
            this
        }
    }

    fun turnLeft(deltaTime:Double): Vector = copy(rotation = rotation - 270*deltaTime)
    fun turnRight(deltaTime: Double): Vector = copy(rotation = rotation + 270*deltaTime)
}
