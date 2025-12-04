package uk.ac.nott.cs.comp2013.controller;

import uk.ac.nott.cs.comp2013.model.Bullet;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.MapBlock;
import uk.ac.nott.cs.comp2013.physicsEngine.BulletPhysics;

import java.util.Iterator;
import java.util.List;

/**
 * Updates bullet movement and resolves collisions with map and enemies.
 */
public class BulletController {

    public void update(List<Bullet> bullets, List<MapBlock> blocks, List<Enemy> enemies) {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.setPosition(bullet.getX() + bullet.getSpeed(), bullet.getY());
            BulletPhysics physics = new BulletPhysics(bullet, blocks, enemies);
            Enemy hit = physics.collidesEnemy();
            if (hit != null) {
                hit.damage();
                iterator.remove();
                continue;
            }
            if (physics.collidesMap() || physics.exceededDistance()) {
                iterator.remove();
            }
        }
    }
}
