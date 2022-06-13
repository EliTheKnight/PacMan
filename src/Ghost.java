import java.awt.*;
import java.util.ArrayList;

public class Ghost {

    private int x, y, size, direction, iD, counter;
    private Image sprite;

    public Ghost(int x, int y, int iD, int counter, Image sprite) {
        this.x = x;
        this.y = y;
        this.size = 23;
        direction = 1;
        this.iD = iD;
        this.counter = counter;
        this.sprite = sprite;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(sprite,x,y,null);
    }

    public void firstMove(ArrayList<Wall> walls, ArrayList<Ghost> ghosts){
        boolean moveUp = true;
        for (Wall wall: walls) {
            if (!(wall instanceof GhostWall) && !(wall instanceof PlayerWall)) {
                if (getHitBox(x, y - 2).intersects(wall.getHitBox())) {
                    moveUp = false;
                }
            }
        }

        for(Ghost ghost: ghosts) {
            if (ghost.getiD() != iD) {
                if (getHitBox(x, y - 2).intersects(ghost.getHitBox()) && ghost.getY() <= 209) {
                    moveUp = false;
                    counter++;
                }
            }
        }

        if (moveUp){
            y -= 2;
        }
    }

    public void move1(ArrayList<Wall> walls, ArrayList<Ghost> ghosts, ArrayList<Image> sprites){

        boolean moveUp = true;
        boolean moveDown = true;
        boolean moveLeft = true;
        boolean moveRight = true;
        ArrayList<Integer> direct = new ArrayList<Integer>();

        for (Wall wall: walls) {
            if (getHitBox(x, y - 3).intersects(wall.getHitBox())){
                moveUp = false;
            }
            if (getHitBox(x, y + 3).intersects(wall.getHitBox())){
                moveDown = false;
            }
            if (getHitBox(x - 3, y).intersects(wall.getHitBox())){
                moveLeft = false;
            }
            if (getHitBox(x + 3, y).intersects(wall.getHitBox())){
                moveRight = false;
            }
        }

        for (Ghost ghost: ghosts) {
            if(!(ghost.getiD() == iD)) {
                if (getHitBox(x, y - 3).intersects(ghost.getHitBox())) {
                    moveUp = false;
                }
                if (getHitBox(x, y + 3).intersects(ghost.getHitBox())) {
                    moveDown = false;
                }
                if (getHitBox(x - 3, y).intersects(ghost.getHitBox())) {
                    moveLeft = false;
                }
                if (getHitBox(x + 3, y).intersects(ghost.getHitBox())) {
                    moveRight = false;
                }
            }
        }

        if (moveUp)
            direct.add(1);
        if (moveDown)
            direct.add(2);
        if (moveLeft)
            direct.add(3);
        if (moveRight)
            direct.add(4);

        int change = (int)(Math.random()*direct.size());
        if (direction == 1 && direct.get(change) == 2){
            for (Ghost ghost: ghosts) {
                if (!(ghost.getiD() == iD)) {
                    if (getHitBox(x, y - 3).intersects(ghost.getHitBox())) {
                        direction = 2;
                    }
                }
            }
        }
        else if (direction == 2 && direct.get(change) == 1){
            for (Ghost ghost: ghosts) {
                if (!(ghost.getiD() == iD)) {
                    if (getHitBox(x, y + 3).intersects(ghost.getHitBox())) {
                        direction = 1;
                    }
                }
            }
        }
        else if (direction == 3 && direct.get(change) == 4){
            for (Ghost ghost: ghosts) {
                if (!(ghost.getiD() == iD)) {
                    if (getHitBox(x - 2, y).intersects(ghost.getHitBox())) {
                        direction = 4;
                    }
                }
            }
        }
        else if (direction == 4 && direct.get(change) == 3){
            for (Ghost ghost: ghosts) {
                if (!(ghost.getiD() == iD)) {
                    if (getHitBox(x + 2, y).intersects(ghost.getHitBox())) {
                        direction = 3;
                    }
                }
            }
        }
        else{
            direction = direct.get(change);
        }

        if (moveUp && direction == 1) {
            sprite = sprites.get((iD-1)*4 + 3);
            y -= 2;
        }
        if (moveDown && direction == 2){
            sprite = sprites.get((iD-1)*4);
            y+=2;

        }
        if (moveLeft && direction ==3){
            sprite = sprites.get((iD-1)*4 + 1);
            x-=2;
        }
        if (moveRight && direction == 4){
            sprite = sprites.get((iD-1)*4 + 2);
            x+=2;
        }

    }

    public Rectangle getHitBox(int x, int y){
        return new Rectangle(x,y,size,size);
    }

    public Rectangle getHitBox(){
        return new Rectangle(x,y,size,size);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCounter(int counter){
        this.counter = counter;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getiD() {
        return iD;
    }

    public int getCounter() {
        return counter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

}
