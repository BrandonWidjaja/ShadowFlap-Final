import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Code for Rock class
 * @author Brandon Widjaja
 */
public class Rock extends Weapon{
    private final int RANGE = 25;
    private final Image ROCK_IMG = new Image("res/level-1/rock.png");

    /**
     * constructor for Rock
     * @param yPos y position of rock
     */
    public Rock(double yPos){
        this.setyPos(yPos);
    }

    /**
     * get the rectangle for this weapon
     * @return Rectangle
     */
    @Override
    public Rectangle getBoundingBox() {
        return ROCK_IMG.getBoundingBoxAt(new Point(this.getxPos(), this.getyPos()));
    }

    /**
     * render the weapon
     * @param xPos x position of weapon
     * @param yPos y position of weapon
     */
    @Override
    public void renderWeapon(double xPos, double yPos) {
        ROCK_IMG.draw(xPos, yPos);
    }

    /**
     * getter for isExpired
     * @return boolean
     */
    public boolean isExpired(){
        if (this.getFrameCount() > this.RANGE){
            return true;
        } else {
            return false;
        }
    }
}
