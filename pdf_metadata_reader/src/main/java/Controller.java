import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class Controller {
    public void onExitButtonClicked(MouseEvent event){
        Platform.exit();
        System.exit(0);
    }

    public void load_files_window(MouseEvent event){

    }

    public void uploaded_files_window(MouseEvent event){

    }


}