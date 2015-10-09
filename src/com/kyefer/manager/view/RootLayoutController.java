package com.kyefer.manager.view;

import com.kyefer.manager.Main;
import com.kyefer.manager.model.Profile;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Eddie on 10/7/2015.
 */
public class RootLayoutController {

    @FXML
    private BorderPane rootLayout;

    private Profile profile;
    private GameOverviewController gameOverviewProfile;
    private GenreOverviewController genreOverviewProfile;

    @FXML
    private void initialize() {

        //sven = 76561198059300359
        //eddie = 76561198051950539
        //dan = 76561198051501091
        String defaultID = "76561198059300359";
        profile = new Profile();
        profile.setSteamID(defaultID);
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    @FXML
    private void handleNewProfile() {
        Profile newProfile = new Profile();
        if (updateProfile(newProfile)) {
            gameOverviewProfile.setProfile(newProfile);
            genreOverviewProfile.setProfile(newProfile);
            profile = newProfile;
        }
    }

    private boolean updateProfile(Profile profile) {

        TextInputDialog dialog = new TextInputDialog("steamid");
        dialog.setTitle("New Profile");
        dialog.setHeaderText(null);
        dialog.setContentText("Steam ID:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get().matches("[0-9]{17}")) {
            profile.setSteamID(result.get());
            return true;
        }
        return false;/*
        try {


            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ProfileRetriever.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Profile");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ProfileRetrieverController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setProfile(profile);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }*/

    }

    public void showGameOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/GameOverview.fxml"));
            AnchorPane profileOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(profileOverview);

            // Give the controller access to the main app.
            gameOverviewProfile = loader.getController();
            gameOverviewProfile.setProfile(profile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showGenreOverview(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/GenreOverview.fxml"));
            AnchorPane genreOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(genreOverview);

            // Give the controller access to the main app.
            genreOverviewProfile = loader.getController();
            genreOverviewProfile.setProfile(profile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
