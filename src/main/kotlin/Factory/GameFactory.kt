package Factory
import model.*
import edu.austral.ingsis.starships.model.Coordinates
import edu.austral.ingsis.starships.model.Vector
import utils.*


fun soloGame(): State {

    val starship = Starship(
        LIVES,
        CENTER,
        "s1",
        Vector(0.0,0.0),
        PAPER_PLANE,
        SOLO_GUN
    )
    val movables = listOf<Movable>(starship)
    val state =  State(
        States.PLAY,
        movables+ asteroids,
        mapOf(
            Pair(PLAYER_1_ACCELERATE, MoveAction(starship.idShip,KeyMovement.ACCELERATE)),
            Pair(PLAYER_1_STOP, MoveAction(starship.idShip,KeyMovement.STOP)),
            Pair(PLAYER_1_LEFT, MoveAction(starship.idShip,KeyMovement.TURN_LEFT)),
            Pair(PLAYER_1_RIGHT, MoveAction(starship.idShip,KeyMovement.TURN_RIGHT)),
        ),
        mapOf(
            Pair(PLAYER_1_SHOOT, ReleaseAction(starship.idShip,Action.SHOOT)),
            Pair(PLAYER_PAUSE, ReleaseAction(starship.idShip,Action.TOGGLE_PAUSE)),
        )

    )
    return state
}


fun duoGame():State{
    val starship1 = Starship(
        LIVES,
        LEFT_SIDE,
        "s1",
        Vector(0.0,0.0),
        BLUE_PAPER_PLANE,
        DUO_1_GUN
    )
    val starship2 = Starship(
        LIVES,
        RIGHT_SIDE,
        "s2",
        Vector(0.0,0.0),
        RED_PAPER_PLANE,
        DUO_2_GUN
    )
    val movables = listOf<Movable>(starship1,starship2)
    val state =  State(
        States.PLAY,
        movables+ asteroids,
        mapOf(
            Pair(PLAYER_1_ACCELERATE, MoveAction(starship1.idShip,KeyMovement.ACCELERATE)),
            Pair(PLAYER_1_STOP, MoveAction(starship1.idShip,KeyMovement.STOP)),
            Pair(PLAYER_1_LEFT, MoveAction(starship1.idShip,KeyMovement.TURN_LEFT)),
            Pair(PLAYER_1_RIGHT, MoveAction(starship1.idShip,KeyMovement.TURN_RIGHT)),
            Pair(PLAYER_2_ACCELERATE, MoveAction(starship2.idShip,KeyMovement.ACCELERATE)),
            Pair(PLAYER_2_STOP, MoveAction(starship2.idShip,KeyMovement.STOP)),
            Pair(PLAYER_2_LEFT, MoveAction(starship2.idShip,KeyMovement.TURN_LEFT)),
            Pair(PLAYER_2_RIGHT, MoveAction(starship2.idShip,KeyMovement.TURN_RIGHT)),
        ),
        mapOf(
            Pair(PLAYER_1_SHOOT, ReleaseAction(starship1.idShip,Action.SHOOT)),
            Pair(PLAYER_2_SHOOT, ReleaseAction(starship2.idShip,Action.SHOOT)),
            Pair(PLAYER_PAUSE, ReleaseAction(starship1.idShip,Action.TOGGLE_PAUSE)),
        )

    )
    return state
}


val asteroids = listOf(
    Asteroid(160,160.0,"a1",
        spawnInBorder(),
        Vector((1..3).random().toDouble(), (0..360).random().toDouble())
    ),
    Asteroid(70,70.0,"a2",
        spawnInBorder(),
        Vector((1..3).random().toDouble(), (0..360).random().toDouble())
    ),
    Asteroid(100,100.0,"a3",
       spawnInBorder(),
        Vector((1..3).random().toDouble(), (0..360).random().toDouble())
    ),
    Asteroid(70,70.0,"a4",
        spawnInBorder(),
        Vector((1..3).random().toDouble(), (0..360).random().toDouble())
    )
)

fun createAsteroid(movables: List<Movable>, id: String): List<Movable> {
    val life = (40..150).random()
    val asteroid = Asteroid(
        life,
        life.toDouble(),
        id,
        spawnInBorder(),
        Vector((1..3).random().toDouble(), (0..360).random().toDouble())
    )
    return movables.plus(asteroid)
}


fun spawnInBorder(): Coordinates {
    val side= (0..3).random()
    return when(side){
        0-> Coordinates(0.0,(0..HEIGHT.toInt()).random().toDouble())
        1-> Coordinates((0..WIDTH.toInt()).random().toDouble(),0.0)
        2-> Coordinates(WIDTH,(0..HEIGHT.toInt()).random().toDouble())
        3-> Coordinates((0..WIDTH.toInt()).random().toDouble(), HEIGHT)
        else -> {
            Coordinates(0.0,0.0)
        }
    }
}