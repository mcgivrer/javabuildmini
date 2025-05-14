package tutorials;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AbstractScene implements Scene {


    private static List<Scene> scenes = new ArrayList<>();
    protected final App app;
    protected World world = new World();

    private String name = "default";
    private static Scene currentScene;

    private List<Entity> entities = new ArrayList<>();
    private Camera activeCamera;


    public AbstractScene(App app, String name) {
        this.app = app;
        this.name = name;
    }

    public static void loadScenes(App app, Configuration config) {
        String[] sceneList = config.getStrings("app.scene.list", ",", "default:tutorials.scene.DefaultScene");
        for (String scene : sceneList) {
            try {
                String[] parts = scene.split(":");
                Class<?> cls = Class.forName(parts[1]);
                Scene s = (Scene) cls.getDeclaredConstructor(App.class, String.class).newInstance(app, parts[0]);
                scenes.add(s);
            } catch (Exception e) {
                Log.error(AbstractScene.class, "Unable to load scene %s: %s", scene, e.getMessage());
            }
        }
        setCurrentScene(getScene(config.getString("app.scene.default", "default")));
    }

    public static Scene getScene(String name) {
        for (Scene s : scenes) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        Log.error(AbstractScene.class, "Unable to get scene %s", name);
        return null;
    }

    public static void initScene() {
        currentScene.add(currentScene.getWorld());
        currentScene.init();
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static void setCurrentScene(Scene s) {
        currentScene = s;
    }

    @Override
    public void init() {

    }

    public void add(Entity e) {
        if (e instanceof Camera c) {
            if (Optional.ofNullable(getActiveCamera()).isEmpty()) {
                setActiveCamera(c);
            }
        }
        entities.add(e);
    }


    @Override
    public void dispose() {

    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(Graphics2D g) {

    }


    public List<Entity> getEntities() {
        return entities;
    }

    public String getName() {
        return name;
    }

    public World getWorld() {
        return world;
    }

    private void setActiveCamera(Camera c) {
        activeCamera = c;
    }

    @Override
    public Camera getActiveCamera() {
        return activeCamera;
    }

}
