package ru.spaceshooter.pool;

import ru.spaceshooter.base.SpritesPool;
import ru.spaceshooter.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip();
    }
}
