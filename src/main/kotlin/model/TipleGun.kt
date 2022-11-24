package model

import edu.austral.ingsis.starships.model.Coordinates
import edu.austral.ingsis.starships.model.Vector
import utils.BULLET_DAMAGE
import utils.BULLET_SPEED
import utils.PAPER_BULLET
import kotlin.math.cos
import kotlin.math.sin

class TripleGun():Gun {

    override fun shoot(starship: Starship): List<Bullet> {
        val xPosition1 = starship.position.x + (50* sin(Math.toRadians(starship.vector.rotation-50)))
        val yPosition1 = starship.position.y + (50* -cos(Math.toRadians(starship.vector.rotation-50)))
        val xPosition2 = starship.position.x + (50* kotlin.math.sin(Math.toRadians(starship.vector.rotation)))
        val yPosition2= starship.position.y + (50* -kotlin.math.cos(Math.toRadians(starship.vector.rotation )))
        val xPosition3 = starship.position.x + (50* kotlin.math.sin(Math.toRadians(starship.vector.rotation + 50)))
        val yPosition3= starship.position.y + (50* -kotlin.math.cos(Math.toRadians(starship.vector.rotation + 50)))

        return listOf(
            Bullet(
                Coordinates(xPosition1,yPosition1),
                "b" + (0..1000).random(),
                Vector(BULLET_SPEED,starship.vector.rotation-10),
                BULLET_DAMAGE /3,
                PAPER_BULLET,
                starship.idShip
            ),
            Bullet(
                Coordinates(xPosition2,yPosition2),
                "b" + (0..1000).random(),
                Vector(BULLET_SPEED,starship.vector.rotation),
                BULLET_DAMAGE /3,
                PAPER_BULLET,
                starship.idShip
            ),
            Bullet(
                Coordinates(xPosition3,yPosition3),
                "b" + (0..1000).random(),
                Vector(BULLET_SPEED,starship.vector.rotation+10),
                BULLET_DAMAGE /3,
                PAPER_BULLET,
                starship.idShip
            )
        )
    }


}