// IBookManager.aidl
package com.example.AIDLLearning.aidl;

// Declare any non-default types here with import statements
import com.example.AIDLLearning.aidl.Book;

interface IBookManager {
   List<Book> getListBook();
   void addBook(in Book Book);
}
