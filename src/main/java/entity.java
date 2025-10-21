import java.awt.*;

public class entity {
    protected boolean isDamaged;
    protected double damagedTime;
    protected boolean isGrounded = false;
    protected int speed = 0;
    protected Image image;
    protected int x;
    protected int y;
    protected double velocity;
    protected double acceleration = 0.5;
    protected int health;
    protected int ammo;
    protected entitystate state;
    public int jumpCounter = 0;
    public int jumpX;
    public int jumpY;
    public double lastTime = System.currentTimeMillis();
    public int lastAnimation = 0;
    public int hitBox;

    public entity(Image image, int x, int y, int health, int ammo, int hitBox) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.health = health;
        this.ammo = ammo;
        this.hitBox = hitBox;
        this.state = new entitystate(true, "idle");
    }


    public boolean intersect() {
        int x2 = x + hitBox;
        int y2 = y + image.getHeight(null);
        boolean isInside = false;
        for (int i = 0; i < MapBlocks.map.size(); i++) {
            MapBlocks block = MapBlocks.map.get(i);
            int blockX = block.x;
            int blockY = block.y;
            int blockX2 = block.x + block.image.getWidth(null);
            int blockY2 = block.y + block.image.getHeight(null);
            boolean widthIsPositive = Math.min(x2, blockX2) > Math.max(x, blockX);
            boolean heightIsPositive = Math.min(y2, blockY2) > Math.max(y, blockY);
            if (widthIsPositive && heightIsPositive) {
                isInside = true;
                break;
            }
        }
        return isInside;
    }


    public void jump() {
        if (jumpCounter == 1) velocity = -6;
        else velocity = -8;
        jumpX = x;
        jumpY = y;
    }


    public entity copy(int newX, int newY) {
        entity copy = new entity(image, newX, newY, health, ammo, hitBox);
        copy.image = image;
        return copy;
    }


    public void gravity() {
        entity entityCopy = copy(x, (int) (y + velocity));
        if (!entityCopy.intersect()) {
            y += velocity;
            velocity += acceleration;
            isGrounded = false;
        } else {
            if (velocity > 1.5) velocity /= 1.5;
            if (velocity < 0) velocity = -(velocity / 4);
            else isGrounded = true;
        }
    }
}

class entitystate {
    protected boolean isFacingForward;
    protected String state;

    public entitystate(boolean isFacingForward, String state) {
        this.isFacingForward = isFacingForward;
        this.state = state;
    }
}