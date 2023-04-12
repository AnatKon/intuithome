package com.example.intuithome.api;

import com.example.intuithome.domain.Player;
import com.example.intuithome.service.ifc.FileHandling;
import com.example.intuithome.service.impl.BasePlayerService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerApi {

    @Autowired
    private BasePlayerService mBasePlayerService;

    private Gson gson = new Gson();

    @GetMapping(value = "/players")
    public ResponseEntity<Resource> getPlayers(){
        return new ResponseEntity<>(new ClassPathResource(FileHandling.FILE_NAME), HttpStatus.OK);
    }

    @GetMapping(value = "/player/{playerId}")
    public ResponseEntity<String> getPlayers(@PathVariable String playerId){
        Player player = mBasePlayerService.getPlayer(playerId);

        return new ResponseEntity<>(gson.toJson(player), HttpStatus.OK);
    }
}
