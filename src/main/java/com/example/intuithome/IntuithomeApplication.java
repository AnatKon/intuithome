package com.example.intuithome;

import com.example.intuithome.dao.ifc.PlayerDao;
import com.example.intuithome.service.ifc.FileHandling;
import com.example.intuithome.service.impl.BaseFileHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class IntuithomeApplication {

    @Autowired
    private PlayerDao playerDao;

    public static void main(String[] args) {
        SpringApplication.run(IntuithomeApplication.class, args);
        BaseFileHandling fileHandling = new BaseFileHandling();
        fileHandling.readFileAndInsertToDB(FileHandling.FILE_NAME);
    }

}
