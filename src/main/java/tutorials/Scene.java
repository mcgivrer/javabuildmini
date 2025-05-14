package tutorials;

import java.awt.*;
import java.util.List;

public interface Scene {
    void init();

    default void update(double deltaTime) {
    }

    default void render(Graphics2D g) {
    }


    default void dispose() {
    }

    List<Entity> getEntities();

    String getName();

    World getWorld();

    void add(Entity e);

    Camera getActiveCamera();
}
