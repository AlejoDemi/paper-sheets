package model

import model.Bullet
import model.Movable

interface Gun{

    fun shoot(starship: Starship): List<Bullet>;
}