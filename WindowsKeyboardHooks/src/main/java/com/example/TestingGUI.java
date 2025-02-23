package com.example;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.StdCallLibrary;

import javax.swing.*;


public class TestingGUI extends JFrame {

    static boolean isLocked = false;
    static boolean isTransparent = false;
    private static int offsetX, offsetY;
    private static WinUser.HHOOK hHook;
    private static WinUser.HHOOK mHook;
    private static final User32 USER32 = User32.INSTANCE;
    private static final Kernel32 KERNEL32 = Kernel32.INSTANCE;
    private static boolean dragging = false;
    private static WinDef.HWND targetWindow;

    TestingGUI(){
        setTitle("DrawingRust");
    }

    public static void main(String[] args) {
        TestingGUI frame = new TestingGUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.Running();
    }

    // Low-level keyboard hook callback
    private final WinUser.LowLevelMouseProc mouseHook = (nCode, wParam, info) -> {
        if (nCode >= 0  ) { // 0x0201 = WM_LBUTTONDOWN

            int wmId = wParam.intValue();
            WinDef.HWND hwnd = USER32.FindWindowA(null, "DrawingRust");
            // Ensure we have a valid window handle

            if (hwnd != null && Pointer.nativeValue(hwnd.getPointer()) != 0) {
                targetWindow = hwnd; // Store the valid window handle
                WinDef.RECT rect = new WinDef.RECT();
                USER32.GetWindowRect(targetWindow, rect);

                if(!isPointInsideWindow(info.pt, rect)){
                    return null;
                }

                switch (wmId) {
                    case 513: // WM_LBUTTONDOWN (0x0201) - Start dragging if inside window
                        offsetX = info.pt.x - rect.left;
                        offsetY = info.pt.y - rect.top;
                        dragging = true;

                        break;

                    case 512: // WM_MOUSEMOVE (0x0200) - Move window if dragging
                        if (dragging) {
                            USER32.MoveWindow(targetWindow.getPointer(), info.pt.x - offsetX, info.pt.y - offsetY,
                                    rect.right - rect.left, rect.bottom - rect.top, true);
                        }
                        break;

                    case 514: // WM_LBUTTONUP (0x0202) - Stop dragging
                        dragging = false;
                        break;
                }
            }
        }
        return USER32.CallNextHookEx(mHook, nCode, wParam, new WinDef.LPARAM(info.mouseData));
    };


    private final WinUser.LowLevelKeyboardProc keyboardHook = ( nCode, wParam, info) -> {

        if (nCode >= 0 && wParam.intValue() == 0x100) { // 0x100 = WM_KEYDOWN (ignore key release)
            int vkCode = info.vkCode;


            // Exit on Escape (0x1B)
            if (vkCode == 0x1B) {
                System.out.println("Exiting program...");
                User32.INSTANCE.UnhookWindowsHookEx(hHook);
                User32.INSTANCE.UnhookWindowsHookEx(mHook);// Unhook before exit

                return new WinDef.LRESULT(1);

                // Block the key
            }

            if(vkCode == 0x5A){
                System.out.println("Z key pressed");
                return new WinDef.LRESULT(1);
            }

            // Toggle Click-Through Mode F1 Key)
            if (vkCode == 0x70) { // 0x70 = Virtual Key for 'F1'
                isLocked = !isLocked;
                toggleClickThrough(isLocked);
                System.out.println("Click-through mode: " + (isLocked ? "Enabled" : "Disabled"));
                return new WinDef.LRESULT(1);
            }

            // Toggle Transparency (F2 Key)
            if (vkCode == 0x71) { // 0x71 = Virtual Key for F2
                isTransparent = !isTransparent;
                setOpacity(isTransparent ? 0f : 0.5f);
                System.out.println("Transparency: " + (isTransparent ? "Fully Transparent" : "50% Opacity"));
                return new WinDef.LRESULT(1);
            }

            if(vkCode == 0x72){ // 0x72 = Virtual Key for F3

                //User32.INSTANCE.MoveWindow(getWindowHandle(), 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, true);
                return new WinDef.LRESULT(1);
            }
        }
        return User32.INSTANCE.CallNextHookEx(hHook, nCode, wParam, new WinDef.LPARAM(info.vkCode));

    };
    private static boolean isPointInsideWindow(WinDef.POINT pt, WinDef.RECT rect) {
        return pt.x >= rect.left && pt.x <= rect.right &&
                pt.y >= rect.top && pt.y <= rect.bottom;
    }




