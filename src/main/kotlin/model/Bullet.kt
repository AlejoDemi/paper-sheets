package model

import edu.austral.ingsis.starships.model.Coordinates
import edu.austral.ingsis.starships.model.Vector
import edu.austral.ingsis.starships.ui.ImageRef

data class Bullet (
    val position: Coordinates,
    val idBullet: String,
    val vector: Vector,
    val damage: Int,
    val skin:ImageRef,
    val shipId:String,
    ):Movable {

    override fun move(deltaTime:Double): Bullet = copy(position=position.move(vector,deltaTime))

    override fun getX(): Double {
      return position.x
    }

    override fun getY(): Double {
        return position.y
    }

    override fun getId(): String {
       return idBullet
    }

    override fun getRotation(): Double {
       return vector.rotation
    }

    override fun collide(collider: Movable): Movable {
        return this
    }

    override fun getLives(): Int {
        return 0
    }
}