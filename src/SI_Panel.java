import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

public class SI_Panel extends JPanel {

    private Timer timer;
    private Player player;
    private Ghost ghost1, ghost2, ghost3, ghost4;
    private ArrayList<Ghost> ghosts;
    private ArrayList<Wall> walls;
    private ArrayList<Pellets> pellets;
    private ArrayList<NoPelletZone> zones;
    private int lives, playerLoss, score, edible;
    private boolean lose, win, pow;

    private ArrayList<Image> playerSprite, ghostSprites;

    public SI_Panel(int width, int height) {

        playerSprite = new ArrayList<>();
        ghostSprites = new ArrayList<>();
            try{
                Image pacU = ImageIO.read(new File("./pacSprites/pacmanU.png"));
                Image pacL = ImageIO.read(new File("./pacSprites/pacmanL.png"));
                Image pacD = ImageIO.read(new File("./pacSprites/pacmanD.png"));
                Image pacR = ImageIO.read(new File("./pacSprites/pacmanR.png"));
                Image pacFull = ImageIO.read(new File("./pacSprites/pacmanFull.png"));
                playerSprite.add(pacU);
                playerSprite.add(pacL);
                playerSprite.add(pacD);
                playerSprite.add(pacR);
                playerSprite.add(pacFull);

                Image BlueGhostD = ImageIO.read(new File("./GhostSprites/BlueGhostD.png"));
                Image BlueGhostL = ImageIO.read(new File("./GhostSprites/BlueGhostL.png"));
                Image BlueGhostR = ImageIO.read(new File("./GhostSprites/BlueGhostR.png"));
                Image BlueGhostU = ImageIO.read(new File("./GhostSprites/BlueGhostU.png"));
                Image OrangeGhostD = ImageIO.read(new File("./GhostSprites/OrangeGhostD.png"));
                Image OrangeGhostL = ImageIO.read(new File("./GhostSprites/OrangeGhostL.png"));
                Image OrangeGhostR = ImageIO.read(new File("./GhostSprites/OrangeGhostR.png"));
                Image OrangeGhostU = ImageIO.read(new File("./GhostSprites/OrangeGhostU.png"));
                Image PinkGhostD = ImageIO.read(new File("./GhostSprites/PinkGhostD.png"));
                Image PinkGhostL = ImageIO.read(new File("./GhostSprites/PinkGhostL.png"));
                Image PinkGhostR = ImageIO.read(new File("./GhostSprites/PinkGhostR.png"));
                Image PinkGhostU = ImageIO.read(new File("./GhostSprites/PinkGhostU.png"));
                Image RedGhostD = ImageIO.read(new File("./GhostSprites/RedGhostD.png"));
                Image RedGhostL = ImageIO.read(new File("./GhostSprites/RedGhostL.png"));
                Image RedGhostR = ImageIO.read(new File("./GhostSprites/RedGhostR.png"));
                Image RedGhostU = ImageIO.read(new File("./GhostSprites/RedGhostU.png"));
                ghostSprites.add(BlueGhostD);
                ghostSprites.add(BlueGhostL);
                ghostSprites.add(BlueGhostR);
                ghostSprites.add(BlueGhostU);
                ghostSprites.add(OrangeGhostD);
                ghostSprites.add(OrangeGhostL);
                ghostSprites.add(OrangeGhostR);
                ghostSprites.add(OrangeGhostU);
                ghostSprites.add(PinkGhostD);
                ghostSprites.add(PinkGhostL);
                ghostSprites.add(PinkGhostR);
                ghostSprites.add(PinkGhostU);
                ghostSprites.add(RedGhostD);
                ghostSprites.add(RedGhostL);
                ghostSprites.add(RedGhostR);
                ghostSprites.add(RedGhostU);

                Image PowGhost = ImageIO.read(new File("./GhostSprites/PowGhost.png"));
                Image FlickerGhost = ImageIO.read(new File("./GhostSprites/FlickerGhost.png"));

                ghostSprites.add(PowGhost);
                ghostSprites.add(FlickerGhost);

            }catch (Exception e){e.printStackTrace();}

        player = new Player(240,290, playerSprite, playerSprite.get(4));

        ghosts = new ArrayList<>();
        ghosts.add(ghost1 = new Ghost(240, 225, 1, 320, ghostSprites.get(3)));
        ghosts.add(ghost2 = new Ghost(240, 225, 2, 620, ghostSprites.get(7)));
        ghosts.add(ghost3 = new Ghost(240, 225, 3, 920, ghostSprites.get(11)));
        ghosts.add(ghost4 = new Ghost(240, 225, 4, 1220, ghostSprites.get(15)));

        setBounds(0, 0, width, height);
        setupKeyListener();
        lose = false;
        win = false;
        pow = false;
        edible = 0;
        score = 0;
        lives = 3;
        playerLoss = 0;
        timer = new Timer(1000/60, e -> update());

        walls = new ArrayList<>();
        walls();
        zones = new ArrayList<>();
        noPZones();
        pellets = new ArrayList<>();
        addPellets();
        timer.start();
    }

