package com.example.intuithome.dao.impl;

import com.example.intuithome.dao.ifc.PlayerDao;
import com.example.intuithome.domain.Player;
import com.example.intuithome.service.impl.IntegerTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class BasePlayerDao implements PlayerDao {
    private MongoClient mongoClient = MongoClients.create();
    private MongoDatabase db = mongoClient.getDatabase("intuit");
    private MongoCollection<Document> collection = db.getCollection("players");

    private Gson gson = new GsonBuilder().registerTypeAdapter(Integer.class, new IntegerTypeAdapter()).create();

    @Override
    public Player findItemByPlayerId(String playerId) {
        Document document = collection.find(new Document("playerID", playerId)).first();
        if(document == null) {
            return null;
        }
        return gson.fromJson(document.toJson(), Player.class);
    }
}
