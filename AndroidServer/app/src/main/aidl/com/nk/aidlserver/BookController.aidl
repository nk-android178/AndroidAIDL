// BookController.aidl
package com.nk.aidlserver;
import com.nk.aidlserver.Books;
// Declare any non-default types here with import statements

interface BookController {
     List<Books> getBookList(String data);
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
