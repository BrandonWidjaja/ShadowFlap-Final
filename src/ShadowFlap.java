import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.nio.channels.Pipe;
import java.util.Random;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2021
 *
 * Please filling your name below
 * @author: Brandon Widjaja
 * Code adapted from Project-1 Sample Solution
 */
public class ShadowFlap extends AbstractGame {
    private final Image BACKGROUND_IMAGE = new Image("res/level-0/background.png");
    private final String INSTRUCTION_MSG = "PRESS SPACE TO START";
    private final String GAME_OVER_MSG = "GAME OVER!";
    private final String CONGRATS_MSG = "CONGRATULATIONS!";
    private final String SCORE_MSG = "SCORE: ";
    private final String FINAL_SCORE_MSG = "FINAL SCORE: ";
    private final int FONT_SIZE = 48;
    private final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    private final int SCORE_MSG_OFFSET = 75;
    private Bird bird;
    private int score;
    private boolean gameOn;
    private boolean win;
    private Life_bar lives;
    private ArrayList<PipeSet> pipeArray = new ArrayList<PipeSet>();
    private final int DEFAULT_PIPE_RATE = 250; // Changed to suit devices frame rate
    private double currPipeRate = DEFAULT_PIPE_RATE;
    private int frameCount = 0;
    private Random rand = new Random();
    private int level = 0;
    private int[] LVL_0_GAP_START = new int[]{100, 300, 500};
    private ArrayList<PipeSet> removePipeList = new ArrayList<>();
    private final double TIMERATIO = 1.5;
    private int currTimeScale = 0;
    private final int MAXTIMESCALE = 5;
    private final int MINTIMESCALE = 1;
    private final int LVL_UP_THRESHOLD = 10;


    public ShadowFlap() {
        super(1024, 768, "ShadowFlap");
        bird = new Bird();
        score = 0;
        gameOn = false;
        win = false;
        lives = new Life_bar(3);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        frameCount++;
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // game has not started
        if (!gameOn && lives.getLives() > 0) {
            renderInstructionScreen(input);
        }

        // game won
        if (win) {
            renderWinScreen();
        }

        // out of bound detection
        if (birdOutOfBound()) {
            lives.removelife();
            bird.resetY();
        }

        if (lives.getLives() < 1){
            gameOn = false;
            renderGameOverScreen();
        }

        // game is active and level 1
        if (gameOn && !win && !birdOutOfBound() && level == 0) {

            updateTimeScale(input);

            if (frameCount % currPipeRate == 0){
                spawnRandomPipe();
            }

            bird.update(input);
            Rectangle birdBox = bird.getBox();

            birdPipeCollision(birdBox);

            lives.update(input);
            renderScore();
            pipeArray.removeAll(removePipeList);
        }


    }

    public boolean birdOutOfBound() {
        return (bird.getY() > Window.getHeight()) || (bird.getY() < 0);
    }

    public void renderInstructionScreen(Input input) {
        // paint the instruction on screen
        FONT.drawString(INSTRUCTION_MSG, (Window.getWidth()/2.0-(FONT.getWidth(INSTRUCTION_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        if (input.wasPressed(Keys.SPACE)) {
            gameOn = true;


        }
    }

    public void renderGameOverScreen() {
        FONT.drawString(GAME_OVER_MSG, (Window.getWidth()/2.0-(FONT.getWidth(GAME_OVER_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    public void renderWinScreen() {
        FONT.drawString(CONGRATS_MSG, (Window.getWidth()/2.0-(FONT.getWidth(CONGRATS_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }


    public boolean detectCollision(Rectangle birdBox, Rectangle topPipeBox, Rectangle bottomPipeBox) {
        // check for collision

        return birdBox.intersects(topPipeBox) ||
                birdBox.intersects(bottomPipeBox);
    }

    public void renderScore(){
        String scoreMsg = SCORE_MSG + score;
        FONT.drawString(scoreMsg, 100, 100);
    }

    public void updateScore(PipeSet pipe) {
        if (bird.getX() > pipe.getTopBox().right() && !pipe.isPassed()) {
            score += 1;
            pipe.pass();
        }

        // detect win
        if (score == LVL_UP_THRESHOLD) {
            win = true;
        }
    }

    public void spawnRandomPipe(){
        if (level==0){
            pipeArray.add(new PipeSet(LVL_0_GAP_START[rand.nextInt(LVL_0_GAP_START.length)]));
        }
    }

    public void birdPipeCollision(Rectangle birdBox){

        for (PipeSet pipe: pipeArray){
            pipe.update();
            Rectangle topPipeBox = pipe.getTopBox();
            Rectangle bottomPipeBox = pipe.getBottomBox();
            if (detectCollision(birdBox, topPipeBox, bottomPipeBox)){
                lives.removelife();
                removePipeList.add(pipe);
            }
            updateScore(pipe);
        }
    }

    public void updateTimeScale(Input input){
        if (input.wasPressed(Keys.L) && currTimeScale < MAXTIMESCALE){
            currTimeScale++;
        }

        if (input.wasPressed(Keys.K) && currTimeScale > MINTIMESCALE){
            currTimeScale--;
        }
        for (PipeSet pipe: pipeArray){
            if (pipe.getCurrSpeed() != pipe.getDefaultSpeed() * Math.pow(TIMERATIO, currTimeScale)){
                pipe.setSpeed(pipe.getDefaultSpeed() * Math.pow(TIMERATIO, currTimeScale));
            }
        }
    }

}