package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The javadoc can be found in the javadoc folder provided in the
 * zip.
 * <p>
 * FUTURE ENHANCEMENTS the application currently does not save
 * any of the inventory that is entered but this feature could be
 * added.
 * </p>
 *
 * @author Tabish Abbasi
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 470);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

