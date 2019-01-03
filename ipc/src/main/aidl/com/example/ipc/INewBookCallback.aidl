// INewBookCallbac.aidl
package com.example.ipc;

import com.example.ipc.IBook;

// Declare any non-default types here with import statements

interface INewBookCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void onNewBook(in IBook book);
}
