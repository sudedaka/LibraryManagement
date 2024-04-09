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


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

public class AddController {
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
    public void importCoverButton()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            importFromJson(file.getAbsolutePath());
        }
    }

    @FXML
    public void initialize() {
        translatorsListView.setItems(translatorsList);
        authorsListView.setItems(authorsList);
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

    private void addButton(ActionEvent event)
    {
        String title = titleField.getText();
        String subtitle = subtitleField.getText();
        ArrayList<String> authors = new ArrayList<>(authorsListView.getItems());
        ArrayList<String> translators = new ArrayList<>(translatorsListView.getItems());
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

    private void clearFields() { // Text fields will be cleared after added button clicked
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
        pageNumberField.clear();
        coverTypeField.clear();
    }
    private void updateBookListView() {
        bookListView.getItems().setAll(books);
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
    public List<String> getAuthors() {
        List<String> authorsList = authorsListView.getItems();
        if (authorsList.isEmpty()) {
            return null;
        } else {
            return authorsList;
        }
    }
    public List<String> getTranslators() {
        List<String> translatorList = translatorsListView.getItems();
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
    public List<String> getTags() {
        List<String> tagsList = tagsListView.getItems();
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
