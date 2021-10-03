import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Bomb extends Weapon{
    private final int RANGE = 50;
    private final Image BOMB_IMG = new Image("res/level-1/bomb.png");

    public Bomb(double yPos){
        this.setyPos(yPos);
    }

    @Override
    public Rectangle getBoundingBox() {
        return BOMB_IMG.getBoundingBoxAt(new Point(this.getxPos(), this.getyPos()));
    }

    @Override
    public void renderWeapon(double xPos, double yPos) {
        BOMB_IMG.draw(xPos, yPos);
    }

    public boolean isExpired(){
        if (this.getFrameCount() > this.RANGE){
            return true;
        } else {
            return false;
        }
    }
}
