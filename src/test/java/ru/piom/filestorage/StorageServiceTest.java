package ru.piom.filestorage;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class StorageServiceTest {

    private FileStorageService storageService = new FileStorageServiceImpl();

    @Test
    public void testAddPositiveCase() {
        try {
            storageService.add(generateFileName(10), getBytes(10));
        } catch (IOException ex) {
            //do nothing
        }
    }

    @Test
    public void testDeletePositiveCase() {
        String fileName = generateFileName(10);
        try {
            storageService.add(fileName, getBytes(10));
            storageService.delete(fileName);
            assertEquals(storageService.size(), 0);
        } catch (IOException ex) {
            //do nothing
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddIncorrectFileName() {
        try {
            storageService.add(generateFileName(51), getBytes(10));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateFileName(int i) {
        byte[] array = getBytes(i);
        return new String(array, StandardCharsets.UTF_8);
    }

    private byte[] getBytes(int i) {
        byte[] array = new byte[i];
        new Random().nextBytes(array);
        return array;
    }
}
