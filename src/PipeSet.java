import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class PipeSet {
    private Image PIPE_IMAGE = new Image("res/level/plasticPipe.png");
    private final int PIPE_GAP = 168;
    private final int DEFAULT_PIPE_SPEED = 2;
    private double currSpeed = DEFAULT_PIPE_SPEED;
    private final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);
    private int gapStart;
    private final double TOP_PIPE_Y;
    private final double BOTTOM_PIPE_Y;
    private double pipeX = Window.getWidth();
    private boolean passed = false;

    public PipeSet(int gapStart) {
        this.gapStart = gapStart;
        this.TOP_PIPE_Y = gapStart - PIPE_IMAGE.getHeight()/2;
        this.BOTTOM_PIPE_Y = TOP_PIPE_Y + PIPE_IMAGE.getHeight() + PIPE_GAP;
    }

    public void renderPipeSet() {
        PIPE_IMAGE.draw(pipeX, TOP_PIPE_Y);
        PIPE_IMAGE.draw(pipeX, BOTTOM_PIPE_Y, ROTATOR);
    }

    public void update() {
        renderPipeSet();
        pipeX -= currSpeed;

    }

    public double getX(){
        return this.pipeX;
    }

    public Rectangle getTopBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, TOP_PIPE_Y));
    }

    public Rectangle getBottomBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, BOTTOM_PIPE_Y));
    }

    public void setImage(Image image){
        this.PIPE_IMAGE = image;
    }

    public void pass(){
        this.passed = true;
    }

    public boolean isPassed(){
        return this.passed;
    }

    public double getCurrSpeed(){
        return this.currSpeed;
    }

    public double getDefaultSpeed(){
        return this.DEFAULT_PIPE_SPEED;
    }

    public void setSpeed(double speed){
        this.currSpeed = speed;
    }

}
