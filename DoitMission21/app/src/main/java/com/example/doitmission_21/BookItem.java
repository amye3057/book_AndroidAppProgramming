package com.example.doitmission_21;

public class BookItem {
    String book_title;
    String book_wirter;
    String book_story;

    public BookItem(String book_title, String book_wirter, String book_story) {
        this.book_title = book_title;
        this.book_wirter = book_wirter;
        this.book_story = book_story;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getBook_wirter() {
        return book_wirter;
    }

    public void setBook_wirter(String book_wirter) {
        this.book_wirter = book_wirter;
    }
}
