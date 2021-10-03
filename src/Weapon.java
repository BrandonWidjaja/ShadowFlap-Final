import bagel.Input;
import bagel.Window;
import bagel.util.Rectangle;

public abstract class Weapon {
    private double xPos = Window.getWidth();
    private double yPos;
    private final int DEFAULT_SPEED = 2; // modified for devices framerate
    private double currSpeed = DEFAULT_SPEED;
    private final double THROWN_SPEED = 5;
    private boolean isUsed = false;
    private boolean isPickedUp = false;

    public abstract void update(Input input);

    public abstract boolean isExpired();

    public void setSpeed(double speed) {
        this.currSpeed = speed;
    }

    public double getThrownSpeed(){
        return this.THROWN_SPEED;
    }

    public void setxPos(double xPos){
        this.xPos = xPos;
    }

    public double getCurrSpeed() {
        return currSpeed;
    }

    public double getxPos(){
        return this.xPos;
    }

    public double getyPos(){
        return this.yPos;
    }

    public void setyPos(double yPos){
        this.yPos = yPos;
    }

    public void pickUp(){
        this.isPickedUp = true;
    }

    public boolean isPickedUp(){
        return this.isPickedUp;
    }

    public void use(){
        this.isUsed = true;
    }

    public boolean isUsed(){
        return this.isUsed;
    }

    public abstract void fireWeapon();

    public abstract Rectangle getBoundingBox();

    public abstract void renderWeapon(double xPos, double yPos);
}


