import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;


public class SuperGlide implements NativeKeyListener, NativeMouseListener {

    private static Robot robot;

    public static void main(String[] args) {
        try {
            // Initialize Robot for simulating key presses
            robot = new Robot();
            SuperGlide superGlide = new SuperGlide();
            // Register the global key listener
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(superGlide);
            GlobalScreen.addNativeMouseListener(superGlide);

            System.out.println("Global key listener registered. Press F8 to trigger the macro!");

        } catch (AWTException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        // Trigger the macro when F8 is pressed
        if (e.getKeyCode() == NativeKeyEvent.VC_V) {
            System.out.println("F8 key detected, running macro...");
            runMacro();
        }
        if(e.getKeyCode() == NativeKeyEvent.VC_U){
            System.exit(0);
        }
    }
    public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
        System.out.println("Mouse Clicked: " + nativeEvent.getButton());
    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // Optional: Handle key release if needed
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyCode());
        // Optional: Handle key typing if needed
    }

    private static void runMacro() {
        try {
            long startTime = System.nanoTime();

            // Trigger the Space key
            triggerKey(robot, KeyEvent.VK_SPACE, startTime);
            // Wait for 6 milliseconds
            long targetTime = startTime + 6_944_444; // 6 milliseconds in nanoseconds
            while (System.nanoTime() < targetTime) {
                // Busy-wait
            }
            triggerKey(robot, KeyEvent.VK_CONTROL, System.nanoTime());
            System.out.println("Ctrl key triggered");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void triggerKey(Robot robot, int keyCode, long time) {
        System.out.println("Triggering key at time: " + time);
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }
}
