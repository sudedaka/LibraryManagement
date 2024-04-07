package com.example.librarymanagement;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class HelloController {

    private ArrayList<Book> books;
    @FXML
    private TextField titleField;
    @FXML
    private TextField subtitleField;
    @FXML
    private ListView<String> authorsListView;
    @FXML
    private ListView<String> translatorsLView;
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
    private void importButton(ActionEvent event) { //to choose file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            importFromJson(file.getAbsolutePath());
        }
    }
    private void exportButton(Stage stage) {
        Gson gson = new Gson();
        FileChooser file = new FileChooser();
        file.setInitialFileName("library.json");
        FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("File", "*.json");
        file.getExtensionFilters().add(fileExtensions);
        file.setTitle("Choose to save!");
        File f = file.showSaveDialog(stage);
        exportToJson(f.getAbsolutePath());
    }

    private void addButton(ActionEvent event)
    {
        String title = titleField.getText();
        String subtitle = subtitleField.getText();
        ArrayList<String> authors = new ArrayList<>(authorsListView.getItems());
        ArrayList<String> translators = new ArrayList<>(translatorsLView.getItems());
        String isbn = isbnField.getText();
        String publisher = publisherField.getText();
        String date = dateField.getText();
        String edition = editionField.getText();
        String cover = coverField.getText();
        String language = languageField.getText();
        String rating = ratingField.getText();
        ArrayList<String> tags = new ArrayList<>(Arrays.asList(tagsField.getText().split(",")));
        String pageNumber = pageNumberField.getText();
        String coverType = coverTypeField.getText();

        Book newBook = new Book(title, subtitle, authors, translators, isbn, publisher, date, edition, cover, language, rating, tags, pageNumber, coverType);
        books.add(newBook);
        clearFields();
        updateBookListView();
    }

    private void clearFields() {
        titleField.clear();
        subtitleField.clear();
        authorsListView.getItems().clear();
        translatorsLView.getItems().clear();
        isbnField.clear();
        publisherField.clear();
        dateField.clear();
        editionField.clear();
        coverField.clear();
        languageField.clear();
        ratingField.clear();
        tagsField.clear();
        pageNumberField.clear();
        coverTypeField.clear();
    }
    private void updateBookListView() {
        bookListView.getItems().setAll(books);
    }

    @FXML
    public void onClick(ActionEvent event)
    {
        String title = titleField.getText();
        String subtitle = subtitleField.getText();
        ArrayList<String> authors = new ArrayList<>(authorsListView.getItems());
        ArrayList<String> translators = new ArrayList<>(translatorsLView.getItems());
        String isbn = isbnField.getText();
        String publisher = publisherField.getText();
        String date = dateField.getText();
        String edition = editionField.getText();
        String cover = coverField.getText();
        String language = languageField.getText();
        String rating = ratingField.getText();
        ArrayList<String> tags = new ArrayList<>(Arrays.asList(tagsField.getText().split(",")));
        String pageNumber = pageNumberField.getText();
        String coverType = coverTypeField.getText();

        Book newBook = new Book(title, subtitle, authors, translators, isbn, publisher, date, edition, cover, language, rating, tags, pageNumber, coverType);
        books.add(newBook);
        clearFields();
        updateBookListView();
    }
}
