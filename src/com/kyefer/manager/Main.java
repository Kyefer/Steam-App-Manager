package com.kyefer.manager;

import com.kyefer.manager.model.Game;
import com.kyefer.manager.view.GameOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Steam App Manager");

        initRootLayout();

        showGameOverview();

    }

    private void showGameOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/GameOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            GameOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRootLayout() {try {
        // Load root layout from fxml file.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }


    public ObservableList<Game> getPersonData() {
        ObservableList<Game> gameList = FXCollections.observableArrayList();
        Game tf2 = new Game("Team Fortress 2");
        tf2.addGenre("Multiplayer");
        tf2.addGenre("Free To Play");
        tf2.addGenre("Shooter");
        gameList.add(tf2);
        return gameList;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