    public void Running(){
        // Install the keyboard hook
        hHook = USER32.SetWindowsHookExA(WinUser.WH_KEYBOARD_LL, keyboardHook, KERNEL32.GetModuleHandle(null), 0);
        mHook = USER32.SetWindowsHookExA(WinUser.WH_MOUSE_LL, mouseHook, KERNEL32.GetModuleHandle(null), 0);


        if (hHook == null) {
            System.out.println("Failed to set hook!");
            return;
        }

        System.out.println("Keyboard hook installed. Press ESC to test (it should be blocked).");

        // Windows message loop (to keep the hook active)
        WinUser.MSG msg = new WinUser.MSG();
        while ((USER32.GetMessageA(msg, null, 0, 0))) {
            USER32.TranslateMessage(msg);
            USER32.DispatchMessage(msg);
        }
    }


    // Method to toggle click-through using Windows API
    static void toggleClickThrough(boolean isLocked) {
        // Check if the platform is Windows
        if (isWindows()) {
            // Get the native window handle (HWND) of the JFrame directly via JNA
            WinDef.HWND hwnd = getWindowHandle();
            if (hwnd != null) {
                // Get current window styles
                int currentStyle = User32.INSTANCE.GetWindowLongPtrA(hwnd.getPointer(), User32.GWL_EXSTYLE);
                if (!isLocked) {

                    // If it's already in click-through mode, remove the style (undo the click-through)
                    User32.INSTANCE.SetWindowLongPtrA(hwnd.getPointer(), User32.GWL_EXSTYLE, currentStyle & User32.WS_EX_LAYERED & ~User32.WS_EX_TRANSPARENT & ~User32.WS_EX_COMPOSITED);
                } else {
                    // If it's not in click-through mode, apply the transparent and layered styles
                    User32.INSTANCE.SetWindowLongPtrA(hwnd.getPointer(), User32.GWL_EXSTYLE, currentStyle | User32.WS_EX_LAYERED | User32.WS_EX_TRANSPARENT | User32.WS_EX_COMPOSITED);
                }
            }
        }
        else {
            System.out.println("Click-through is only supported on Windows.");
        }
    }
    // Use JNA to directly get the HWND of the JFrame
    private static WinDef.HWND getWindowHandle() {
        try {
            // Call the native method to get the window handle directly via JNA
            //return User32.INSTANCE.GetForegroundWindow();
            return User32.INSTANCE.FindWindowA(null, "DrawingRust");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

        // Native method for FindWindow to retrieve the window handle by class name and window name
        WinDef.HWND FindWindowA(String lpClassName, String lpWindowName);

        WinUser.HHOOK SetWindowsHookExA(int whKeyboardLl, WinUser.LowLevelKeyboardProc keyboardHook, WinDef.HMODULE hmodule, int i);

        WinUser.HHOOK SetWindowsHookExA(int whMouseLl, WinUser.LowLevelMouseProc mouseHook, WinDef.HMODULE hmodule, int i);

        WinDef.LRESULT CallNextHookEx(WinUser.HHOOK hHook, int nCode, WinDef.WPARAM wParam, WinDef.LPARAM lparam);

        boolean GetMessageA(WinUser.MSG lpMsg, WinDef.HWND hWnd, int wMsgFilterMin, int wMsgFilterMax);

        void UnhookWindowsHookEx(WinUser.HHOOK hHook);

        void TranslateMessage(WinUser.MSG msg);

        void DispatchMessage(WinUser.MSG msg);

        boolean MoveWindow(Pointer hwnd, int x, int y, int width, int height, boolean repaint);

        void GetWindowRect(WinDef.HWND targetWindow, WinDef.RECT rect);

    }







}
