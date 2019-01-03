package com.example.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by guocai.cgc on 2018/4/2.
 */

public class IBook implements Parcelable {

    private String bookName;
    private String author;

    public IBook() {

    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    protected IBook(Parcel in) {
        bookName = in.readString();
        author = in.readString();
    }

    public static final Creator<IBook> CREATOR = new Creator<IBook>() {
        @Override
        public IBook createFromParcel(Parcel in) {
            return new IBook(in);
        }

        @Override
        public IBook[] newArray(int size) {
            return new IBook[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
        dest.writeString(author);
    }

    @Override
    public String toString() {
        return "bookName="+ bookName + ";author="+ author + "\n";
    }

    public void readFromParcel(Parcel reply) {
        bookName = reply.readString();
        author = reply.readString();
    }
}
