package uk.ac.nott.cs.comp2013.physicsEngine;

import uk.ac.nott.cs.comp2013.model.Bullet;
import uk.ac.nott.cs.comp2013.model.Enemy;
import uk.ac.nott.cs.comp2013.model.Player;
import java.util.List;

/**
 * Shooting, damage handling and cooldown tracking for the player.
 */
public final class PlayerCombat {

    private static final long SHOOT_COOLDOWN_MS = 500;
    private static final long DAMAGE_COOLDOWN_MS = 1000;

    private final Player player;
    public PlayerCombat(Player player) {
        this.player = player;
    }

    public void shoot(List<Bullet> bullets) {
        if (player.isJustShot() || player.getAmmo() <= 0) {
            return;
        }

        Bullet bullet = createBullet();
        bullets.add(bullet);

        player.setAmmo(player.getAmmo() - 1);
        player.setJustShot(true);
        player.setLastShotTime(System.currentTimeMillis());
    }

    private Bullet createBullet() {
        int bulletX = player.isFacingForwards() ? player.getX() + 47 : player.getX() - 25;
        int bulletY = (int) player.getY() + 10;
        Bullet bullet = new Bullet(bulletX, bulletY);
        bullet.setSpeed(player.isFacingForwards() ? 10 : -10);
        bullet.setStartPoint(bullet.getX(), (int) bullet.getY());
        return bullet;
    }

    public void damageFrom(Enemy enemy) {
        if (player.isDamaged()) {
            return;
        }

        player.setHealth(player.getHealth() - 1);
        player.setDamaged(true);
        player.setVelocityY(-6);
        if (player.getX() < enemy.getX()) {
            player.setSpeed(-4);
        } else {
            player.setSpeed(4);
        }
    }

    public void refreshCooldowns() {
        long now = System.currentTimeMillis();
        if (player.isDamaged() && (now - player.getDamagedTime()) > DAMAGE_COOLDOWN_MS) {
            player.setDamaged(false);
        }
        if (player.isJustShot() && (now - player.getLastShotTime()) > SHOOT_COOLDOWN_MS) {
            player.setJustShot(false);
        }
    }

    public void resetAfterRespawn() {
        player.setDamaged(false);
        player.setJustShot(false);
        player.setLastShotTime(0);
    }
}
