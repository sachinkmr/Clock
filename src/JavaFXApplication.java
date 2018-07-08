import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;

public class JavaFXApplication extends Application {

    public static void main(String[] args) {

        Application.launch(args);
    }

    public void start(Stage primaryStage) {

        primaryStage.setTitle("AnimationTimer");

        Group root = new Group();
        Scene scene = new Scene(root, 400, 400, Color.GREY);

        Rectangle rect = new Rectangle();
        rect.setFill(Color.GHOSTWHITE);
        rect.setWidth(100);
        rect.setHeight(100);

        AnimationTimer timer = new AnimationTimer() {

            int i;
            int j;

            public void handle(long now) {

                if(rect.getBoundsInParent().getMaxX()!=400)
                {
                    rect.setTranslateX(++i);
                    rect.setTranslateY(++j);
                }
            }

        };
        timer.start();

        root.getChildren().addAll(rect);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}