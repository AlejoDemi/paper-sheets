package model

sealed interface Movable{

    fun move(deltaTime:Double): Movable;

    fun getX(): Double;

    fun getY(): Double;

    fun getId(): String

    fun getRotation(): Double

    fun collide(collider: Movable): Movable

    fun getLives():Int
}