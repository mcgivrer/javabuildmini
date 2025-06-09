package tutorials.core.behavior;

import tutorials.core.App;
import tutorials.core.entity.Entity;

public interface Behavior<T extends Entity> {

    void update(App app, T e, double deltaTime);

}
