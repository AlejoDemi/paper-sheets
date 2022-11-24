package edu.austral.ingsis.starships.model

import adapter.Adapter
import model.Bullet
import model.Gun
import model.Starship
import utils.BULLET_DAMAGE
import utils.BULLET_SPEED
import utils.PAPER_BULLET
import kotlin.math.cos
import kotlin.math.sin

class ClassicGun ():Gun{

     override fun shoot(starship:Starship): List<Bullet> {
         val xPosition = starship.position.x + (50* sin(Math.toRadians(starship.vector.rotation)))
         val yPosition = starship.position.y + (50* -cos(Math.toRadians(starship.vector.rotation)))
         return listOf(Bullet(
                         Coordinates(xPosition,yPosition),
                         "b" + (0..1000).random(),
                         Vector(BULLET_SPEED,starship.vector.rotation),
                         BULLET_DAMAGE,
                         PAPER_BULLET,
                         starship.idShip
                     )
         )
    }
}