import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player {

    private int x, y, size, direction, nextDirection;
    private boolean moveLeft, moveRight, moveUp, moveDown;

    private ArrayList<Image> sprites;

    private Image sprite;

    public Player(int x, int y, ArrayList<Image> sprites, Image sprite) {
        this.x = x;
        this.y = y;
        this.size = 23;
        moveLeft = false;
        moveRight = false;
        moveDown = false;
        moveUp = false;
        direction = 0;
        nextDirection = 0;
        this.sprites = sprites;
        this.sprite = sprite;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(sprite,x,y,null);
    }

    public void move(ArrayList<Wall> walls){
    moveRight = true;
    moveDown = true;
    moveLeft = true;
    moveUp = true;
        for (Wall wall: walls){
            if (!(wall instanceof GhostWall)) {
                if (getHitBox(x + 3, y).intersects(wall.getHitBox())) {
                    moveRight = false;
                }
                if (getHitBox(x - 3, y).intersects(wall.getHitBox())) {
                    moveLeft = false   ;
                }
                if (getHitBox(x, y - 3).intersects(wall.getHitBox())) {
                    moveUp = false;
                }
                if (getHitBox(x, y + 3).intersects(wall.getHitBox())) {
                    moveDown = false;
                }
            }
        }

        if (direction == 1 && moveUp){
            nextDirection = 1;
        }
        if (direction == 2 && moveLeft){
            nextDirection = 2;
        }
        if (direction == 3 && moveDown){
            nextDirection = 3;
        }
        if (direction == 4 && moveRight){
            nextDirection = 4;
        }


        if (nextDirection == 1 && moveUp){
            sprite = sprites.get(0);
            y-=2;
        }
        else if (nextDirection == 2 && moveLeft){
            sprite = sprites.get(1);
            x-=2;
        }
        else if (nextDirection == 3 && moveDown){
            sprite = sprites.get(2);
            y+=2;
        }
        else if (nextDirection == 4 && moveRight){
            sprite = sprites.get(3);
            x+=2;
        }


        if (x > 497){
            x = 0;
            y = 237;
        }
        else if(x<-20){
            x = 473;
            y = 237;
        }
    }

    public void pressed(int keyCode){
        if(keyCode == KeyEvent.VK_A){
            direction = 2;
        }
        else if(keyCode == KeyEvent.VK_D){
            direction = 4;
        }
        else if(keyCode == KeyEvent.VK_W){
            direction = 1;
        }
        else if (keyCode == KeyEvent.VK_S){
            direction = 3;
        }
    }

    public Rectangle getHitBox(){
        return new Rectangle(x,y,size,size);
    }

    public Rectangle getHitBox(int x, int y){
        return new Rectangle(x,y,size,size);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setNextDirection(int nextDirection) {
        this.nextDirection = nextDirection;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }
}