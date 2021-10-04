import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Rock extends Weapon{
    private final int RANGE = 25;
    private final Image ROCK_IMG = new Image("res/level-1/rock.png");

    public Rock(double yPos){
        this.setyPos(yPos);
    }

    @Override
    public Rectangle getBoundingBox() {
        return ROCK_IMG.getBoundingBoxAt(new Point(this.getxPos(), this.getyPos()));
    }

    @Override
    public void renderWeapon(double xPos, double yPos) {
        ROCK_IMG.draw(xPos, yPos);
    }

    public boolean isExpired(){
        if (this.getFrameCount() > this.RANGE){
            return true;
        } else {
            return false;
        }
    }
}
