package com.example.intuithome.service.ifc;

public interface FileHandling {

    String FILE_NAME = "player.csv";

    void readFileAndInsertToDB(String fileName);
}
