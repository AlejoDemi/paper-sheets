package model
import edu.austral.ingsis.starships.model.Coordinates
import edu.austral.ingsis.starships.model.Vector
import edu.austral.ingsis.starships.ui.ImageRef
import utils.COLLIDE_DAMAGE

data class Starship(
    val life: Int,
    val position: Coordinates,
    val idShip: String,
    val vector: Vector,
    val skin: ImageRef,
    val gun : Gun
    ):  Movable {

    fun accelerate(): Starship = copy(vector = vector.accelerate())
    fun stop(): Starship = copy(vector = vector.stop())
    fun turnLeft(deltaTime: Double): Starship = copy(vector = vector.turnLeft(deltaTime))
    fun turnRight(deltaTime: Double): Starship = copy(vector = vector.turnRight(deltaTime))

    override fun collide(collider: Movable): Starship {
        return when(collider){
            is Asteroid -> copy(life = life- COLLIDE_DAMAGE,vector=Vector(-vector.speed,vector.rotation));
            is Starship -> copy( life = life- COLLIDE_DAMAGE);
            is Bullet -> copy(life= life-(collider as Bullet).damage)
            else -> this
        }
    }

    override fun getLives(): Int {
        return life
    }

    fun shoot(): List<Bullet>{
        return gun.shoot(this)
    }

    override fun move(deltaTime:Double): Starship = copy(position = position.move(vector,deltaTime))

    override fun getX(): Double {
        return position.x
    }

    override fun getY(): Double {
        return position.y
    }

    override fun getId(): String {
        return idShip
    }

    override fun getRotation(): Double {
        return vector.rotation
    }
}