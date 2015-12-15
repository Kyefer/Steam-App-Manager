package com.kyefer.manager.view;

import com.kyefer.manager.model.SteamProfile;
import javafx.fxml.FXML;
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
    private SteamProfile profile;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk(){
        if (isInputValid()){
            //profile.setSteamID(steamIDField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    public void setProfile(SteamProfile profile){
        this.profile = profile;
//        if (profile.getSteamID().equals(SteamProfile.EMPTY_PROFILE)){
//            steamIDField.setDisable(true);
//        }
    }

    private boolean isInputValid(){
        return steamIDField.getText().matches("[0-9]{17}");
    }
}
