package ru.piom.filestorage;

public interface FileStorageService {
    void add(String fileName, byte[] data);

    void delete(String fileName);

}
