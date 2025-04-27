package tutorials;

import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class App {
  public static ResourceBundle messages = ResourceBundle.getBundle("i18n/messages.properties");
  
  public void App(){
    info(App.class, "Stat the application %s",messages.getString("app.title"));
  }
  
  public void run(String[] args){
  }
  
  public static void main(String[] args){
    App app = new App();
    app.run(args);
  }

  public static void log(Class cls, String level, String message, Object... args){
    System.out.printf("%s;%s;%s;%s%f", ZonedDateTime.now(), cls.getCanonicalName(),level, message.formatted(args));
  }

  public static void info(Class cls, String message, Object... args){
    log(cls,"INFO",message,args);
  }
    
}
