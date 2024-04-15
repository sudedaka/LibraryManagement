package com.example.librarymanagement;

import com.google.gson.GsonBuilder;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.google.gson.Gson;
import javafx.scene.control.TableView;
import java.io.*;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.*;

import javafx.fxml.FXML;


public class MainWindowController extends Application {


    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> subtitleCol;
    @FXML
    private TableColumn<Book, String> authorsCol;
    @FXML
    private TableColumn<Book, String> translatorsCol;
    @FXML
    private TableColumn<Book, String> isbnCol;
    @FXML
    private TableColumn<Book, String> publisherCol;
    @FXML
    private TableColumn<Book, LocalDate> dateCol;
    @FXML
    private TableColumn<Book, String> editionCol;
    @FXML
    private TableColumn<Book, String> coverCol;
    @FXML
    private TableColumn<Book, String> languageCol;
    @FXML
    private TableColumn<Book, String> ratingCol;
    @FXML
    private TableView<Book> tableView;

    //@FXML
    // private ArrayList<Book> books;
    @FXML
    private TableColumn<Book, String> tagsCol;
    @FXML
    private TableColumn<Book, String> numberOfPagesCol;
    @FXML
    private TableColumn<Book, String> coverTypeCol;
    @FXML
    private ArrayList<Book> books = new ArrayList<>(); // Initialize the ArrayList

    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private ListView<String> translatorsListView;

    private ObservableList<String> authorsList = FXCollections.observableArrayList();
    private ObservableList<String> translatorsList = FXCollections.observableArrayList();
    private ObservableList<String> tagsList = FXCollections.observableArrayList();


    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Library Management");
        stage.setMaximized(true);
        stage.setScene(scene);
        MainWindowController controller = fxmlLoader.getController();
        controller.initialize(books); //AddController from passing the books in MainWindow
        controller.loadBooksFromFile();


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    private void loadBooksFromFile() {  // Method to load books from a JSON file.
        String folderPath = "Books";
        String jsonFilePath = folderPath + File.separator + "library.json";
        File jsonFile = new File(jsonFilePath);
        if (jsonFile.exists()) {
            importFromJson(jsonFilePath);
        } else {
            updateBookListView();
        }
    }


    public ArrayList<Book> filterByTags() {
        String searchQuery = searchField.getText().toLowerCase();
        String[] tagsToSearch = searchQuery.split("\\s+"); // Split search query by spaces
        ArrayList<Book> filteredBooks = new ArrayList<>();

        if (searchQuery.isEmpty()) {
            return new ArrayList<>(books); // Return all books if search query is empty
        }

        for (Book book : bookTableView.getItems()) {
            if (book.getTags() == null || book.getTags().isEmpty()) {
                if (searchQuery.isEmpty()) {
                    filteredBooks.add(book); // If the book has no tags and the search query is empty, include it
                }
            } else {
                boolean allTagsFound = true;
                for (String tag : tagsToSearch) {
                    boolean tagFound = false;
                    for (String bookTag : book.getTags()) {
                        if (bookTag.toLowerCase().contains(tag)) {
                            tagFound = true;
                            break;
                        }
                    }
                    if (!tagFound) {
                        allTagsFound = false;
                        break;
                    }
                }
                if (allTagsFound) {
                    filteredBooks.add(book);
                }
            }
        }

        return filteredBooks;
    }


    public void show() {
        ArrayList<Book> showSearchResults = filterByTags();

        // Clear the current items in the TableView
        bookTableView.getItems().clear();

        // Add the filtered results to the TableView
        bookTableView.getItems().addAll(showSearchResults);
    }
    @FXML
    public void handleSearch(ActionEvent event) {
        // Call the show() method to update the TableView with the filtered results
        show();
    }

    @FXML
    public void refreshTableView(ActionEvent event) {
        // Clear the current items in the TableView
        bookTableView.getItems().clear();

        // Add all the books back to the TableView
        bookTableView.getItems().addAll(books);
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
        AddController controller = fxmlLoader.getController();
        controller.initialize(bookTableView,books); //
        stage.showAndWait();
        exportToJson("library.json");

    }

