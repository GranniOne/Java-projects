package AutoClicker;

import java.awt.*;

import com.github.kwhat.jnativehook.*;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class Main {
    static Robot robot;

    public static void main(String[] args) throws InterruptedException, NativeHookException {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(listener);
        Thread.sleep(5000);

        /*
        try {
            robot = new Robot();
            while (true) {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(10);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }

         */

    }
    static NativeKeyListener listener = new NativeKeyListener() {
        @Override
        public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
            NativeKeyListener.super.nativeKeyPressed(nativeEvent);

            if(nativeEvent.getKeyCode() == NativeKeyEvent.VC_ESCAPE){
                System.out.println("Exiting...");
                System.exit(0);

            }
        }
    };


}