package model

import edu.austral.ingsis.starships.model.Coordinates
import edu.austral.ingsis.starships.model.Vector
import edu.austral.ingsis.starships.ui.ImageRef

data class Asteroid (
    val life: Int,
    val size: Double,
    val asteroidId: String,
    val position: Coordinates,
    val movementVector: Vector,
    val color: ImageRef
) :  Movable {

    override fun collide(collider: Movable): Asteroid {
        val newSize = (if (size>100.0){size-10}else{size})
        return when(collider){
            is Starship -> this;
            is Bullet -> copy(life= life-(collider as Bullet).damage,size= newSize,color=ImageRef(pickRandomColor(),newSize,newSize));
            else -> this
        }
    }

    private fun pickRandomColor():String{
        val num = (0..2).random()
        return when(num){
            0-> "blueBalloon"
            1-> "redBalloon"
            else -> "greenBalloon"
        }
    }

    override fun getLives(): Int {
        return life
    }

    override fun move(deltaTime:Double): Asteroid =copy(position = position.move(movementVector,deltaTime))

    override fun getX(): Double {
        return position.x
    }

    override fun getY(): Double {
        return position.y
    }

    override fun getId(): String {
        return asteroidId
    }

    override fun getRotation(): Double {
        return movementVector.rotation
    }


}