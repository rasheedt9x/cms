package com.sgdc.cms.models;


import jakarta.persistence.*;


@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "available_copies")
    private int availableCopies;

    @Column(name = "total_copies")
    private int totalCopies;

    // @Lob
    // @Column(name = "image", columnDefinition = "BLOB")
    // private byte[] image;


    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }


    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailaleCopies() {
        return availableCopies;
    }

    public void setAvailaleCopies(int availaleCopies) {
        this.availableCopies = availaleCopies;
    }

    public Long getId() {
        return id;
    }

	// public byte[] getImage() {
	// 	return image;
	// }

	// public void setImage(byte[] image) {
	// 	this.image = image;
	// }
}
