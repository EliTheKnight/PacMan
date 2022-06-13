import java.awt.*;

public class NoPelletZone {

    private int x, y, width, height;

    public NoPelletZone(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getHitBox(){
        return new Rectangle(x,y,width,height);
    }

}
