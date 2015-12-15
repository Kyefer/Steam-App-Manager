package com.kyefer.manager.view;

import com.kyefer.manager.Main;
import com.kyefer.manager.model.SteamProfile;
import com.kyefer.manager.util.SteamUtil;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Eddie on 10/7/2015.
 */
public class RootLayoutController {

    @FXML
    private BorderPane rootLayout;

    private SteamProfile profile;
    private GameOverviewController gameOverviewController;
    private GenreOverviewController genreOverviewController;
    private Pane genreOverview;
    private Pane profileOverview;

    @FXML
    private void initialize() {

//        String defaultID = "";
//        profile = new SteamProfile(defaultID);

        profile = null;
        try {
            FXMLLoader gameLoader = new FXMLLoader();
            gameLoader.setLocation(Main.class.getResource("view/GenreOverview.fxml"));
            genreOverview = (AnchorPane) gameLoader.load();
            genreOverviewController = gameLoader.getController();
            genreOverviewController.loadProfile(profile);


            FXMLLoader genreLoader = new FXMLLoader();
            genreLoader.setLocation(Main.class.getResource("view/GameOverview.fxml"));
            profileOverview = (AnchorPane) genreLoader.load();
            gameOverviewController = genreLoader.getController();
            gameOverviewController.loadProfile(profile);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    @FXML
    private void handleNewProfile() {
        TextInputDialog dialog = new TextInputDialog("steamid");
        dialog.setTitle("New SteamProfile");
        dialog.setHeaderText(null);
        dialog.setContentText("Steam ID:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get().matches("\\d{17}")) {
            SteamProfile newProfile = new SteamProfile(result.get());

            gameOverviewController.startLoading();
            genreOverviewController.startLoading();

            new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            SteamUtil.loadGames(newProfile);
                            Platform.runLater(()-> gameOverviewController.loadProfile(newProfile));
                            Platform.runLater(()-> genreOverviewController.loadProfile(newProfile));
                            return null;
                        }
                    };
                }
            }.start();
            profile = newProfile;
        }
    }

    private boolean updateProfile(SteamProfile profile) {
        return false;

/*
        TextInputDialog dialog = new TextInputDialog("steamid");
        dialog.setTitle("New SteamProfile");
        dialog.setHeaderText(null);
        dialog.setContentText("Steam ID:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get().matches("[0-9]{17}")) {
            profile.setSteamID(result.get());
            return true;
        }
        return false;
        try {


            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ProfileRetriever.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New SteamProfile");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ProfileRetrieverController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.loadProfile(profile);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }*/

    }

    public void showGameOverview() {
        rootLayout.setCenter(profileOverview);
    }

    public void showGenreOverview() {

        rootLayout.setCenter(genreOverview);

    }
}
