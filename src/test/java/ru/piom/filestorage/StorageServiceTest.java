package ru.piom.filestorage;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class StorageServiceTest {

    private FileStorageService storageService = new FileStorageServiceImpl();

    @Test
    public void testAddPositiveCase() {
        storageService.add(generateFileName(10), getBytes(50));
    }

    @Test
    public void testDeletePositiveCase() {
        String fileName = generateFileName(10);
        storageService.add(fileName, getBytes(50));
        storageService.delete(fileName);

    }

    private String generateFileName(int i) {
        byte[] array = getBytes(i);
        return new String(array, StandardCharsets.UTF_8);
    }

    private byte[] getBytes(int i) {
        byte[] array = new byte[i]; // length is bounded by 7
        new Random().nextBytes(array);
        return array;
    }
}
