package model

import edu.austral.ingsis.starships.model.Coordinates
import edu.austral.ingsis.starships.model.Vector
import utils.PAPER_BULLET


class DoubleGun():Gun {

    override fun shoot(starship: Starship): List<Bullet> {
        val xPosition1 = starship.position.x + (50* kotlin.math.sin(Math.toRadians(starship.vector.rotation - 15)))
        val yPosition1 = starship.position.y + (50* -kotlin.math.cos(Math.toRadians(starship.vector.rotation - 15)))
        val xPosition2 = starship.position.x + (50* kotlin.math.sin(Math.toRadians(starship.vector.rotation + 15)))
        val yPosition2= starship.position.y + (50* -kotlin.math.cos(Math.toRadians(starship.vector.rotation + 15)))
        return listOf(
            Bullet(
                Coordinates(xPosition1,yPosition1),
                "b" + (0..1000).random(),
                Vector(10.0,starship.vector.rotation-45),
                (10..50).random(),
                PAPER_BULLET,
                starship.idShip
            ),
            Bullet(
                Coordinates(xPosition2,yPosition2),
                "b" + (0..1000).random(),
                Vector(10.0,starship.vector.rotation+45),
                (10..50).random(),
                PAPER_BULLET,
                starship.idShip
            )
        )
    }
}