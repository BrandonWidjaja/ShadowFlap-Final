import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Code for Bomb class
 * @author Brandon Widjaja
 */
public class Bomb extends Weapon{
    private final int RANGE = 50;
    private final Image BOMB_IMG = new Image("res/level-1/bomb.png");

    /**
     * constructor for Bomb
     * @param yPos y position of bomb
     */
    public Bomb(double yPos){
        this.setyPos(yPos);
    }

    /**
     * get the rectangle for this weapon
     * @return Rectangle
     */
    @Override
    public Rectangle getBoundingBox() {
        return BOMB_IMG.getBoundingBoxAt(new Point(this.getxPos(), this.getyPos()));
    }

    /**
     * render the weapon
     * @param xPos x position of weapon
     * @param yPos y position of weapon
     */
    @Override
    public void renderWeapon(double xPos, double yPos) {
        BOMB_IMG.draw(xPos, yPos);
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
