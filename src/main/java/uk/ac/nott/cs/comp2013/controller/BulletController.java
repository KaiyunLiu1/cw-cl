package uk.ac.nott.cs.comp2013.controller;

import uk.ac.nott.cs.comp2013.model.Bullet;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.MapBlock;
import uk.ac.nott.cs.comp2013.model.TileMap;
import uk.ac.nott.cs.comp2013.physics.BulletPhysics;
import java.util.Iterator;
import java.util.List;

/** Manages bullet movement and collision resolution.*/
public class BulletController {

    /**
     * Updates all bullets for one frame.
     *
     * @param bullets bullet list
     * @param tileMap map for collision
     * @param enemies enemies for collision
     */
    public void update(List<Bullet> bullets, TileMap tileMap, List<Enemy> enemies) {
        Iterator<Bullet> iterator = bullets.iterator();

        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            updateBulletPosition(bullet);

            BulletPhysics physics = new BulletPhysics(bullet, tileMap, enemies);

            Enemy hitEnemy = physics.collidesEnemy();
            if (hitEnemy != null) {
                hitEnemy.damage();
                iterator.remove();
                continue;
            }

            if (physics.collidesMap() || physics.exceededDistance()) {
                iterator.remove();
            }
        }
    }

    /** Updates bullet position based on speed.*/
    private void updateBulletPosition(Bullet bullet) {
        bullet.setPosition(bullet.getX() + bullet.getSpeed(), bullet.getY());
    }
}
