package com.example.librarymanagement;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.google.gson.Gson;

import java.io.*;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.fxml.FXML;


public class MainWindowController extends Application {


    @FXML
    private ArrayList<Book> books;
    @FXML
    private TextField translatorsField;
    @FXML
    private ListView<String> translatorsListView;
    private ObservableList<String> translatorsList = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Library Management");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {


        launch();
    }


    @FXML
    public void addButtonClick(ActionEvent event) throws IOException
    {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("add.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Add Screen");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }


    @FXML

    public void importFromJson(String filePath) {   // Method to read data from a JSON file
        try {
            Gson gson = new Gson();
            try (Reader reader = new FileReader(filePath)) {
                books = gson.fromJson(reader, new TypeToken<ArrayList<Book>>() {
                }.getType());  // Add data read from JSON file to the books list
                System.out.println("Books imported from JSON .");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML

    public void importButton(ActionEvent event) throws IOException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            importFromJson(file.getAbsolutePath());  // Read data from the file
        }
    }
    @FXML
    public void exportToJson(String path) {
        try {
            Gson gson = new Gson();
            String jsonFormat = gson.toJson(books);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                writer.write(jsonFormat);
            } catch (IOException e) {
                System.err.println("An error occurred while writing to the file: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
    @FXML
    private void exportButton(ActionEvent event) {

        Gson gson = new Gson();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser file = new FileChooser();
        file.setInitialFileName("library.json");
        FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("File", "*.json");
        file.getExtensionFilters().add(fileExtensions);
        file.setTitle("Choose to save!");
        File f = file.showSaveDialog(stage);
        exportToJson(f.getAbsolutePath());   // Write data in JSON format to the selected file
    }



    //COVER IMPORT BUTTON FUNCTION RUNNING
    @FXML
    public void importCoverButton()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            importFromJson(file.getAbsolutePath());
        }
    }
}

