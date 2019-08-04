package ru.piom.filestorage;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileStorageService {
    void add(String fileName, byte[] data) throws IOException;

    void delete(String fileName) throws FileNotFoundException;

    int size();

}
