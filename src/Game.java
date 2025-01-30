import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    public List<BrickObject> BrickCollection = new ArrayList<>();
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    public Game() {
        Dimension ScreenSize = toolkit.getScreenSize();
        int width = (int) (ScreenSize.getWidth()-60)/26;
        int height = (int) ScreenSize.getHeight()/26;
        for(int i = 0; i < 26; i++) {
            for(int j = 0; j <10; j++) {
                addBrick((i * width)+30, (j * height)+40, width-5, height-5);
            }

        }

    }

    public void addBrick(int x, int y, int width, int height) {
        BrickCollection.add(new BrickObject(x, y, width, height));
    }

}