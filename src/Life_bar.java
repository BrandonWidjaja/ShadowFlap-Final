import bagel.Image;
import bagel.Input;

import java.util.ArrayList;

public class Life_bar {
    private final Image full = new Image("res/level/fullLife.png");
    private final Image no = new Image("res/level/noLife.png");
    private int curr;
    private int max;
    private int x = 100;


    public Life_bar(int max) {
        this.max = max;
        this.curr = max;
    }

    public void update(Input input){
        for (int i=0; i<curr; i++){
            full.draw(x, 15);
            x+= 15 + full.getWidth();
        }
        for (int i=0; i<max - curr; i++){
            no.draw(x, 15);
            x+= 15 + full.getWidth();
        }

        x = 100;
    }
    public void removelife(){
        this.curr--;
    }

    public int getLives(){
        return this.curr;
    }
}
