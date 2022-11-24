package adapter

import Factory.createAsteroid
import edu.austral.ingsis.starships.ui.*
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import model.*
import utils.*


class Adapter(val state: State, private val spawnProbs:Int){


    private fun adaptStarship(starship: Starship): ElementModel{
        return ElementModel(
            starship.idShip,
            starship.getX(),
            starship.getY(),
            starship.skin.height,
            starship.skin.width,
            starship.vector.rotation,
            ElementColliderType.Elliptical,
            starship.skin
        )
    }

    private fun adaptAsteroid(asteroid: Asteroid): ElementModel{
        return ElementModel(
            asteroid.asteroidId,
            asteroid.getX(),
            asteroid.getY(),
            asteroid.size,
            asteroid.size,
            asteroid.movementVector.rotation,
            ElementColliderType.Elliptical,
            ImageRef("paperBall", asteroid.size, asteroid.size)
        )
    }

    private fun adaptBullet(bullet: Bullet): ElementModel{
        return ElementModel(
            bullet.getId(),
            bullet.position.x,
            bullet.position.y,
            bullet.skin.height,
            bullet.skin.width,
            bullet.vector.rotation,
            ElementColliderType.Rectangular,
            bullet.skin
        )
    }

    private fun accelerate(id: String): Adapter {
        val starship = (state.movables.find { it -> it.getId()==id } as Starship)
        val newShips = state.movables.filter { it-> it.getId()!=id }
        return Adapter(state.copy(movables = newShips.plus(starship.accelerate())),spawnProbs)
    }


    private fun stop(id: String): Adapter {
        val starship = (state.movables.find { it -> it.getId()==id } as Starship)
        val newShips = state.movables.filter { it-> it.getId()!=id }
        return Adapter(state.copy(movables = newShips.plus(starship.stop())),spawnProbs)
    }


    private fun turnLeft(id: String,deltaTime: Double): Adapter {
        if (state.gameState == States.PLAY){
            val starship = (state.movables.find { it -> it.getId()==id } as Starship)
            val newShips = state.movables.filter { it-> it.getId()!=id }
            return Adapter(state.copy(movables = newShips.plus(starship.turnLeft(deltaTime))),spawnProbs)
        }
       return this
    }


    private fun turnRight(id: String,deltaTime: Double): Adapter {
        if (state.gameState ==States.PLAY){
            val starship = (state.movables.find { it -> it.getId()==id } as Starship)
            val newShips = state.movables.filter { it-> it.getId()!=id }
            return Adapter(state.copy(movables = newShips.plus(starship.turnRight(deltaTime))),spawnProbs)
        }
        return this
    }

    private fun shoot(id: String): Adapter {
        if(state.gameState==States.PLAY) {
            val starship = (state.movables.find { it -> it.getId() == id } as Starship)
            return Adapter(
                state.copy(
                    movables = state.movables + starship.shoot()

                ),
                SPAWN_PROBS
            )
        }
        return this

    }


    fun collide(id1: String, id2:String,elements: MutableMap<String, ElementModel>): Adapter {
        var remainingObjects = state.movables.filter { it.getId() != id1 && it.getId() != id2}
        val fromObj = state.movables.find { it.getId() == id1 }
        val toObj = state.movables.find { it.getId() == id2 }
        if(fromObj == null || toObj == null) return this
        val c1 = fromObj.collide(toObj)
        val c2 = toObj.collide(fromObj)
        if(c1.getLives()>0) {
            remainingObjects=remainingObjects.plus(c1)
        }
        if(c2.getLives()>0) {
            remainingObjects=remainingObjects.plus(c2)
        }
        val newAdapter = Adapter(state.copy(
            movables = remainingObjects),
            spawnProbs=if(spawnProbs>200){spawnProbs-1}else{spawnProbs}
        )
        val removedGameObjects = state.movables.filter { obj -> !remainingObjects.any { newObj -> newObj.getId() == obj.getId()} }
        removedGameObjects.forEach { it -> elements.remove(it.getId()) }
        return newAdapter
    }


    fun keyPressed(event: KeyCode,deltaTime: Double): Adapter {
         if (state.movementKeyMap.containsKey(event)){
            val action =state.movementKeyMap.getValue(event)
            return when (action.movement) {
                KeyMovement.ACCELERATE -> accelerate(action.id)
                KeyMovement.STOP -> stop(action.id)
                KeyMovement.TURN_LEFT -> turnLeft(action.id,deltaTime)
                KeyMovement.TURN_RIGHT -> turnRight(action.id,deltaTime)
                else -> this
            }
        }
        return this

    }



    fun keyReleased(event: KeyReleased): Adapter {
        if(state.releaseKeyMap.containsKey(event.key)){
            val action =state.releaseKeyMap.getValue(event.key)
            return when(action.action){
                Action.TOGGLE_PAUSE->toggleState()
                Action.SHOOT->shoot(action.id)
                else -> {this}
            }
        }
        return this
    }

    private fun toggleState(): Adapter {
        if (state.gameState==States.PLAY)return Adapter(state.copy(gameState = States.PAUSE),spawnProbs)
        return Adapter(state.copy(gameState = States.PLAY),spawnProbs)
    }

    fun keyFramePassed(deltaTime:Double):Adapter{
        if(state.gameState===States.PLAY){
            val spawnAsteroid = (0..spawnProbs).random()
            if (spawnAsteroid == 1){
                print("nuevo")
                val newMovables = createAsteroid(state.movables,"a"+(0..1000).random() )
                return Adapter(
                    state.copy(
                        movables = newMovables.map { it.move(deltaTime) }
                    ),
                    spawnProbs=if (spawnProbs>200){spawnProbs-3}else{spawnProbs}
                )
            }
            return Adapter(
                state.copy(
                    movables = state.movables.map { it->it.move(deltaTime) }
                ) ,
                spawnProbs=if (spawnProbs>200){spawnProbs-3}else{spawnProbs}
            )

        }
        return this


    }

    fun adaptElements(elements: Map<String, ElementModel>): Adapter {
        state.movables.forEach {
            elements.getValue(it.getId()).x.set(it.getX())
            elements.getValue(it.getId()).y.set(it.getY())
            elements.getValue(it.getId()).rotationInDegrees.set(it.getRotation())
        }
        return this
    }

    fun addElements(elements: MutableMap<String, ElementModel>): Adapter {
        val newElements = state.movables
        newElements.forEach { elements[it.getId()] = elementToUI(it) }
        return this
    }

    fun updateLives(id:String):Label{
        return when(val element= state.movables.find { it-> it.getId()===id}){
            is Starship-> Label(element.getLives().toString())
            else -> {Label("")}
        }
    }

    private fun elementToUI(movable: Movable): ElementModel {
        return when (movable) {
            is Starship -> adaptStarship(movable)
            is Asteroid -> adaptAsteroid(movable)
            is Bullet -> adaptBullet(movable)
            else -> adaptAsteroid(movable as Asteroid)
        }
    }
}
