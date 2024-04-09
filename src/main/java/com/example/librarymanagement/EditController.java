package com.example.librarymanagement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.google.gson.Gson;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
public class EditController {
    @FXML
    private Book book;

    @FXML
    private ArrayList<Book> books;
    @FXML
    private TextField titleField;
    @FXML
    private TextField subtitleField;
    @FXML
    private ListView<String> authorsListView;
    @FXML
    private ListView<String> translatorsListView;
    @FXML
    private TextField isbnField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField editionField;
    @FXML
    private TextField coverField;
    @FXML
    private TextField languageField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField tagsField;
    @FXML
    private TextField pageNumberField;
    @FXML
    private TextField coverTypeField;
    @FXML
    private ListView<Book> bookListView;
    @FXML
    private ListView<String> tagsListView;

    @FXML
    private ObservableList<String> translatorsList = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> authorsList = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> tagsList = FXCollections.observableArrayList();
    @FXML
    private TextField translatorsField;
    @FXML
    private TextField authorsField;

    @FXML
    public void importFromJson(String filePath) {
        try {
            Gson gson = new Gson();
            try (Reader reader = new FileReader(filePath)) {
                books = gson.fromJson(reader, new TypeToken<ArrayList<Book>>() {
                }.getType());
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

    //COVER IMPORT BUTTON FUNCTION RUNNING
    @FXML
    public void importCoverButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            importFromJson(file.getAbsolutePath());
        }
    }

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
    public void setBook(Book book) {
        this.book = book;
        // Kitap bilgilerini metin alanlarına yerleştir
        if (book != null) {
            titleField.setText(book.getTitle());
            subtitleField.setText(book.getSubtitle());
            authorsListView.setItems(FXCollections.observableArrayList(book.getAuthors()));
            translatorsListView.setItems(FXCollections.observableArrayList(book.getAuthors()));
            publisherField.setText(book.getPublisher());
            isbnField.setText(book.getIsbn());
            publisherField.setText(book.getPublisher());
            dateField.setText(book.getDate());
            editionField.setText(book.getEdition());
            coverField.setText(book.getCover());
            languageField.setText(book.getLanguage());
            ratingField.setText(book.getRating());
            tagsListView.setItems(FXCollections.observableArrayList(book.getTags()));
            pageNumberField.setText(book.getNumberofPages());
            coverTypeField.setText(book.getCoverType());
        }
    }
}