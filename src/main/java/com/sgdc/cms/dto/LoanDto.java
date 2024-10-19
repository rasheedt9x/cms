package com.sgdc.cms.dto;

public class LoanDto {
    private Long id;
    private Long bookId;
    private String bookTitle;
    public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	private String collegeId;    
	private String startDate;
    private String dueDate;
    private String returnDate;
    private boolean loanApproved;

    
    public boolean isLoanApproved() {
		return loanApproved;
	}

	public void setLoanApproved(boolean loanApproved) {
		this.loanApproved = loanApproved;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBook(Long bookId) {
        this.bookId = bookId;
    }

    public String getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(String collegeId) {
		this.collegeId = collegeId;
	}

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {  
        this.dueDate = dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
