package editorScreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PopUpController implements Initializable {
    @FXML
    AnchorPane wumpusButton, pitButton, goldButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wumpusButton.setId("wumpusButton");
        pitButton.setId("pitButton");
        goldButton.setId("goldButton");
    }
}
