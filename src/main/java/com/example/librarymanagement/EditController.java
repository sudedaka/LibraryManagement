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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.util.converter.IntegerStringConverter;

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
    private DatePicker datePickerField;

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
            if(book.getAuthors()!=null){
                authorsListView.setItems(FXCollections.observableArrayList(book.getAuthors()));
            }
            if(book.getTranslators()!=null) {
                translatorsListView.setItems(FXCollections.observableArrayList(book.getTranslators()));
            }
            isbnField.setText(book.getIsbn());
            publisherField.setText(book.getPublisher());
            if (book.getDate() != null && !book.getDate().isEmpty()) {
                LocalDate date = LocalDate.parse(book.getDate());
                datePickerField.setValue(date);
            }
            editionField.setText(book.getEdition());
            coverField.setText(book.getCover());
            languageField.setText(book.getLanguage());
            ratingField.setText(book.getRating());
            if(book.getTags()!=null) {
                tagsListView.setItems(FXCollections.observableArrayList(book.getTags()));
            }
            pageNumberField.setText(book.getNumberofPages());
            coverTypeField.setText(book.getCoverType());

        }

        isbnField.textProperty().addListener((observable, oldValue, newValue) -> {
            // If the input value from the user is not a digit or exceeds 13 characters
            if (!newValue.matches("\\d+") || newValue.length() > 13) {
                // Remove any non-digit characters and limit to 13 characters
                String formattedISBN = newValue.replaceAll("[^\\d]", "");
                if (formattedISBN.length() > 13) {
                    formattedISBN = formattedISBN.substring(0, 13);
                }
                // Add "-" after every four characters
                StringBuilder formattedValue = new StringBuilder();
                for (int i = 0; i < formattedISBN.length(); i++) {
                    if (i > 0 && i % 4 == 0) {
                        formattedValue.append("-");
                    }
                    formattedValue.append(formattedISBN.charAt(i));
                }
                isbnField.setText(formattedValue.toString());
            }
        });

    }

    private boolean isISBNUnique(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return false; //Returns false if the ISBN already exists in the list
            }
        }
        return true; // Returns true if the ISBN doesn't exist in the list
    }

    @FXML
    public void editButtonClick(ActionEvent event) {
        // update the book details with the edited values
        if (bookSelected != null) {
            int index = books.indexOf(bookSelected);

            String title = getTitle();
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
            if (pageNumber == null) {
                return;
            }
            if (rating == null) {
                return;
            }
            if (isbn == null) {
                return;
            }
            if (edition == null) {
                return;
            }
            if (publisher == null) {
                return;
            }
            if (language == null) {
                return;
            }
            if (translators == null) {
                return;
            }
            if (authors == null) {
                return;
            }
            else if ((title == null || title.isBlank()) &&
                    (subtitle == null || subtitle.isBlank()) &&
                    (authors == null || authors.isEmpty()) &&
                    (translators == null || translators.isEmpty()) &&
                    (isbn == null || isbn.isBlank()) &&
                    (publisher == null || publisher.isBlank()) &&
                    (date == null || date.isBlank()) &&
                    (edition == null || edition.isBlank()) &&
                    (cover == null || cover.isBlank()) &&
                    (language == null || language.isBlank()) &&
                    (rating == null || rating.isBlank()) &&
                    (tags == null || tags.isEmpty()) &&
                    (pageNumber == null || pageNumber.isBlank()) &&
                    (coverType == null || coverType.isBlank())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("No Information Entered");
                alert.setContentText("Please enter information before editing the book.");
                alert.showAndWait();

            }
            // ISBN is empty or not checking
            if (isbn.isBlank()) {
                showErrorAlert("ISBN cannot be empty.");
                return;
            }
            else {
                Book updatedBook = new Book(title, subtitle, authors, translators, isbn, publisher, date,
                        edition, cover, language, rating, tags, pageNumber, coverType);
                books.set(index, updatedBook);
                updateBookTableView();
                clearFields();
                ((Node) event.getSource()).getScene().getWindow().hide();

            }
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
        editionField.clear();
        coverField.clear();
        languageField.clear();
        ratingField.clear();
        tagsField.clear();
        tagsListView.getItems().clear();
        pageNumberField.clear();
        coverTypeField.clear();
        datePickerField.setValue(null);
    }
    @FXML
    private void updateBookTableView() {

        table.setItems(FXCollections.observableArrayList(books));
    }
    public String getTitle() {
        String title = titleField.getText();
        return title != null? title : "";
    }
    public String getSubtitle()
    {
        String subtitle = subtitleField.getText();
        return subtitle != null ? subtitle : "";
    }

    public String getISBN() {
        String isbn = isbnField.getText();
        if (isbn == null || isbn.isBlank()) {
            return ""; // Return empty string if ISBN field is empty
        }

        else
        {
            String formattedISBN = isbn.replaceAll("[^\\d]", ""); // Remove any non-digit characters
            if (formattedISBN.length() > 13) {     // Limit to 13 characters
                formattedISBN = formattedISBN.substring(0, 13);
            }
            // ISBN length check
            if (formattedISBN.length() != 13) {
                showErrorAlert("ISBN must contain exactly 13 digits.");
                return null;
            }
            // Add "-" after every four characters
            StringBuilder formattedValue = new StringBuilder();
            for (int i = 0; i < formattedISBN.length(); i++) {
                if (i > 0 && i % 4 == 0) {
                    formattedValue.append("-");
                }
                formattedValue.append(formattedISBN.charAt(i));
            }
            isbnField.setText(formattedValue.toString());
            return formattedValue.toString();
        }
    }

    public String getPublisher()
    {
        String publisher =  publisherField.getText();
        if (publisher == null || publisher.isEmpty()) {
            return "";
        } else if (publisher.matches(".\\d+.")) {
            showErrorAlert("Publisher cannot contain numeric characters.");
            return null; // Show error message for publisher containing numeric characters
        } else {
            return publisher;
        }
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
        } else if (language.matches(".\\d+.")) {
            showErrorAlert("Title cannot contain numeric characters.");
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
        for (String author : authorList) {
            if (author.matches(".*\\d+.*")) {
                showErrorAlert("Author names cannot contain numeric characters.");
                return null; // Show error message and return null if any author name contains numeric characters
            }
        }
        return authorList; // Return the list if all author names are valid
    }
    public ArrayList<String> getTranslators() {
        ArrayList<String> translatorList = new ArrayList<>(translatorsListView.getItems());
        for (String translator : translatorList) {
            if (translator.matches(".*\\d+.*")) {
                showErrorAlert("Translator names cannot contain numeric characters.");
                return null; // Show error message and return null if any translator name contains numeric characters
            }
        }
        return translatorList; // Return the list if all translator names are valid
    }

    public ArrayList<String> getTags(){
        ArrayList<String> tagsList = new ArrayList<>(tagsListView.getItems());
        if (tagsList.isEmpty()) {
            return null;
        } else {
            return tagsList;
        }
    }
    public String getDate() {
        LocalDate selectedDate = datePickerField.getValue();
        if (selectedDate == null) {
            return null;
        } else {
            return selectedDate.toString(); // You can format the date as needed
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
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML //This method, selects the parts to add and shows the url of the file.
    public void importCoverButton(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Cover Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // image chosen
            String imagesDirectory = "src/images/";
            File imagesDir = new File(imagesDirectory);
            if (!imagesDir.exists()) {
                imagesDir.mkdirs();
            }
            String imageName = selectedFile.getName();
            String targetPath = imagesDirectory + imageName;
            try {
                Files.copy(selectedFile.toPath(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error copying image file.", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            coverField.setText(targetPath);
        }
    }
}