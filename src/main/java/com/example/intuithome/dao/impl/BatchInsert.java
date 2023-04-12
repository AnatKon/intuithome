package com.example.intuithome.dao.impl;

import com.example.intuithome.service.impl.IntegerTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BatchInsert implements AutoCloseable {

    private MongoClient mongoClient = MongoClients.create();
    private MongoDatabase db = mongoClient.getDatabase("intuit");
    private MongoCollection<Document> collection = db.getCollection("players");

    private final int batchLimit;
    private List<Document> playerList;

    private Gson gson = new GsonBuilder().registerTypeAdapter(Integer.class, new IntegerTypeAdapter()).create();

    public BatchInsert(int batchLimit) {
        this.batchLimit = batchLimit;
        playerList = new ArrayList<>();

        // create unique index on playerID
        IndexOptions indexOptions = new IndexOptions().unique(true);
        collection.createIndex(Indexes.descending("playerID"), indexOptions);
    }

    public void insert(Map<String, String> playerMap) {
        String s = gson.toJson(playerMap);

        Document document = new Document();
        for (String key : playerMap.keySet()) {
            document.append(key, playerMap.get(key));
        }
        playerList.add(document);
        if (playerMap.size() >= batchLimit) {
            sendBatch();
        }
    }

    public void sendBatch() {
        collection.insertMany(playerList);
        System.out.format("Send batch with %d records%n", playerList.size());
        playerList.clear();
    }

    @Override
    public void close() {
        if (playerList.size() != 0) {
            sendBatch();
        }
    }
}
