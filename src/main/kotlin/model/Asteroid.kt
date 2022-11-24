package model

import edu.austral.ingsis.starships.model.Coordinates
import edu.austral.ingsis.starships.model.Vector

data class Asteroid (
    val life: Int,
    val size: Double,
    val asteroidId: String,
    val position: Coordinates,
    val movementVector: Vector
) :  Movable {

    override fun collide(collider: Movable): Asteroid {
        return when(collider){
            is Starship -> this;
            is Bullet -> copy(life= life-(collider as Bullet).damage,size= (if (size>80.0){size-10}else{size}));
            else -> this
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