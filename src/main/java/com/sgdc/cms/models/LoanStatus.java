package com.sgdc.cms.models;

public enum LoanStatus {
    PENDING_APPROVAL,   // Loan requested, awaiting approval
    APPROVED,           // Loan approved by a librarian
    RETURN_REQUESTED,   // Return request submitted by the user
    RETURNED,           // Book has been returned
    OVERDUE             // Loan is overdue
}
