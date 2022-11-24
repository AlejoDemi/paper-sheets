package utils

import edu.austral.ingsis.starships.model.ClassicGun
import edu.austral.ingsis.starships.model.Coordinates
import edu.austral.ingsis.starships.ui.ImageRef
import javafx.scene.input.KeyCode
import model.DoubleGun

val WIDTH = 1500.0
val HEIGHT = 1000.0
val CENTER = Coordinates(750.0,500.0)
val LEFT_SIDE = Coordinates(500.0,500.0)
val RIGHT_SIDE = Coordinates(1000.0,500.0)
val LIVES = 200
val SPAWN_PROBS=1500
val PAPER_PLANE = ImageRef("paperPlane",60.0,40.0)
val PAPER_BULLET = ImageRef("paperBullet",20.0,20.0)
val RED_PAPER_PLANE= ImageRef("redPlane",60.0,40.0)
val BLUE_PAPER_PLANE= ImageRef("bluePlane",60.0,40.0)

//keys
val PLAYER_1_ACCELERATE = KeyCode.UP
val PLAYER_1_STOP = KeyCode.DOWN
val PLAYER_1_RIGHT = KeyCode.RIGHT
val PLAYER_1_LEFT = KeyCode.LEFT
val PLAYER_1_SHOOT = KeyCode.SPACE

val PLAYER_2_ACCELERATE = KeyCode.W
val PLAYER_2_STOP = KeyCode.S
val PLAYER_2_RIGHT = KeyCode.D
val PLAYER_2_LEFT = KeyCode.A
val PLAYER_2_SHOOT = KeyCode.E

val PLAYER_PAUSE = KeyCode.P

//Guns
val DOUBLE_GUN = DoubleGun()
val CLASSIC_GUN = ClassicGun()


