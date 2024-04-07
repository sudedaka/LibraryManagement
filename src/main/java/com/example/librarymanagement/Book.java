package com.example.librarymanagement;
import java.util.ArrayList;
public class Book {
    private final String title;
    private final String subtitle;
    private final ArrayList<String> authors;
    private final ArrayList<String> translators;
    private final String isbn;
    private final String publisher;
    private final String date;
    private final String edition;
    private final String cover;
    private final String language;
    private final String rating;
    private final ArrayList<String> tags;
    private final String numberofPages;
    private final String coverType;

    public Book(String title,String subtitle,ArrayList<String>authors,ArrayList<String>translators, String isbn,String publisher,String date, String edition,String cover,String language,String rating,ArrayList<String>tags,String numberofPages,String coverType){
        this.title=title;
        this.subtitle=subtitle;
        this.authors=authors;
        this.translators=translators;
        this.isbn=isbn;
        this.publisher=publisher ;
        this.date=date;
        this.edition=edition;
        this.cover=cover;
        this.language=language;
        this.rating=rating;
        this.tags=tags;
        this.numberofPages=numberofPages;
        this.coverType=coverType;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getEdition() {
        return edition;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getNumberofPages() {
        return numberofPages;
    }

    public String getCoverType() {
        return coverType;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public ArrayList<String> getTranslators() {
        return translators;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getLanguage() {
        return language;
    }

    public String getCover() {
        return cover;
    }

    public String getDate() {
        return date;
    }

    public String getRating() {
        return rating;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }
}
