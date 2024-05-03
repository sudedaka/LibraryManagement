package com.example.librarymanagement;

import com.example.librarymanagement.Book;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;

public class FilterController {
    @FXML
    private VBox root;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox tagsContainer;
    @FXML
    private Label noTagsLabel;

    private TableView<Book> bookTableView;
    private ArrayList<Book> books;

    public void initialize(TableView<Book> bookTableView, ArrayList<Book> books) {
        this.bookTableView = bookTableView;
        this.books = books;
        loadTags();
    }
    @FXML
    private void loadTags() {
        tagsContainer.getChildren().clear();
        if (books.isEmpty()) {
            noTagsLabel.setVisible(true);
        } else {
            HashSet<String> uniqueTags = new HashSet<>();
            for (Book book : books) {
                if (book.getTags() != null) {
                    for (String tag : book.getTags()) {
                        if (!uniqueTags.contains(tag)) {
                            CheckBox checkBox = new CheckBox(tag);
                            tagsContainer.getChildren().add(checkBox);
                            uniqueTags.add(tag);
                        }
                    }
                }
            }
        }
    }

    @FXML
    protected void applyFilters(ActionEvent event) {
        bookTableView.getItems().clear(); // Clear the current items in the TableView

        for (Book book : books) {
            if (book.getTags() != null) {
                boolean match = false;
                for (int i = 0; i < tagsContainer.getChildren().size(); i++) {
                    CheckBox checkBox = (CheckBox) tagsContainer.getChildren().get(i);
                    if (checkBox.isSelected() && book.getTags().contains(checkBox.getText())) {
                        match = true;
                        break;
                    }
                }
                if (match) {
                    bookTableView.getItems().add(book);
                }
            }
        } Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    private void updateCheckBoxes() {
        for (int i = 0; i < tagsContainer.getChildren().size(); i++) {
            CheckBox checkBox = (CheckBox) tagsContainer.getChildren().get(i);
            String tag = checkBox.getText();
            boolean tagExists = false;
            for (Book book : books) {
                if (book.getTags() != null && book.getTags().contains(tag)) {
                    tagExists = true;
                    break;
                }
            }
            if (!tagExists) {
                tagsContainer.getChildren().remove(checkBox);
            }
        }
    }

}