    public void update(){
        edible ++;
        playerLoss --;
        player.move(walls);
        updateGhost1();
        updateBoard();
        repaint();
    }

    public void addPellets(){
        PowerPellet pp1 = new PowerPellet(20,20);
        PowerPellet pp2 = new PowerPellet(20,475);
        PowerPellet pp3 = new PowerPellet(475,20);
        PowerPellet pp4 = new PowerPellet(475,475);
        pellets.add(pp1);
        pellets.add(pp2);
        pellets.add(pp3);
        pellets.add(pp4);
        for (int r = 20; r<490; r+= 5){
            for (int c = 20; c < 490; c += 5){
                boolean add = true;
                Pellets pell = new Pellets(r,c);
                for (Wall wall:walls){
                    if (pell.getHitBox().intersects(wall.getHitBox()) ){
                        add = false;
                    }
                    for (int i = 0; i < 8; i++) {
                        if (pell.getHitBox(r-i, c).intersects(wall.getHitBox())){
                            add = false;
                        }
                        if (pell.getHitBox(r+i, c).intersects(wall.getHitBox())){
                            add = false;
                        }
                        if (pell.getHitBox(r, c-i).intersects(wall.getHitBox())){
                            add = false;
                        }
                        if (pell.getHitBox(r, c+i).intersects(wall.getHitBox())){
                            add = false;
                        }
                    }
                }
                for (NoPelletZone zone: zones){
                    if (pell.getHitBox().intersects(zone.getHitBox())){
                        add = false;
                    }
                }
                for (Pellets pellet: pellets){
                    if (r <= pellet.getX() && r+25 >= pellet.getX()){
                        if (c <= pellet.getY() && c+25 >= pellet.getY()){
                            add = false;
                        }
                        if (c >= pellet.getY() && c-25 <= pellet.getY()){
                            add = false;
                        }
                    }
                    if (r >= pellet.getX() && r-25 <= pellet.getX()){
                        if (c <= pellet.getY() && c+25 >= pellet.getY()){
                            add = false;
                        }
                        if (c >= pellet.getY() && c-25 <= pellet.getY()){
                            add = false;
                        }
                    }
                }
                if (player.getHitBox().intersects(pell.getHitBox())){
                    add = false;
                }
                if (add){
                    pellets.add(pell);
                }
            }
        }

    }

    public void updateGhost1(){
        for (Ghost ghost: ghosts){
            if (ghost.getCounter() <= 20 && ghost.getCounter() >= 0){
                ghost.firstMove(walls, ghosts);
            }
            else if(ghost.getCounter() < 0){
                ghost.move1(walls, ghosts, ghostSprites);
            }
                ghost.setCounter(ghost.getCounter() - 1);
        }

    }

