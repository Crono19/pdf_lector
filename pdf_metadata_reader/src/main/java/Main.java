import com.example.pdf_metadata_reader.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class Main extends Application{
    @Override
    // public void start(Stage primaryStage) throws Exception {
       // Parent root = FXMLLoader.load(getClass().getResource("main_window.fxml"));
        //primaryStage.setTitle("Metadata Reader");
        // primaryStage.setScene(new Scene(root));
        //primaryStage.show();
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main_window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PDF Metadata Reader");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
