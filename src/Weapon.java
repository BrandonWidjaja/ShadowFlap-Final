import bagel.Input;
import bagel.Window;
import bagel.util.Rectangle;

/**
 * Code for abstract weapon class
 * @author Brandon Widjaja
 */
public abstract class Weapon {
    private double xPos = Window.getWidth();
    private double yPos;
    private final int DEFAULT_SPEED = 2; // changed to suit devices framerate
    private double currSpeed = DEFAULT_SPEED;
    private final double THROWN_SPEED = 5; // changed to suit devices framerate
    private boolean isUsed = false;
    private boolean isPickedUp = false;
    private int frameCount = 0;

    /**
     * update method for weapons
     * @param input input parameter
     */
    public void update(Input input){
        // move weapon at set speed while not picked up or used
        if (!this.isPickedUp() || !this.isUsed()) {
            this.setxPos(this.getxPos() - this.getCurrSpeed());
        }

        // if bird used this weapon, fire it
        if (this.isUsed()){
            frameCount++;
            this.fireWeapon();
        }

        renderWeapon(this.getxPos(), this.getyPos());
    }

    /**
     * getter for isExpired
     * @return boolean
     */
    public abstract boolean isExpired();

    /**
     * setter for speed
     * @param speed speed of weapon
     */
    public void setSpeed(double speed) {
        this.currSpeed = speed;
    }

    /**
     * getter for default speed of weapon
     * @return int
     */
    public int getDefaultSpeed(){
        return this.DEFAULT_SPEED;
    }

    /**
     * getter for thrown_speed
     * @return double
     */
    public double getThrownSpeed(){
        return this.THROWN_SPEED;
    }

    /**
     * getter for current speed
     * @return double
     */
    public double getCurrSpeed() {
        return currSpeed;
    }

    /**
     * setter for xPos
     * @param xPos x position of weapon
     */
    public void setxPos(double xPos){
        this.xPos = xPos;
    }

    /**
     * getter for xPos
     * @return double
     */
    public double getxPos(){
        return this.xPos;
    }

    /**
     * getter for yPos
     * @return double
     */
    public double getyPos(){
        return this.yPos;
    }

    /**
     * setter for yPos
     * @param yPos y position of weapon
     */
    public void setyPos(double yPos){
        this.yPos = yPos;
    }

    /**
     * pick up the weapon
     */
    public void pickUp(){
        this.isPickedUp = true;
    }

    /**
     * getter for isPickedUp
     * @return boolean
     */
    public boolean isPickedUp(){
        return this.isPickedUp;
    }

    /**
     * use the weapon
     */
    public void use(){
        this.isUsed = true;
    }

    /**
     * getter for isUsed
     * @return boolean
     */
    public boolean isUsed(){
        return this.isUsed;
    }

    /**
     * getter for framecount
     * @return int
     */
    public int getFrameCount(){
        return this.frameCount;
    }

    /**
     * fire the weapon
     */
    public void fireWeapon() {
        this.setxPos(this.getxPos() + this.getThrownSpeed());
    }

    /**
     * get the rectangle for this weapon
     * @return Rectangle
     */
    public abstract Rectangle getBoundingBox();

    /**
     * render the weapon
     * @param xPos x position of weapon
     * @param yPos y position of weapon
     */
    public abstract void renderWeapon(double xPos, double yPos);
}


