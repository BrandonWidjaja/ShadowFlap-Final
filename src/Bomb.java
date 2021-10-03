import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Bomb extends Weapon{
    private final int RANGE = 50;
    private final Image BOMB_IMG = new Image("res/level-1/bomb.png");
    private int frameCount = 0;

    public Bomb(double yPos){
        this.setyPos(yPos);
    }

    @Override
    public void update(Input input){
        if (!this.isPickedUp() || !this.isUsed()) {
            this.setxPos(this.getxPos() - this.getCurrSpeed());
        }
        System.out.println(this.getxPos());
        if (this.isUsed()){
            frameCount++;
            this.fireWeapon();
        }

        renderWeapon(this.getxPos(), this.getyPos());
    }


    @Override
    public void fireWeapon() {

        this.setxPos(this.getxPos() + this.getThrownSpeed());

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
        if (this.frameCount > this.RANGE){
            return true;
        } else {
            return false;
        }
    }
}
