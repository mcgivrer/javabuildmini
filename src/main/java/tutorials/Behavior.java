package tutorials;

public interface Behavior<T extends Entity> {

    void update(App app, T e, double deltaTime);

}
