import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;
import org.w3c.dom.css.Rect;

import java.nio.channels.Pipe;
import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2021
 *
 * Please filling your name below
 * @author: Brandon Widjaja
 * Code adapted from Project-1 Sample Solution
 */
public class ShadowFlap extends AbstractGame {
    private final Image LVL0_BACKGROUND = new Image("res/level-0/background.png");
    private final Image LVL1_BACKGROUND = new Image("res/level-1/background.png");
    private final String INSTRUCTION_MSG = "PRESS SPACE TO START";
    private final String GAME_OVER_MSG = "GAME OVER!";
    private final String CONGRATS_MSG = "CONGRATULATIONS!";
    private final String SCORE_MSG = "SCORE: ";
    private final String FINAL_SCORE_MSG = "FINAL SCORE: ";
    private final String LVLUP_MSG = "LEVEL-UP!";
    private final String SHOOT_MSG = "PRESS 'S' TO SHOOT";
    private final int FONT_SIZE = 48;
    private final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    private final int SCORE_MSG_OFFSET = 75;
    private final int SHOOT_MSG_OFFSET = 68;
    private final Point SCORE_COORDS = new Point(100, 100);

    private final Bird bird;
    private int score;
    private boolean gameOn;
    private boolean win;
    private Life_bar lives;
    private final ArrayList<PipeSet> pipeArray = new ArrayList<PipeSet>();
    private final ArrayList<PipeSet> removePipeList = new ArrayList<>();
    private final ArrayList<Weapon> weaponArray = new ArrayList<>();
    private final ArrayList<Weapon> removeWeaponList = new ArrayList<>();
    private final int DEFAULT_PIPE_RATE = 250; // Changed to suit devices frame rate
    private double currPipeRate = DEFAULT_PIPE_RATE;
    private final int DEFAULT_WEAPON_DELAY = 125;
    private int weaponFrameCount = 0;
    private final int DEFAULT_WEAPON_RATE = 750;
    private double currWeaponRate = DEFAULT_WEAPON_RATE;
    private int frameCount = 0;
    private final Random rand = new Random();
    private boolean levelUp = false;
    private int levelUpDuration = 200; // changed to suit devices framerate
    private final int[] LVL_0_GAP_START = new int[]{100, 300, 500};
    private final int[] LVL_1_GAP_START = new int[]{100, 500};
    private final int LVL_0_LIVES = 3;
    private final int LVL_1_LIVES = 6;

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
        lives = new Life_bar(LVL_0_LIVES);
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
        frameCount++;
        if (frameCount > DEFAULT_WEAPON_DELAY && levelUp){
            weaponFrameCount++;
        }

        if (!levelUp || frameCount < levelUpDuration){
            LVL0_BACKGROUND.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        } else {
            LVL1_BACKGROUND.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        }

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // game has not started
        if (!gameOn && lives.getLives() > 0) {
            if (!levelUp){
                renderInstructionScreen(input);
            } else if (frameCount >= levelUpDuration){
                renderInstructionScreen(input);
            }
        }

        if (levelUp && frameCount < levelUpDuration){
            renderLvlUp();
            pipeArray.clear();
        }

        // game won
        if (win) {
            renderWinScreen();
        }

        // out of bound detection
        if (birdOutOfBound()) {
            lives.removeLife();
            bird.resetY();
        }

        if (lives.getLives() == 0){
            gameOn = false;
            renderGameOverScreen();
        }

