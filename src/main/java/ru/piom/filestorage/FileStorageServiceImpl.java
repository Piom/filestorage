package ru.piom.filestorage;

import java.io.FileNotFoundException;

public class FileStorageServiceImpl implements FileStorageService {
    private static int FILE_NAME_LENGTH_LIMIT = 50;
    private static int DATA_LENGTH_LIMIT = 10;
    private static int STORAGE_LIMIT = 100_000;

    private String[] nameListIndex;
    private byte[][] dataArray;

    public FileStorageServiceImpl() {
        this.nameListIndex = new String[STORAGE_LIMIT];
        this.dataArray = new byte[STORAGE_LIMIT][DATA_LENGTH_LIMIT];
    }

    @Override
    public void add(String fileName, byte[] data) {

        if (fileName == null || fileName.isEmpty()){
            git
        }
    }

    @Override
    public void delete(String fileName) {

    }

    private void deleteElement(String key) throws FileNotFoundException {
        int pos = findElement(key);

        if (pos == -1) {
            throw new FileNotFoundException("Element not found");
        }
        int i;
        for (i = pos; i < nameListIndex.length; i++) {
            nameListIndex[i] = nameListIndex[i + 1];
            dataArray[i] = dataArray[i + 1];
        }

    }


    private int findElement(String key) {
        for (int i = 0; i < nameListIndex.length; i++)
            if (nameListIndex[i].equals(key))
                return i;

        return -1;
    }

}
