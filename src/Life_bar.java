import bagel.Image;
import bagel.Input;

public class Life_bar {
    private final Image full = new Image("res/level/fullLife.png");
    private final Image used = new Image("res/level/noLife.png");
    private final int INITIAL_X = 100;
    private final int X_GAP = 15;
    private int currLives;
    private int max;
    private int currX;


    public Life_bar(int max) {
        this.max = max;
        this.currLives = max;
    }

    public void update(Input input){

        currX = INITIAL_X;
        // draw full hearts
        for (int i = 0; i < currLives; i++){
            full.drawFromTopLeft(currX, X_GAP);
            currX += X_GAP + full.getWidth();
        }
        // draw used hearts
        for (int i = 0; i < max - currLives; i++){
            used.drawFromTopLeft(currX, X_GAP);
            currX += X_GAP + full.getWidth();
        }

    }

    public void removeLife(){
        this.currLives--;
    }

    public int getLives(){
        return this.currLives;
    }

}
