package uk.ac.nott.cs.comp2013.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uk.ac.nott.cs.comp2013.model.Player;

/**
 * Player view and its asset bundle consolidated in one class.
 */
public class PlayerView {

    /**
     * Aggregates player sprites and HUD icons for the view layer.
     */
    public static final class PlayerAssets {
        private final Image[] idleSprites;
        private final Image[] runningSprites;
        private final Image[] hurtSprites;
        private final Image shootingSprite;
        private final Image bulletImage;
        private final Image heartImage;
        private final Image ammoBoxImage;
        private final Image cloudImage;

        public PlayerAssets(Image[] idleSprites,
                            Image[] runningSprites,
                            Image[] hurtSprites,
                            Image shootingSprite,
                            Image bulletImage,
                            Image heartImage,
                            Image ammoBoxImage,
                            Image cloudImage) {
            this.idleSprites = idleSprites;
            this.runningSprites = runningSprites;
            this.hurtSprites = hurtSprites;
            this.shootingSprite = shootingSprite;
            this.bulletImage = bulletImage;
            this.heartImage = heartImage;
            this.ammoBoxImage = ammoBoxImage;
            this.cloudImage = cloudImage;
        }

        public Image[] getIdleSprites() { return idleSprites; }
        public Image[] getRunningSprites() { return runningSprites; }
        public Image[] getHurtSprites() { return hurtSprites; }
        public Image getShootingSprite() { return shootingSprite; }
        public Image getBulletImage() { return bulletImage; }
        public Image getHeartImage() { return heartImage; }
        public Image getAmmoBoxImage() { return ammoBoxImage; }
        public Image getCloudImage() { return cloudImage; }
    }

    private final Player player;
    private final PlayerAssets assets;
    private final ImageView node;

    public PlayerView(Player player, PlayerAssets assets) {
        this.player = player;
        this.assets = assets;

        Image firstIdle = (this.assets != null && this.assets.getIdleSprites() != null && this.assets.getIdleSprites().length > 0)
                ? this.assets.getIdleSprites()[0]
                : null;
        node = new ImageView(firstIdle);
    }

    public PlayerView(Player player) {
        this(player, null);
    }

    public ImageView getNode() { return node; }

    public void render() {
        node.setX(player.getX());
        node.setY(player.getY());

        if (assets != null && assets.getIdleSprites() != null && assets.getIdleSprites().length > 0) {
            node.setImage(assets.getIdleSprites()[0]);
        }
    }
}
