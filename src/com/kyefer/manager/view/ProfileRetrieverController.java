package com.kyefer.manager.view;

import com.kyefer.manager.model.Profile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Eddie on 10/7/2015.
 */
public class ProfileRetrieverController {

    @FXML
    private TextField steamIDField;

    private Stage dialogStage;
    private boolean okClicked = false;
    private Profile profile;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk(){
        if (isInputValid()){
            profile.setSteamID(steamIDField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    public void setProfile(Profile profile){
        this.profile = profile;
    }

    private boolean isInputValid(){
        return steamIDField.getText().matches("[0-9]{17}");
    }
}
