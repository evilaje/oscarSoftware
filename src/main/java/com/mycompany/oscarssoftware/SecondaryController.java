package com.mycompany.oscarssoftware;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("menu", 780, 460);
    }
}