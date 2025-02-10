import com.sun.jna.*;
import com.sun.jna.win32.StdCallLibrary;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static JFrame frame = new JFrame("Click-Through Example");
    static boolean isLocked = false;
    static boolean isTransparent = false;

    public static void main(String[] args) {
        // Set up the JFrame
        frame.setUndecorated(true);
        frame.setOpacity(0.5f); // Set transparency
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(new ImagePanel());
        frame.setAlwaysOnTop(true);

        // Add MouseListener to handle dragging
        Point initialClick = new Point();
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(isLocked) return;
                initialClick.setLocation(e.getPoint());
            }
        });
        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(isLocked) return;
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;
                int newX = thisX + e.getX() - initialClick.x;
                int newY = thisY + e.getY() - initialClick.y;
                frame.setLocation(newX, newY);
            }
        });
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    // Toggle click-through mode when F1 is pressed
                    isLocked = !isLocked;
                    toggleClickThrough();
                }
                if(e.getKeyCode() == KeyEvent.VK_F2) {
                    // Toggle transparency when F2 is pressed
                    isTransparent = !isTransparent;
                    if(!isTransparent) {
                        frame.setOpacity(0.5f);
                    } else {
                        frame.setOpacity(0f);
                    }
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to toggle click-through using Windows API
    private static void toggleClickThrough() {
        // Check if the platform is Windows
        if (isWindows()) {
            // Get the native window handle (HWND) of the JFrame directly via JNA
            Pointer hwnd = getWindowHandle(frame);

            if (hwnd != null) {
                // Get current window styles
                int currentStyle = User32.INSTANCE.GetWindowLongPtrA(hwnd, User32.GWL_EXSTYLE);

                if (!isLocked) {
                    // If it's already in click-through mode, remove the style (undo the click-through)
                    User32.INSTANCE.SetWindowLongPtrA(hwnd, User32.GWL_EXSTYLE, currentStyle & User32.WS_EX_LAYERED & ~User32.WS_EX_TRANSPARENT & ~User32.WS_EX_COMPOSITED);
                    System.out.println("Window is no longer click-through.");
                } else {
                    // If it's not in click-through mode, apply the transparent and layered styles
                    User32.INSTANCE.SetWindowLongPtrA(hwnd, User32.GWL_EXSTYLE, currentStyle | User32.WS_EX_LAYERED | User32.WS_EX_TRANSPARENT | User32.WS_EX_COMPOSITED);
                    System.out.println("Window is now click-through and transparent with compositing.");
                }
            }
        } else {
            System.out.println("This functionality is only supported on Windows.");
        }
    }


    // Use JNA to directly get the HWND of the JFrame
    private static Pointer getWindowHandle(JFrame frame) {
        try {
            // Call the native method to get the window handle directly via JNA
            return User32.INSTANCE.GetForegroundWindow();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Check if the current platform is Windows
    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    // JNA interface for User32 DLL methods
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = Native.load("user32", User32.class);
        int GWL_EXSTYLE = -20;
        int WS_EX_LAYERED = 0x80000;
        int WS_EX_TRANSPARENT = 0x20;
        int WS_EX_COMPOSITED = 0x02000000;

        // Native method for GetWindowLongPtr (Windows 64-bit)
        int GetWindowLongPtrA(Pointer hwnd, int nIndex);
        // Native method for SetWindowLongPtr
        int SetWindowLongPtrA(Pointer hwnd, int nIndex, int dwNewLong);

        // Native method for SetLayeredWindowAttributes
        boolean SetLayeredWindowAttributes(Pointer hwnd, int crKey, byte bAlpha, int dwFlags);

        // Native method for GetForegroundWindow to retrieve the current window handle
        Pointer GetForegroundWindow();
    }
}
