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
    public String getTitle()
    {
        String title = titleField.getText();
        if(title.isBlank()) return null;
        return title;
    }
    public String getSubtitle()
    {
        String subtitle = subtitleField.getText();
        if(subtitle.isBlank()) return null;
        return subtitle;
    }
    public ArrayList<String> getAuthors() {
        ArrayList<String> authorsList = new ArrayList<>(authorsListView.getItems());
        if (authorsList.isEmpty()) {
            return null;
        } else {
            return authorsList;
        }
    }
    public ArrayList<String> getTranslators() {
        ArrayList<String> translatorList = new ArrayList<>(translatorsListView.getItems());
        if (translatorList.isEmpty()) {
            return null;
        } else {
            return translatorList;
        }
    }

    public String getISBN()
    {
        String isbn = isbnField.getText();
        if(isbn.isBlank()) return null;
        return isbn;
    }
    public String getPublisher()
    {
        String publisher =  publisherField.getText();
        if(publisher.isBlank()) return null;
        return publisher;
    }
    public String getDate()
    {
        String date = dateField.getText();
        if(date.isBlank()) return null;
        return date;
    }
    public String getEdition()
    {
        String edition= editionField.getText();
        if(edition.isBlank()) return null;
        return edition;
    }
    public String getCover()
    {
        String cover = coverField.getText();
        if(cover.isBlank()) return null;
        return cover;
    }
    public String getLanguage()
    {
        String language = languageField.getText();
        if(language.isBlank()) return null;
        return language;
    }
    public String getRating()
    {
        String rating = ratingField.getText();
        if(rating.isBlank()) return null;
        return rating;
    }
    public ArrayList<String> getTags() {
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
        if(pageNumber.isBlank()) return null;
        return pageNumber;
    }
    public String getCoverType()
    {
        String coverType = coverTypeField.getText();
        if(coverType.isBlank()) return null;
        return coverType;
    }
}
