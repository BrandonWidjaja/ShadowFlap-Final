import bagel.Image;

public class SteelPipe extends PipeSet{
    private Image steelImg = new Image("res/level-1/steelPipe.png");

    public SteelPipe(int gapStart){
        super(gapStart);
        this.setImage(steelImg);
    }

    public void update(){
        super.update();
    }

}