    public void updateBoard(){
        for (int i = 0; i < pellets.size(); i++){
            Pellets pel = pellets.get(i);
            if (player.getHitBox().intersects(pel.getHitBox())){
                if (pel instanceof PowerPellet){
                    pow = true;
                    edible = 0;
                    pellets.remove(pel);
                    score += 50;
                }
                else {
                    pellets.remove(pel);
                    score += 10;
                }
            }
        }

        for (int i = 0; i < ghosts.size(); i++){
            Ghost ghost = ghosts.get(i);
            if (pow){
                if (ghost.getHitBox().intersects(player.getHitBox())){
                    ghost.setX(240);
                    ghost.setY(225);
                    ghost.setCounter(480);
                    score += 200;
                }
            }
            else if (ghost.getHitBox().intersects(player.getHitBox())){
                if (playerLoss < 0) {
                    loseLife();
                }
            }
        }

        if (edible > 480){
            pow = false;
        }

        if (pellets.size() == 0){
            win();
        }
    }

    public void loseLife() {
        lives--;
        playerLoss = 85;
        player.setX(240);
        player.setY(290);
        player.setDirection(0);
        player.setNextDirection(0);
        player.setSprite(playerSprite.get(4));
        for (Ghost ghost: ghosts){
            ghost.setX(240);
            ghost.setY(225);
            ghost.setDirection(0);
            ghost.setCounter((300*ghost.getiD()) + 20);
        }
        if (lives <= 0) {
            lose();
        }
    }

    public void setupKeyListener(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                player.pressed(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 500, 500);
        Font stringFont = new Font( "SansSerif", Font.PLAIN, 18 );
        g2.setFont(stringFont);
        g2.drawString("score: " + score, 5, 517);
        g2.drawString("Lives: " + lives, 425, 517);
        if (win){
            g2.setColor(Color.WHITE);
            g2.setFont(stringFont);
            g2.drawString("YOU WIN", 220, 240);
        }
        else if(lose){
            g2.setColor(Color.WHITE);
            g2.setFont(stringFont);
            g2.drawString("GAME OVER", 220, 240);
        }
        else {
            for (Wall wall : walls) {
                if (!(wall instanceof GhostWall) && !(wall instanceof PlayerWall)) {
                    g2.setColor(Color.YELLOW);
                    wall.draw(g2);
                }
            }
            for (Pellets pellet : pellets) {
                if (pellet instanceof PowerPellet){
                    g2.setColor(Color.GREEN);
                }
                else {
                    g2.setColor(Color.BLUE);
                }
                pellet.draw(g2);
            }


            for (Ghost ghost: ghosts){
                if (ghost.getX() != 240 && ghost.getY() != 225) {
                    if (pow && edible <= 480) {
                        ghost.setSprite(ghostSprites.get(16));
                    }
                    if (pow && edible >= 360) {
                        if ((edible / 30) % 2 == 0) {
                            ghost.setSprite(ghostSprites.get(17));
                        }
                    }
                }
                ghost.draw(g2);
            }


            g2.setColor(Color.WHITE);
            if (playerLoss > 0) {
                Color color = new Color(255, 255 - playerLoss * 3, 255 - playerLoss * 3);
                g2.setColor(color);
            }
            player.draw(g2);
        }
    }

    public void noPZones(){
        NoPelletZone z1 = new NoPelletZone(210,210, 85,55);
        NoPelletZone z2 = new NoPelletZone(0,225,70,50);
        NoPelletZone z3 = new NoPelletZone(430,225,70,50);
        NoPelletZone z4 = new NoPelletZone(340,315,65,115);
        NoPelletZone z5 = new NoPelletZone(95,315,65,115);
        NoPelletZone z6 = new NoPelletZone(320,65,85,135);
        NoPelletZone z7 = new NoPelletZone(95,65,85,135);
        NoPelletZone z8 = new NoPelletZone(185,315,125,90);
        NoPelletZone z9 = new NoPelletZone(210,95,80,90);

        zones.add(z1);
        zones.add(z2);
        zones.add(z3);
        zones.add(z4);
        zones.add(z5);
        zones.add(z6);
        zones.add(z7);
        zones.add(z8);
        zones.add(z9);


    }

