import java.awt.*;

public class Wall{

    private int x,y,width,height;

    public Wall(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getHitBox(){
        return new Rectangle(x,y,width,height);
    }

    public void draw(Graphics2D g2){
        g2.fillRect(x,y,width,height);
    }

}
