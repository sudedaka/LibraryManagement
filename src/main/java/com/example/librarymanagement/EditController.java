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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
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
    private Book bookSelected;

    @FXML
    private ArrayList<Book> books = new ArrayList<>(); // Initialize the ArrayList
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
    private ListView<String> tagsListView;
    @FXML
    private TableView<Book> table;

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
    private void translatorsAddClick(ActionEvent event) throws IOException {
        if (!translatorsField.getText().isEmpty()) {
            translatorsListView.getItems().add(translatorsField.getText());
            translatorsField.clear();
        }
    }

    @FXML
    private void authorsAddClick(ActionEvent event) throws IOException {
        if (!authorsField.getText().isEmpty()) {
            authorsListView.getItems().add(authorsField.getText());
            authorsField.clear();
        }
    }
    @FXML
    private void tagsAddClick(ActionEvent event) throws IOException {
        if (!tagsField.getText().isEmpty()) {
            tagsListView.getItems().add(tagsField.getText());
            tagsField.clear();
        }
    }

    //deletes selected index in listview elements
    @FXML
    private void authorsRemoveClick(ActionEvent event)
    {
        int index = authorsListView.getSelectionModel().getSelectedIndex();
        if(index != -1)
        {
            authorsListView.getItems().remove(index);
        }
    }

    @FXML
    private void translatorsRemoveClick(ActionEvent event)
    {
        int index = translatorsListView.getSelectionModel().getSelectedIndex();
        if(index != -1)
        {
            translatorsListView.getItems().remove(index);
        }
    }

    @FXML
    private void tagsRemoveClick(ActionEvent event)
    {
        int index = tagsListView.getSelectionModel().getSelectedIndex();
        if(index != -1)
        {
            tagsListView.getItems().remove(index);
        }
    }

    @FXML
    public void initialize(Book book,TableView<Book>table,ArrayList<Book> books) {
        this.books=books;
        this.table=table;
        this.bookSelected =book;
        translatorsListView.setItems(translatorsList);
        authorsListView.setItems(authorsList);
        tagsListView.setItems(tagsList);
        // Place book information in text fields
        if (book != null) {
            titleField.setText(book.getTitle());
            subtitleField.setText(book.getSubtitle());
            authorsListView.setItems(FXCollections.observableArrayList(book.getAuthors()));
            translatorsListView.setItems(FXCollections.observableArrayList(book.getTranslators()));
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
    @FXML
    public void editButtonClick(ActionEvent event) {
        // update the book details with the edited values
        if (bookSelected != null) {
            int index = books.indexOf(bookSelected);
            String title= getTitle();
            String subtitle = getSubtitle();
            ArrayList<String> authors = getAuthors();
            ArrayList<String> translators = getTranslators();
            String isbn = getISBN();
            String publisher = getPublisher();
            String date = getDate();
            String edition = getEdition();
            String cover = getCover();
            String language = getLanguage();
            String rating = getRating();
            ArrayList<String> tags = getTags();
            String pageNumber = getPageNumber();
            String coverType = getCoverType();
            // creates new object with changed information then changes places with old one
            Book updatedBook = new Book(title, subtitle, authors, translators, isbn, publisher, date,
                    edition, cover, language, rating, tags, pageNumber, coverType);
            books.set(index,updatedBook);
            updateBookTableView();
            clearFields();
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
    }
    @FXML
    private void clearFields() { // Text fields will be cleared after appy button clicked
        titleField.clear();
        subtitleField.clear();
        authorsListView.getItems().clear();
        translatorsListView.getItems().clear();
        isbnField.clear();
        publisherField.clear();
        dateField.clear();
        editionField.clear();
        coverField.clear();
        languageField.clear();
        ratingField.clear();
        tagsField.clear();
        tagsListView.getItems().clear();
        pageNumberField.clear();
        coverTypeField.clear();

    }
    @FXML
    private void updateBookTableView() {

        table.setItems(FXCollections.observableArrayList(books));
    }
    //THIS CODES:  If the text is blank, it returns ; otherwise, it returns the text.
    public String getTitle() {
        String title = titleField.getText();
        return title != null ? title : ""; // Eğer title null ise, boş bir string döndür
    }
    public String getSubtitle()
    {
        String subtitle = subtitleField.getText();
        return subtitle != null ? subtitle : "";
    }

    public String getISBN()
    {
        String isbn = isbnField.getText();
        return isbn != null ? isbn: "";
    }
    public String getPublisher()
    {
        String publisher =  publisherField.getText();
        return publisher != null ? publisher : "";
    }
    public String getDate()
    {
        String date = dateField.getText();
        return date != null ? date: "";
    }
    public String getEdition()
    {
        String edition= editionField.getText();
        return edition != null ? edition : "";
    }
    public String getCover()
    {
        String cover = coverField.getText();
        return cover != null ? cover : "";
    }
    public String getLanguage()
    {
        String language = languageField.getText();
        return language != null ? language : "";
    }
    public String getRating()
    {
        String rating = ratingField.getText();
        return rating != null ? rating : "";
    }
    public ArrayList<String> getAuthors(){
        ArrayList<String> authorsList = new ArrayList<>(authorsListView.getItems());
        if (authorsList.isEmpty()) {
            return null;
        } else {
            return authorsList;
        }
    }
    public ArrayList<String> getTranslators(){
        ArrayList<String> translatorList = new ArrayList<>(translatorsListView.getItems());
        if (translatorList.isEmpty()) {
            return null;
        } else {
            return translatorList;
        }
    }

    public ArrayList<String> getTags(){
        ArrayList<String> tagsList = new ArrayList<>(tagsListView.getItems());
        if (tagsList.isEmpty()) {
            return null;
        } else {
            return tagsList;
        }
    }

    public String getPageNumber()
    {
        String pageNumber = pageNumberField.getText();
        return pageNumber != null ? pageNumber : "";
    }
    public String getCoverType()
    {
        String coverType = coverTypeField.getText();
        return coverType != null ? coverType: "";
    }
}