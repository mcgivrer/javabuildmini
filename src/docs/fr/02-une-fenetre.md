# Ajout d'une fenetre à notre application

Afin de commencer à créer un programme offrant une interactivité avec l'utilisteur,
nous allons ajouter une fenêtre à notre programme.

Pour cela, nous utiliserons la classe `JFrame` issue du JDK.

Nous ajouterons un buffer de rendu sur 3 niveau (`BufferStrategy`) et l'intégration
de la gestion des évennements clavier via une implementation de l'interface
`KeyListener`.

La gestion des évenement clavier sera déléguée à la classe `App` elle-même, en
icluant un buffer contenant l'état courant de chaqune des touches du clavier.

Ce buffer sera alors intérrogeable à n'importe quel moment pour en connaitre l'état.

## Création de la fenêtre

La fenêtre est créée avec des parametre venant de la configuration (fichier config.properties)
ou du fichier de traduction (i18n/messages.propoerties) :

| Nom    | Description                | Configuration      | I18n             | Valeur par défaut     |
|:-------|:---------------------------|:-------------------|:-----------------|:----------------------|
| size   | taille de la fenetre       | app.window.size    | N/A              | "720x460"             |
| title  | titre de la fenetre        | N/A                | app.window.title | "Application (0.0.1)" |
| buffer | Nombre de  buffer de rendu | app.window.buffers | N/A              | "3"                   |

Ainsi, la taille de la fenêtre et le nombre de buffers de rendu sont configurables et peuvent
être ajustés en fonction des besoins de l'application.

### Initialisation

Le code ci-dessous illustre une possible implémentation du chargement de la traduction et de la configuration:

```java
public class App implements KeyListener{
    private static final ResourceBundle messages;
    private static final Properties config = new Properties();
    public App(){
        initialization();
    }

    private void initialization() {
        // Load configuration properties
        try {
            config.load(App.class.getClassLoader().getResourceAsStream("config.properties"));
            messages = ResourceBundle.getBundle("i18n/messages", Locale.getDefault());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
```

### Valeur de configuration

La méthode ci-dessous permet la récupération des valeurs de configuration.  Elle convertit les chaines de caractères issues de la configuration en objets Java correspondant.

```java
    private <T> T getConfig(String key, T defaultValue) {
        switch(key){
            case "app.window.size" -> {
                String[] parts = getConfig("app.window.size").split("x");
                return (T) new Dimension(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            }
            case "app.window.buffers" -> {
                return (T) Integer.valueOf(config.getProperty("app.window.buffers"));
            }
            default->return null;
        }
    }
```

### Fenêtre

Le code de création de la fenêtre est le suivant. Il est important de noter que la fenêtre devra provoquer la fin de l'application si elle est fermée.

```java
public class App implements KeyListener{
    //...
        public App(){
        initialization();
        createWindow();
    }
    //...
    private void createWindow() {
        JFrame frame = new JFrame(messages.getString("app.window.title"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(getConfig("app.window.size", new Dimension(720, 460)));
        frame.setVisible(true);
        frame.createBufferStrategy(getConfig("app.window.buffers", 3));
    }
    //...
}
```

### Gestion des événements clavier

Afin de traiter les événements issues de actions sur les touches du clavier, nous allons implémenter les méthodes issue de l'interface KeyListener:

```java
public class App implements KeyListener{
    //...
    private boolean[] keys = new boolean[1024];

    //...
    private void createWindow() {
        JFrame frame = new JFrame(messages.getString("app.window.title"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(getConfig("app.window.size"));
        frame.addKeyListener(this);
        //...
    }

    //...
    @Override
    public void keyTyped(KeyEvent e) {
        // rien a faire dans ce cas précis
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        onKeyReleased(e);
    }

    public boolean isKeyPressed(int keyCode) {
        return keys[keyCode];
    }
    //...
}
```
