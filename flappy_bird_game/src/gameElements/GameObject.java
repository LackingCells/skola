package gameElements;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
/**
 The abstract class GameObject represents any object in the game that can be drawn on the screen and checked for collisions.
 This class provides common properties and behaviors for game objects, such as position, size, speed, image, collision detection, and drawing.
 @author leodi727 eliba473
 */
public abstract class GameObject {
    protected final int width, height;
    protected int x, y;
    protected float speedX, speedY;
    protected Image img;
    private boolean hasCollided = false;

    public GameObject(int x, int y, int width, int height, String asset){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        try {
            img = new Image(new FileInputStream(asset));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public boolean checkCollision(GameObject object) {
        Rectangle2D rect1 = new Rectangle2D(x, y, width, height);
        Rectangle2D rect2 = new Rectangle2D(object.x, object.y, object.width, object.height);
        return rect1.intersects(rect2);
    }

    public void draw(GraphicsContext g){
        g.drawImage(img,x,y,width,height);
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public abstract void update();

    public int getHeight() {
        return height;
    }

    public void setHasCollided(){
        hasCollided = true;
    }

    public boolean hasCollided(){
        return hasCollided;
    }
}
