package com.akul.inspire;

// Created by Akul Srivastava
// Date: 21 March 2019

public class Quote
{
    public String quote, qcategory, qauthor;

    public Quote()
    {

    }

    public String getQauthor() {
        return qauthor;
    }

    public void setQauthor(String qauthor) {
        this.qauthor = qauthor;
    }

    public Quote(String qauthor) {
        this.qauthor = qauthor;
    }

    public Quote(String quote, String qcategory) {
        this.quote = quote;
        this.qcategory = qcategory;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getQcategory() {
        return qcategory;
    }

    public void setQcategory(String qcategory) {
        this.qcategory = qcategory;
    }
}

// Created by Akul Srivastava
// Date: 21 March 2019