    public void walls(){
        //boundaries
        Wall wall = new Wall(0,0, 500, 10);
        Wall wall1 = new Wall(0,490, 500, 10);
        Wall wall2 = new Wall(0,0, 10, 230);
        Wall wall3 = new Wall(0,275, 10, 225);
        Wall wall4 = new Wall(490,0, 10, 230);
        Wall wall5 = new Wall(490,275, 10, 225);

        //corners
        Wall wall6 = new Wall(35,35, 55, 5);
        Wall wall7 = new Wall(35,35, 5, 70);
        Wall wall8 = new Wall(35,460, 55, 5);
        Wall wall9 = new Wall(35,390, 5, 70);
        Wall wall10 = new Wall(415,35, 50, 5);
        Wall wall11 = new Wall(460,35, 5, 70);
        Wall wall12 = new Wall(415,460, 50, 5);
        Wall wall13 = new Wall(460,390, 5, 70);

        //mid box
        Wall wall14 = new Wall(210,255, 85, 5);
        Wall wall15 = new Wall(125,225, 85, 5);
        Wall wall16 = new Wall(290,225, 85, 5);
        Wall wall17 = new Wall(210,210, 5, 50);
        Wall wall18 = new Wall(290,210, 5, 50);
        Wall wall72 = new Wall(210, 210, 30,5);
        Wall wall73 = new Wall(265,210,30,5);

        //L,R side strait walls
        Wall wall19 = new Wall(35,130,5,70);
        Wall wall20 = new Wall(35, 305,5,60);
        Wall wall21 = new Wall(460,130,5,70);
        Wall wall22 = new Wall(460, 305,5,60);

        //connectors to TP
        Wall wall23 = new Wall(0, 275,70,5);
        Wall wall24 = new Wall(0, 225,70,5);
        Wall wall25 = new Wall(430, 275,70,5);
        Wall wall26 = new Wall(430, 225,70,5);

        //walls connected to ^^^
        Wall wall27 = new Wall(65, 280,5,60);
        Wall wall28 = new Wall(65, 165,5,60);
        Wall wall29 = new Wall(430, 280,5,60);
        Wall wall30 = new Wall(430, 165,5,60);

        //2nd straight side walls
        Wall wall31 = new Wall(65, 65,5,75);
        Wall wall32 = new Wall(65, 365,5,70);
        Wall wall33 = new Wall(430, 65,5,75);
        Wall wall34 = new Wall(430, 365,5,70);

        //bottom T
        Wall wall35 = new Wall(250, 430,5,60);
        Wall wall36 = new Wall(185, 430,130,5);

        //bottom straight lines
        Wall wall37 = new Wall(115, 460,110,5);
        Wall wall38 = new Wall(280, 460,110,5);

        //bottom rects
        Wall wall39 = new Wall(340, 315,65,5);
        Wall wall40 = new Wall(340, 315,5,115);
        Wall wall41 = new Wall(400, 315,5,115);
        Wall wall42 = new Wall(340, 430,65,5);
        Wall wall43 = new Wall(95, 315,65,5);
        Wall wall44 = new Wall(95, 315,5,115);
        Wall wall45 = new Wall(155, 315,5,115);
        Wall wall46 = new Wall(95, 430,65,5);

        //top T
        Wall wall47 = new Wall(250, 10,5,55);
        Wall wall48 = new Wall(210, 65,85,5);

        //top straight lines
        Wall wall49 = new Wall(115, 35,110,5);
        Wall wall50 = new Wall(280, 35,110,5);

        //top rects
        Wall wall51 = new Wall(320, 65,85,5);
        Wall wall52 = new Wall(320, 65,5,135);
        Wall wall53 = new Wall(400, 65,5,135);
        Wall wall54 = new Wall(320, 195,85,5);
        Wall wall55 = new Wall(95, 65,85,5);
        Wall wall56 = new Wall(95, 65,5,135);
        Wall wall57 = new Wall(180, 65,5,135);
        Wall wall58 = new Wall(95, 195,85,5);

        //left T
        Wall wall59 = new Wall(95,225,5,65);
        Wall wall60 = new Wall(95, 255, 90,5);

        //right T
        Wall wall61 = new Wall(400,225,5,65);
        Wall wall62 = new Wall(320, 255, 85,5);

        //low mid horizontal
        Wall wall63 = new Wall(125,285,250,5);

        //top mid rect
        Wall wall64 = new Wall(210, 95,80,5);
        Wall wall65 = new Wall(210, 95,5,90);
        Wall wall66 = new Wall(290, 95,5,90);
        Wall wall67 = new Wall(210, 180,80,5);

        //bottom mid rect
        Wall wall68 = new Wall(185, 315,125,5);
        Wall wall69 = new Wall(185, 315,5,90);
        Wall wall70 = new Wall(310, 315,5,90);
        Wall wall71 = new Wall(185, 400,125,5);

        //Ghost boundaries
        GhostWall ghostWall1 = new GhostWall(65,225,5,50);
        GhostWall ghostWall2 = new GhostWall(430, 225,5,50);
        GhostWall ghostWall3 = new GhostWall(240, 210, 30,5);
        GhostWall ghostWall4 = new GhostWall(234, 210, 5,50);
        GhostWall ghostWall5 = new GhostWall(264, 210, 5,50);

        //Player mid wall
        PlayerWall playerWall1 = new PlayerWall(230,210,50,5);
        PlayerWall playerWall2 = new PlayerWall(0,225,70,10);
        PlayerWall playerWall3 = new PlayerWall(0,260,70,15);
        PlayerWall playerWall4 = new PlayerWall(430,225,70,10);
        PlayerWall playerWall5 = new PlayerWall(430,260,70,15);


        walls.add(wall);
        walls.add(wall1);
        walls.add(wall2);
        walls.add(wall3);
        walls.add(wall4);
        walls.add(wall5);
        walls.add(wall6);
        walls.add(wall7);
        walls.add(wall8);
        walls.add(wall9);
        walls.add(wall10);
        walls.add(wall11);
        walls.add(wall12);
        walls.add(wall13);
        walls.add(wall14);
        walls.add(wall15);
        walls.add(wall16);
        walls.add(wall17);
        walls.add(wall18);
        walls.add(wall19);
        walls.add(wall20);
        walls.add(wall21);
        walls.add(wall22);
        walls.add(wall23);
        walls.add(wall24);
        walls.add(wall25);
        walls.add(wall26);
        walls.add(wall27);
        walls.add(wall28);
        walls.add(wall29);
        walls.add(wall30);
        walls.add(wall31);
        walls.add(wall32);
        walls.add(wall33);
        walls.add(wall34);
        walls.add(wall35);
        walls.add(wall36);
        walls.add(wall37);
        walls.add(wall38);
        walls.add(wall39);
        walls.add(wall40);
        walls.add(wall41);
        walls.add(wall42);
        walls.add(wall43);
        walls.add(wall44);
        walls.add(wall45);
        walls.add(wall46);
        walls.add(wall47);
        walls.add(wall48);
        walls.add(wall49);
        walls.add(wall50);
        walls.add(wall51);
        walls.add(wall52);
        walls.add(wall53);
        walls.add(wall54);
        walls.add(wall55);
        walls.add(wall56);
        walls.add(wall57);
        walls.add(wall58);
        walls.add(wall59);
        walls.add(wall60);
        walls.add(wall61);
        walls.add(wall62);
        walls.add(wall63);
        walls.add(wall64);
        walls.add(wall65);
        walls.add(wall66);
        walls.add(wall67);
        walls.add(wall68);
        walls.add(wall69);
        walls.add(wall70);
        walls.add(wall71);
        walls.add(wall72);
        walls.add(wall73);
        walls.add(ghostWall1);
        walls.add(ghostWall2);
        walls.add(ghostWall3);
        walls.add(ghostWall4);
        walls.add(ghostWall5);
        walls.add(playerWall1);
        walls.add(playerWall2);
        walls.add(playerWall3);
        walls.add(playerWall4);
        walls.add(playerWall5);

    }

    public void lose(){
        lose = true;
        player.setX(1000);
        player.setY(1000);
        for (int i = 0; i < ghosts.size(); i++) {
            ghosts.remove(i);
            i--;
        }
        timer.stop();
    }

    public void win(){
        win = true;
        timer.stop();
    }

}
