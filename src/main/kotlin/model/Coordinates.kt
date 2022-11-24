package edu.austral.ingsis.starships.model

import utils.HEIGHT
import utils.WIDTH
import kotlin.math.cos
import kotlin.math.sin

data class Coordinates (
    val x: Double,
    val y: Double,
    ){

    fun move(vector: Vector, deltaTime:Double): Coordinates {
        if (y<0){
            return copy(
                y= HEIGHT
            )
        }
        else if(y> HEIGHT){
            return copy(
                y=0.0
            )
        }
        else if(x<0){
            return copy(
                x= WIDTH
            )
        }
        else if (x> WIDTH){
            return copy(
                x=0.0
            )
        }

        return copy(
            x = x + 80*deltaTime*(vector.speed * sin(Math.toRadians(vector.rotation))),
            y = y + 80*deltaTime*(vector.speed * -cos(Math.toRadians(vector.rotation)))
        )

    }
}