package tutorials;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static tutorials.Log.*;

public class InputHandler implements KeyListener {

    private static boolean[] keys = new boolean[1024];
    private final App app;


    public InputHandler(App app) {
        this.app = app;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        //debug(InputHandler.class, 5, "Key pressed: %d", e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> {
                info(App.class, "Exiting...");
                App.setExit(true);
            }
            case KeyEvent.VK_D -> {
                debug(App.class, 0, "Debug level set to %d", App.getDebug());
                App.setDebug(App.getDebug() + 1 > 6 ? 0 : App.getDebug() + 1);
            }
            case KeyEvent.VK_F12 -> {
                app.getRenderer().setVFX(!app.getRenderer().getVFX());
                info(InputHandler.class, "VFX settings toggled %s", app.getRenderer().getVFX() ? "ON" : "OFF");
            }
            case KeyEvent.VK_H -> {
                app.setHelpDisplay(!app.getHelpDisplay());
                info(InputHandler.class, "Help display toggles %s", app.getHelpDisplay() ? "ON" : "OFF");
            }
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return keys[keyCode];
    }

    public static void ifKeyPressed(Entity e, int keyCode, Method process, Object... obj) {
        if (isKeyPressed(keyCode)) {
            try {
                process.invoke((Object) e, obj);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                error(InputHandler.class, "Unable to process method for the key %d", keyCode);
            }
        }
    }
}
