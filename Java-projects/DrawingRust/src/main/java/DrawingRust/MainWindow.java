package DrawingRust;

import javax.swing.*;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;



public class MainWindow extends JFrame implements NativeKeyListener, NativeMouseInputListener {

    //static boolean isLocked = false;
    //static boolean isTransparent = false;



    MainWindow()  {
        this.setTitle("DrawingRust");


        /*
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

         */
    }
    public void nativeKeyPressed(NativeKeyEvent e) {
        /*
        System.out.println("Key Pressed JNativen: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }
            System.exit(0);
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_7) {
            // Toggle click-through mode when F1 is pressed
            isLocked = !isLocked;
            WindowsApiCall.toggleClickThrough(isLocked);
        }
        if(e.getKeyCode() == NativeKeyEvent.VC_F2) {
            // Toggle transparency when F2 is pressed
            isTransparent = !isTransparent;
            if(!isTransparent) {
                this.setOpacity(0.5f);
            } else {
                this.setOpacity(0f);

            }


        }
    */
    }
    public void nativeMouseClicked(NativeMouseEvent e) {
       // System.out.println("Mouse Clicked: " + e.getClickCount());
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        //System.out.println("Mouse Pressed: " + e.getButton());
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
       // System.out.println("Mouse Released: " + e.getButton());
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        //System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        //System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }




}
