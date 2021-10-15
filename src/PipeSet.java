import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Code for PipeSet class
 * @author Brandon Widjaja
 */
public class PipeSet {
    private Image PIPE_IMAGE = new Image("res/level/plasticPipe.png");
    private final int PIPE_GAP = 168;
    private final int DEFAULT_PIPE_SPEED = 2; // changed to suit devices framerate
    private double currSpeed = DEFAULT_PIPE_SPEED;
    private final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);
    private int gapStart;
    private final double TOP_PIPE_Y;
    private final double BOTTOM_PIPE_Y;
    private double pipeX = Window.getWidth();
    private boolean passed = false;

    /**
     * constructor for PipeSet
     * @param gapStart input start of the gap
     */
    public PipeSet(int gapStart) {
        this.gapStart = gapStart;
        this.TOP_PIPE_Y = gapStart - PIPE_IMAGE.getHeight()/2;
        this.BOTTOM_PIPE_Y = TOP_PIPE_Y + PIPE_IMAGE.getHeight() + PIPE_GAP;
    }

    /**
     * render the PipeSet
     */
    public void renderPipeSet() {
        PIPE_IMAGE.draw(pipeX, TOP_PIPE_Y);
        PIPE_IMAGE.draw(pipeX, BOTTOM_PIPE_Y, ROTATOR);
    }

    /**
     * update method
     */
    public void update() {
        renderPipeSet();
        pipeX -= currSpeed;

    }

    /**
     * getter for x pos of pipeSet
     * @return double
     */
    public double getX(){
        return this.pipeX;
    }

    /**
     * get the top rectangle of PipeSet
     * @return Rectangle
     */
    public Rectangle getTopBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, TOP_PIPE_Y));
    }

    /**
     * get the bottom rectangle of PipeSet
     * @return Rectangle
     */
    public Rectangle getBottomBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, BOTTOM_PIPE_Y));
    }

    /**
     * set the image for PipeSet
     * @param image input image to set
     */
    public void setImage(Image image){
        this.PIPE_IMAGE = image;
    }

    /**
     * mark the pipes as passed
     */
    public void pass(){
        this.passed = true;
    }

    /**
     * check if pipe is passed
     * @return boolean
     */
    public boolean isPassed(){
        return this.passed;
    }

    /**
     * get current speed of pipes
     * @return double
     */
    public double getCurrSpeed(){
        return this.currSpeed;
    }

    /**
     * get default speed of pipes
     * @return double
     */
    public double getDefaultSpeed(){
        return this.DEFAULT_PIPE_SPEED;
    }

    /**
     * set speed of pipes
     * @param speed input speed to change to
     */
    public void setSpeed(double speed){
        this.currSpeed = speed;
    }

}
