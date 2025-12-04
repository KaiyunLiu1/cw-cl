package uk.ac.nott.cs.comp2013.physicsEngine;

import uk.ac.nott.cs.comp2013.model.Bullet;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.MapBlock;
import uk.ac.nott.cs.comp2013.utils.CollisionUtils;

import java.util.List;

/**
 * 针对单颗子弹的碰撞检测：与地图、与敌人、飞行距离。
 */
public class BulletPhysics {

    private static final int TILE_SIZE = 48;

    private final Bullet bullet;
    private final List<MapBlock> blocks;
    private final List<Enemy> enemies;

    public BulletPhysics(Bullet bullet, List<MapBlock> blocks, List<Enemy> enemies) {
        this.bullet = bullet;
        this.blocks = blocks;
        this.enemies = enemies;
    }

    // 地图碰撞
    public boolean collidesMap() {
        for (MapBlock block : blocks) {
            if (CollisionUtils.overlaps(
                    bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight(),
                    block.getX(), block.getY(), TILE_SIZE, TILE_SIZE)
            ) {
                return true;
            }
        }
        return false;
    }

    // 敌人碰撞
    public Enemy collidesEnemy() {
        for (Enemy e : enemies) {
            if (CollisionUtils.overlaps(
                    bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight(),
                    e.getX(), e.getY(), e.getWidth(), e.getHeight())
            ) {
                return e;
            }
        }
        return null;
    }

    // 飞行距离
    public boolean exceededDistance() {
        int dx = Math.abs(bullet.getX() - bullet.getStartX());
        return dx >= bullet.getMaxDistance();
    }
}