        // game is active and level 1
        if (gameOn && !win && !birdOutOfBound()) {

            updateTimeScale(input);

            if (frameCount % currPipeRate == 0){
                spawnRandomPipe();
            }

            if (weaponFrameCount % currWeaponRate == 0 && weaponFrameCount > 0){
                spawnRandomWeapon();
            }

            bird.update(input);
            Rectangle birdBox = bird.getBox();

            updatePipes(birdBox);

            lives.update(input);

            updateWeapons(birdBox, input);
            renderScore();
            weaponArray.removeAll(removeWeaponList);
            pipeArray.removeAll(removePipeList);
        }


    }

    public boolean birdOutOfBound() {
        return (bird.getY() > Window.getHeight()) || (bird.getY() < 0);
    }

    public void renderInstructionScreen(Input input) {
        // paint the instruction on screen
        FONT.drawString(INSTRUCTION_MSG, (Window.getWidth()/2.0-(FONT.getWidth(INSTRUCTION_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        if (levelUp){
            FONT.drawString(SHOOT_MSG, (Window.getWidth()/2.0-(FONT.getWidth(SHOOT_MSG)/2.0)),
                    (Window.getHeight()/2.0-(FONT_SIZE/2.0) + SHOOT_MSG_OFFSET));
        }
        if (input.wasPressed(Keys.SPACE)) {
            gameOn = true;
        }
    }

    public void renderGameOverScreen() {
        FONT.drawString(GAME_OVER_MSG, (Window.getWidth()/2.0-(FONT.getWidth(GAME_OVER_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    public void renderWinScreen() {
        FONT.drawString(CONGRATS_MSG, (Window.getWidth()/2.0-(FONT.getWidth(CONGRATS_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    public boolean birdFlameCollision(Rectangle birdBox, Rectangle topFlameBox, Rectangle botFlameBox){
        return birdBox.intersects(topFlameBox) || birdBox.intersects(botFlameBox);
    }

    public boolean birdPipeCollision(Rectangle birdBox, Rectangle topPipeBox, Rectangle bottomPipeBox) {
        // check for collision

        return birdBox.intersects(topPipeBox) ||
                birdBox.intersects(bottomPipeBox);
    }

    public boolean weaponPipeCollision(Rectangle topPipeBox, Rectangle botPipeBox, Rectangle weaponBox){
        return weaponBox.intersects(topPipeBox) || weaponBox.intersects(botPipeBox);
    }

    public void renderLvlUp(){
        FONT.drawString(LVLUP_MSG, (Window.getWidth()/2.0-(FONT.getWidth(LVLUP_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
    }

    public void renderScore(){
        String scoreMsg = SCORE_MSG + score;
        FONT.drawString(scoreMsg, SCORE_COORDS.x, SCORE_COORDS.y);
    }

    public void updateScore(PipeSet pipe) {
        // tag passed pipes
        if (bird.getX() > pipe.getTopBox().right() && !pipe.isPassed()) {
            score ++;
            pipe.pass();
        }

        // detect level up
        if (score == LVL_UP_THRESHOLD) {
            levelUp = true;
            frameCount = 0;
            lives = new Life_bar(LVL_1_LIVES);
            gameOn = false;
            win = false;
            score = 0;
            bird.resetY();

        }
    }

    public void spawnRandomPipe(){
        // randomise pipe spawns
        if (!levelUp){
            pipeArray.add(new PipeSet(LVL_0_GAP_START[rand.nextInt(LVL_0_GAP_START.length)]));
        } else {
            // generate random type
            if (rand.nextInt(2) == 0){
                pipeArray.add(new SteelPipe(rand.nextInt(
                        LVL_1_GAP_START[1] - LVL_1_GAP_START[0]) + LVL_1_GAP_START[0]));
            } else {
                pipeArray.add(new PlasticPipe(rand.nextInt(
                        LVL_1_GAP_START[1] - LVL_1_GAP_START[0]) + LVL_1_GAP_START[0]));
            }

        }
    }

    public void spawnRandomWeapon(){
        if (rand.nextInt(2) == 0){
            weaponArray.add(new Bomb(rand.nextInt(
                    LVL_1_GAP_START[1] - LVL_1_GAP_START[0]) + LVL_1_GAP_START[0]));
        } else {
            weaponArray.add(new Rock(rand.nextInt(
                    LVL_1_GAP_START[1] - LVL_1_GAP_START[0]) + LVL_1_GAP_START[0]));
        }

    }

    public void updateTimeScale(Input input){
        // increase timescale
        if (input.wasPressed(Keys.L) && currTimeScale < MAXTIMESCALE){
            currTimeScale++;
        }
        // decrease timescale
        if (input.wasPressed(Keys.K) && currTimeScale >= MINTIMESCALE){
            currTimeScale--;
        }
        // update pipe speed
        for (PipeSet pipe: pipeArray){
            if (pipe.getCurrSpeed() != pipe.getDefaultSpeed() * Math.pow(TIMERATIO, currTimeScale)){
                pipe.setSpeed(pipe.getDefaultSpeed() * Math.pow(TIMERATIO, currTimeScale));
            }
        }
    }

    public void detectPipeCollisions(PipeSet pipe, Rectangle birdBox){

        Rectangle topPipeBox = pipe.getTopBox();
        Rectangle bottomPipeBox = pipe.getBottomBox();
        // bird and flame collision detection
        if (pipe instanceof  SteelPipe){
            Rectangle topFlameBox = ((SteelPipe) pipe).getTopFlameBox();
            Rectangle botFlameBox = ((SteelPipe) pipe).getBotFlameBox();

            if (birdFlameCollision(birdBox, topFlameBox, botFlameBox) && ((SteelPipe) pipe).isFlameOn()){
                lives.removeLife();
                removePipeList.add(pipe);
            }
        }
        // bird and pipe collision detection
        if (birdPipeCollision(birdBox, topPipeBox, bottomPipeBox)){
            lives.removeLife();
            removePipeList.add(pipe);
        }

    }

    public void updatePipes(Rectangle birdBox){
        for (PipeSet pipe: pipeArray){
            pipe.update();
            detectPipeCollisions(pipe, birdBox);

            // remove pipes which go off-screen
            if (pipe.getTopBox().right() < 0){
                removePipeList.add(pipe);
            }
            updateScore(pipe);
        }
    }

    public void updateWeapons(Rectangle birdBox, Input input){
        for (Weapon weapon: weaponArray){
            Rectangle weaponBox = weapon.getBoundingBox();
            weapon.update(input);
            if (birdWeaponCollision(weaponBox, birdBox)){
                bird.pickupWeapon(weapon);
            }
            detectWeaponPipeCollision();
            if (weapon.isExpired()){
                removeWeaponList.add(weapon);
            }
        }
    }

    public void detectWeaponPipeCollision(){
        for (PipeSet pipe: pipeArray){
            for (Weapon weapon: weaponArray) {
                if (weaponPipeCollision(pipe.getTopBox(), pipe.getBottomBox(), weapon.getBoundingBox())
                        && weapon.isUsed()) {

                    if (weapon instanceof Bomb) {
                        removePipeList.add(pipe);
                        score++;
                    } else if (weapon instanceof Rock && pipe instanceof PlasticPipe) {
                        removePipeList.add(pipe);
                        score++;
                    }
                    removeWeaponList.add(weapon);
                }
            }
        }
    }

    public boolean birdWeaponCollision(Rectangle weaponBox, Rectangle birdBox){
        return weaponBox.intersects(birdBox);
    }


}