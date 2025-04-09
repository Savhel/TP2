package com.example.poo_tp1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFrmApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFrmApplication.class.getResource("MainFrmApplication.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Contact Manager!");

        // Désactiver le redimensionnement
        stage.setResizable(false);

        // Optionnel : Fixer la taille minimale et maximale à la taille initiale
        stage.setMinWidth(600);
        stage.setMinHeight(610);
        stage.setMaxWidth(600);
        stage.setMaxHeight(630);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}