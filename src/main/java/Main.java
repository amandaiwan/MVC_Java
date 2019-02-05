import controller.ClockController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.service.ClockService;
import view.ClockView;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {


        ClockService model = new ClockService();
        ClockView view = new ClockView();

        ClockController controller = new ClockController(model, view);

        primaryStage.setTitle("Clock");
        Scene scene = new Scene(controller.getView(), 500, 500);

        view.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        primaryStage.setScene(scene);
        primaryStage.show();

    }

}

