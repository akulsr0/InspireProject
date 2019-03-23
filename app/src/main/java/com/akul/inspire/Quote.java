package com.akul.inspire;

public class Quote
{
    public String quote, qcategory;

    public Quote()
    {

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
