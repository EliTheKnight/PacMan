import java.awt.*;

public class Pellets {

    private int x, y, size;

    public Pellets(int x, int y){
        this.x = x;
        this.y = y;
        size = 5;
    }

    public Rectangle getHitBox(){
        return new Rectangle(x,y,size,size);
    }

    public Rectangle getHitBox(int x, int y){
        return new Rectangle(x,y,size,size);
    }

    public void draw(Graphics2D g2){
        g2.fillRect(x,y,size,size);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