    @FXML
    public void importFromJson(String filePath) {   // Method to read data from a JSON file
        try {
            Gson gson = new Gson();
            try (Reader reader = new FileReader(filePath)) {
                books = gson.fromJson(reader, new TypeToken<ArrayList<Book>>() {
                }.getType());  // Add data read from JSON file to the books list
                updateBookListView();
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
    public void importButton(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            importFromJson(file.getAbsolutePath());  // Read data from the file
        }
    }

    @FXML
    public void exportToJson(String path) {
        initialize(books);
        String folderPath = "Books";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String baseFileName = "library";
        String filePath = folderPath + File.separator + baseFileName + ".json";
        File file = new File(filePath);


        try (FileWriter fileWriter = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(books);
            fileWriter.write(jsonString);
            System.out.println("JSON is created");
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    private String userPath;

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
        userPath=f.getAbsolutePath();
    }

    @FXML
    public void addBook(String title, String subtitle, ArrayList<String> authors, ArrayList<String> translators, String isbn, String publisher, String date, String edition, String cover, String language, String rating, ArrayList<String> tags, String pageNumber, String coverType) {
        Book newBook = new Book(title, subtitle, authors, translators, isbn, publisher, date, edition, cover, language, rating, tags, pageNumber, coverType);
        books.add(newBook);
    }

    @FXML
    public void initialize(ArrayList<Book> books) //This method, set the columns correctly and populate the TableView with data from your book list.
    {

        bookTableView.setItems(FXCollections.observableArrayList(books));

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        subtitleCol.setCellValueFactory(new PropertyValueFactory<>("subtitle"));
        authorsCol.setCellValueFactory(new PropertyValueFactory<>("authors"));
        translatorsCol.setCellValueFactory(new PropertyValueFactory<>("translators"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        editionCol.setCellValueFactory(new PropertyValueFactory<>("edition"));
        coverCol.setCellValueFactory(new PropertyValueFactory<>("cover"));
        languageCol.setCellValueFactory(new PropertyValueFactory<>("language"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tagsCol.setCellValueFactory(new PropertyValueFactory<>("tags"));
        numberOfPagesCol.setCellValueFactory(new PropertyValueFactory<>("numberofPages"));
        coverTypeCol.setCellValueFactory(new PropertyValueFactory<>("coverType"));
    }

    @FXML
    public void editButtonClick(ActionEvent event) throws IOException {
        // Get the selected book from the TableView
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("edit.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setTitle("Edit Screen");
            stage.setMaximized(true);
            stage.setScene(scene);
            EditController editController = fxmlLoader.getController();
            editController.initialize(selectedBook,bookTableView,books);
            stage.showAndWait();
            exportToJson("library.json");
        }
    }

    private void updateBookListView() {

        bookTableView.getItems().setAll(books);
    }
    @FXML
    public void deleteBook() {

        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            books.remove(selectedBook);
            bookTableView.getItems().remove(selectedBook);
            updateBookListView();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String folderPath = "Books";
            String filePath = folderPath + File.separator + "library.json";
            try (Writer writer = new FileWriter(filePath)) {
                gson.toJson(books, writer);
                System.out.println("Selected book is deleted from JSON file.");
            } catch (IOException e) {
                e.printStackTrace();
            }

            gson = new Gson();
            try (Writer writer = new FileWriter(userPath)) {
                gson.toJson(books, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    @FXML
    public void openListController(MouseEvent event) throws IOException {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2) { // Check if it's a double click

            if (selectedBook != null) {
                Stage stage = new Stage(); // Create a new stage
                FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("list.fxml")); // Load the FXML file

                Scene scene = new Scene(fxmlLoader.load(), 320, 240); // Create the scene
                stage.setTitle("List Screen"); // Set the stage title
                stage.setMaximized(true); // Maximize the stage
                stage.setScene(scene); // Set the scene to the stage
                // Get the controller instance
                ListController listController = fxmlLoader.getController();

                // Initialize the controller with the selected book and other necessary data
                listController.initialize(selectedBook, bookTableView, books,event);


                // Show the stage
                stage.showAndWait();
            }
        }
    }


}

