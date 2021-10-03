import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.lang.Math;

public class Bird {
    private final Image WING_DOWN_IMAGE = new Image("res/level-0/birdWingDown.png");
    private final Image WING_UP_IMAGE = new Image("res/level-0/birdWingUp.png");
    private final double X = 200;
    private final double FLY_SIZE = 4;
    private final double FALL_SIZE = 0.1;
    private final double INITIAL_Y = 350;
    private final double Y_TERMINAL_VELOCITY = 6;
    private final double SWITCH_FRAME = 10;
    private int frameCount = 0;
    private double y;
    private double yVelocity;
    private Rectangle boundingBox;
    private Weapon weapon;

    public Bird() {
        y = INITIAL_Y;
        yVelocity = 0;
        boundingBox = WING_DOWN_IMAGE.getBoundingBoxAt(new Point(X, y));
    }

    public Rectangle update(Input input) {
        frameCount ++;
        if (input.wasPressed(Keys.SPACE)) {
            yVelocity = -FLY_SIZE;
            WING_DOWN_IMAGE.draw(X, y);
        }
        else {
            yVelocity = Math.min(yVelocity + FALL_SIZE, Y_TERMINAL_VELOCITY);
            if (frameCount % SWITCH_FRAME == 0) {
                WING_UP_IMAGE.draw(X, y);
                boundingBox = WING_UP_IMAGE.getBoundingBoxAt(new Point(X, y));
            }
            else {
                WING_DOWN_IMAGE.draw(X, y);
                boundingBox = WING_DOWN_IMAGE.getBoundingBoxAt(new Point(X, y));
            }
        }
        y += yVelocity;
        if (this.hasWeapon()){
            if (!this.weapon.isUsed()) {

                this.weapon.pickUp();
                this.weapon.setxPos(this.getBox().right());
                this.weapon.setyPos(this.y);
            }
        }


        if (input.wasPressed(Keys.S)){
            this.useWeapon();
        }

        return boundingBox;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return X;
    }

    public void resetY(){
        this.y = INITIAL_Y;
    }

    public Rectangle getBox() {
        return boundingBox;
    }

    public void pickupWeapon(Weapon weapon){
        if (!weapon.isUsed()){
            this.weapon = weapon;
        }
    }

    public boolean hasWeapon(){
        if (weapon == null){
            return false;
        } else {
            return true;
        }
    }

    public void useWeapon(){
        if (this.weapon != null){
            this.weapon.use();
            this.weapon = null;
        }
    }
}