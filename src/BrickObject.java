public class BrickObject {
    private int x, y; // Position
    private int width, height; // Size
    private int dx, dy; // Velocity

    public BrickObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public boolean collidesWith(BrickObject other) {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y;
    }

    // Getters and setters omitted for brevity

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getY() {
        return y;
    }

}