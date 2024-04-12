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
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.google.gson.Gson;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;

public class AddController {
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

    private TableView<Book> table;

    @FXML //This 3 ObservableList codes,represent dynamic data that can be observed for changes.
    private ObservableList<String> authorsList = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> translatorsList = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> tagsList = FXCollections.observableArrayList();
    @FXML
    private TextField translatorsField;
    @FXML
    private TextField authorsField;



    @FXML //This method, selects the parts to add and shows the url of the file.
    public void importCoverButton(ActionEvent event)
    {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg");
        fc.getExtensionFilters().add(filter);
        fc.setTitle("Select an cover image");
        Stage stage = new Stage();
        File file = fc.showOpenDialog(stage);
        if (file != null) {
            coverField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    public void initialize(TableView<Book> table, ArrayList<Book> books) {
        this.table = table;
        this.books = books; //To access the list of books.

        authorsList = FXCollections.observableArrayList();
        translatorsList = FXCollections.observableArrayList();
        tagsList = FXCollections.observableArrayList();

        authorsListView.setItems(authorsList);
        translatorsListView.setItems(translatorsList);
        tagsListView.setItems(tagsList);
    }

    @FXML //It updates the tableView in the MainWindowController
    private void updateBookTableView() {
        table.setItems(FXCollections.observableArrayList(books));
    }


    @FXML //Transferring data from Authors TextBox to ListView
    private void authorsAddClick(ActionEvent event) throws IOException {
        if (!authorsField.getText().isEmpty()) {
            authorsListView.getItems().add(authorsField.getText());
            authorsField.clear();
        }
    }

    @FXML //Transferring data from Translator TextBox to ListView
    private void translatorsAddClick(ActionEvent event) throws IOException {
        if (!translatorsField.getText().isEmpty()) {
            translatorsListView.getItems().add(translatorsField.getText());
            translatorsField.clear();
        }
    }

    @FXML //Transferring data from Tags TextBox to ListView
    private void tagsAddClick(ActionEvent event) throws IOException {
        if (!tagsField.getText().isEmpty()) {
            tagsListView.getItems().add(tagsField.getText());
            tagsField.clear();
        }
    }

    @FXML //For Authors: Deleting the selected index in ListView
    private void authorsRemoveClick(ActionEvent event)
    {
        int index = authorsListView.getSelectionModel().getSelectedIndex();
        if(index != -1)
        {
            authorsListView.getItems().remove(index);
        }
    }

    @FXML //For Translators: Deleting the selected index in ListView
    private void translatorsRemoveClick(ActionEvent event)
    {
        int index = translatorsListView.getSelectionModel().getSelectedIndex();
        if(index != -1)
        {
            translatorsListView.getItems().remove(index);
        }
    }

    @FXML //For Tags: Deleting the selected index in ListView
    private void tagsRemoveClick(ActionEvent event)
    {
        int index = tagsListView.getSelectionModel().getSelectedIndex();
        if(index != -1)
        {
            tagsListView.getItems().remove(index);
        }
    }

    //When on clicked ADD: Creates a new Book object, adds it to the books list,clear fields,updates information.
    @FXML
     private void addButtonClick(ActionEvent event)
    {
        String title = getTitle();
        String subtitle = getSubtitle();
        ArrayList<String> authors =  getAuthors();
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

        Book newBook = new Book(title, subtitle, authors, translators, isbn, publisher, date, edition, cover, language, rating, tags, pageNumber, coverType);
        books.add(newBook);
        clearFields(); // Clear all the fields.
        updateBookTableView(); //Updates the TableView information at the MainWindow.
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML // Text fields will be cleared after added button clicked
    private void clearFields() {
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

    //THIS CODES:  If the text is blank, it returns null; otherwise, it returns the text.
    public String getTitle() {

        String title = titleField.getText();

        return title != null? title : "";

    }

    public String getSubtitle()

    {

        String subtitle = subtitleField.getText();

        return subtitle != null ? subtitle : "";

    }



    public String getISBN()

    {    String isbn=isbnField.getText();

        if (isbn==null||isbn.isBlank()) {

            return "";}

        else if  (isbn.matches("\\d{13}")) {

            return isbn;

        } else {

            // If not numeric, show error message

            showErrorAlert("ISBN must be a 13-digit number.");

            return null;

        }

    }

    public String getPublisher()

    {

        String publisher =  publisherField.getText();

        if (publisher == null || publisher.isEmpty()) {

            return "";

        } else if (publisher.matches(".*\\d+.*")) {

            showErrorAlert("Publisher cannot contain numeric characters.");

            return null; // Show error message for publisher containing numeric characters

        } else {

            return publisher;

        }

    }

    public String getDate()

    {

        String date = dateField.getText();

        return date != null ? date: "";

    }

    public String getEdition()

    {

        String edition= editionField.getText();

        if (edition==null||edition.isBlank()) {

            return "";}

        else if  (edition.matches("\\d+")) {

            return edition;

        } else {

            // If not numeric, show error message

            showErrorAlert("Edition must be numeric.");

            return null;

        }

    }

    public String getCover()

    {

        String cover = coverField.getText();

        return cover != null ? cover : "";

    }

    public String getLanguage()

    {

        String language = languageField.getText();

        if (language == null || language.isEmpty()) {

            return ""; // Return empty string for null or empty title

        } else if (language.matches(".*\\d+.*")) {

            showErrorAlert("Language cannot contain numeric characters.");

            return null; // Show error message for titles containing numeric characters

        } else {

            return language;

        }

    }

    public String getRating()

    {

        String rating = ratingField.getText();

        if (rating == null||rating.isBlank()) {

            return "";}

        else if (rating.matches("\\d+")) {

            return rating; // If input is integer

        } else if (rating.matches("\\d+\\.\\d+")) {

            return rating; // If input is double

        }

        else{

            showErrorAlert("Rating must be numeric.");

            return null;

        }

    }

    public ArrayList<String> getAuthors() {

        ArrayList<String> authorList = new ArrayList<>(authorsListView.getItems());

        if (authorList.isEmpty()) {

            return null; //

        } else {

            for (String author : authorList) {

                if (author.matches(".*\\d+.*")) {

                    showErrorAlert("Author names cannot contain numeric characters.");

                    return null; // Show error message and return null if any author name contains numeric characters

                }

            }

            return authorList;

        }

    }



    public ArrayList<String> getTranslators() {

        ArrayList<String> translatorList = new ArrayList<>(translatorsListView.getItems());

        if (translatorList.isEmpty()) {

            return null;

        } else {

            for (String translator : translatorList) {

                if (translator.matches(".*\\d+.*")) {

                    showErrorAlert("Translator names cannot contain numeric characters.");

                    return null; // Show error message and return null if any translator name contains numeric characters

                }

            }

            return translatorList; // Return the list if all translator names are valid

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

        if (pageNumber==null||pageNumber.isBlank()) {

            return "";}

        else if  (pageNumber.matches("\\d+")) {

            return pageNumber;

        } else {

            // If not numeric, show error message

            showErrorAlert("Page Number must be numeric.");

            return null;

        }

    }

    public String getCoverType()

    {

        String coverType = coverTypeField.getText();

        return coverType != null ? coverType: "";

    }

    private void showErrorAlert(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Input Error");

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();

    }
}
