import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.lang.Math;

/**
 * Code for Bird class
 * @author Brandon Widjaja
 * some methods adapted from ShadowFlap-1 sample solution
 */
public class Bird {
    private final Image LVL0_WING_DOWN_IMAGE = new Image("res/level-0/birdWingDown.png");
    private final Image LVL0_WING_UP_IMAGE = new Image("res/level-0/birdWingUp.png");
    private final Image LVL1_WING_DOWN_IMAGE = new Image("res/level-1/birdWingDown.png");
    private final Image LVL1_WING_UP_IMAGE = new Image("res/level-1/birdWingUp.png");
    private Image currBirdImgUp = LVL0_WING_UP_IMAGE;
    private Image currBirdImgDown = LVL0_WING_DOWN_IMAGE;
    private final double X = 200;
    private final double FLY_SIZE = 4; // changed to suit devices framerate
    private final double FALL_SIZE = 0.1; // changed to suit devices framerate
    private final double INITIAL_Y = 350;
    private final double Y_TERMINAL_VELOCITY = 6; // changed to suit devices framerate
    private final double SWITCH_FRAME = 10;
    private int frameCount = 0;
    private double y;
    private double yVelocity;
    private Rectangle boundingBox;
    private Weapon weapon;
    private boolean leveledUp = false;

    /**
     * constructor for bird class
     */
    public Bird() {
        y = INITIAL_Y;
        yVelocity = 0;
        boundingBox = currBirdImgDown.getBoundingBoxAt(new Point(X, y));
    }

    /**
     * update bird
     * first if/else clause taken from ShadowFlap-1 sample solution
     * @param input input parameter
     * @return Rectangle
     */
    public Rectangle update(Input input) {
        frameCount ++;
        if (input.wasPressed(Keys.SPACE)) {
            yVelocity = -FLY_SIZE;
            currBirdImgDown.draw(X, y);
        }
        else {
            yVelocity = Math.min(yVelocity + FALL_SIZE, Y_TERMINAL_VELOCITY);
            if (frameCount % SWITCH_FRAME == 0) {
                currBirdImgUp.draw(X, y);
                boundingBox = currBirdImgUp.getBoundingBoxAt(new Point(X, y));
            }
            else {
                currBirdImgDown.draw(X, y);
                boundingBox = currBirdImgDown.getBoundingBoxAt(new Point(X, y));
            }
        }
        y += yVelocity;
        // check for weapon usage
        if (this.hasWeapon()){
            if (!this.weapon.isUsed()) {

                this.weapon.pickUp();
                this.weapon.setxPos(this.getBox().right());
                this.weapon.setyPos(this.y);
            }
        }

        // use the weapon
        if (input.wasPressed(Keys.S)){
            this.useWeapon();
        }

        return boundingBox;
    }

    /**
     * getter for y coord
     * @return double
     */
    public double getY() {
        return y;
    }

    /**
     * getter for x coord
     * @return double
     */
    public double getX() {
        return X;
    }

    /**
     * reset the bird to initial values
     */
    public void reset(){
        this.y = INITIAL_Y;
        this.yVelocity = 0;
    }

    /**
     * get rectangle for bird
     * @return Rectangle
     */
    public Rectangle getBox() {
        return boundingBox;
    }

    /**
     * pick up a weapon
     * @param weapon weapon to be picked up
     */
    public void pickupWeapon(Weapon weapon){
        if (!weapon.isUsed()){
            this.weapon = weapon;
        }
    }

    /**
     * return whether or not the bird has a weapon
     * @return boolean
     */
    public boolean hasWeapon(){
        return weapon != null;
    }

    /**
     * use the held weapon
     */
    public void useWeapon(){
        if (this.weapon != null){
            this.weapon.use();
            this.weapon = null;
        }
    }

    /**
     * level up the bird
     */
    public void levelUpBird(){
        this.leveledUp = true;
        this.currBirdImgDown = LVL1_WING_DOWN_IMAGE;
        this.currBirdImgUp = LVL1_WING_UP_IMAGE;
    }
}