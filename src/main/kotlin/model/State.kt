package model

import javafx.scene.input.KeyCode



data class State (
    val gameState: States,
    val movables :List<Movable>,
    val movementKeyMap : Map<KeyCode,MoveAction>,
    val releaseKeyMap : Map<KeyCode,ReleaseAction>
){}

data class MoveAction(val id: String, val movement: KeyMovement)

enum class KeyMovement {
    ACCELERATE,
    TURN_LEFT,
    TURN_RIGHT,
    STOP,
}

data class ReleaseAction(val id: String, val action: Action)

enum class Action{
    SHOOT,
    TOGGLE_PAUSE
}

