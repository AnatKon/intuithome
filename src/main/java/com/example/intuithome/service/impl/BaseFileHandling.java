package com.example.intuithome.service.impl;

import com.example.intuithome.service.ifc.FileHandling;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Service
public class BaseFileHandling implements FileHandling {

    public void readFileAndInsertToDB(String fileName) {
        Supplier<Stream<String>> streamSupplier = validateFile(fileName);
        if (streamSupplier == null) {
            return;
        }

        List<String> fieldNameList = Arrays.asList(streamSupplier.get().findFirst().get().split(","));
        if (fieldNameList == null) {
            System.out.println("Invalid file");
            return;
        }

        runBatch(fieldNameList, streamSupplier);
    }

    protected Supplier<Stream<String>> validateFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            System.out.println("Empty file name");
            return null;
        }
        ClassLoader classLoader = getClass().getClassLoader();
        if (classLoader.getResource(fileName) == null) {
            System.out.println("File does not exists");
            return null;
        }

        Supplier<Stream<String>> streamSupplier = () -> {
            try {
                return Files.lines(Paths.get(classLoader.getResource(fileName).toURI()));
            } catch (IOException e) {
                System.out.println("Empty file");
                return null;
            } catch (URISyntaxException e) {
                System.out.println("Empty file");
                return null;
            }
        };

        if (streamSupplier == null || streamSupplier.get() == null || !streamSupplier.get().findFirst().isPresent()) {
            System.out.println("Empty file");
            return null;
        }

        return streamSupplier;
    }

    public void runBatch(List<String> fieldNameList, Supplier<Stream<String>> streamSupplier){
        BalanceBatch balance = new BalanceBatch(fieldNameList, 10);
        balance.startAll();

        streamSupplier.get().forEach(balance::send);
        balance.stopAll();
    }
}
