// IBookManager.aidl
package com.example.ipc;

import com.example.ipc.IBook;
import com.example.ipc.INewBookCallback;

// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void addBook(inout IBook book);

    List<IBook> getBookList();

    void registCallback(in INewBookCallback callback);

    void unregistCallback(in INewBookCallback callback);

}
