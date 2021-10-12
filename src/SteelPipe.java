import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Code for SteelPipe class
 * @author Brandon
 */
public class SteelPipe extends PipeSet{
    private final Image STEEL_IMG = new Image("res/level-1/steelPipe.png");
    private final Image FLAME_IMG = new Image("res/level-1/flame.png");
    private final int FLAME_DUR = 150; // adjusted for devices framerate
    private final int FLAME_FREQ = 250; // changed to suit devices framerate
    private boolean flameOn = false;
    private int frameCount = 0;
    private final DrawOptions FLAME_ROTATE = new DrawOptions().setRotation(Math.PI);
    private final int FLAME_OFFSET = 10;

    /**
     * constructor for SteelPipes
     * @param gapStart input the start of the gap
     */
    public SteelPipe(int gapStart){
        super(gapStart);
        this.setImage(STEEL_IMG);
    }

    /**
     * update method
     */
    @Override
    public void update(){
        super.update();

        renderFlames();
    }

    /**
     * render the flames
     */
    public void renderFlames(){
        frameCount++;
        if (frameCount == FLAME_FREQ){
            frameCount = 0;
        }
        // render flames and set flameOn accordingly
        if (frameCount <= FLAME_DUR){
            flameOn = true;
            FLAME_IMG.draw(this.getX(), this.getTopBox().bottom() + FLAME_IMG.getHeight()/2.0 - FLAME_OFFSET);
            FLAME_IMG.draw(this.getX(), this.getBottomBox().top() - FLAME_IMG.getHeight()/2.0 + FLAME_OFFSET, FLAME_ROTATE);
        } else {
            flameOn = false;
        }
    }

    /**
     * get rectangle for top flame
     * @return Rectangle
     */
    public Rectangle getTopFlameBox(){
        return FLAME_IMG.getBoundingBoxAt(new Point(this.getX(), this.getTopBox().bottom() + FLAME_IMG.getHeight()/2.0 - FLAME_OFFSET));
    }

    /**
     * get rectangle for bottom flame
     * @return Rectangle
     */
    public Rectangle getBotFlameBox(){
        return FLAME_IMG.getBoundingBoxAt(new Point(this.getX(), this.getBottomBox().top() - FLAME_IMG.getHeight()/2.0 + FLAME_OFFSET));
    }

    /**
     * check if flame is on or not
     * @return boolean
     */
    public boolean isFlameOn(){
        return flameOn;
    }

}
