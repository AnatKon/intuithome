package com.example.intuithome.service.impl;

import com.example.intuithome.dao.ifc.PlayerDao;
import com.example.intuithome.domain.Player;
import com.example.intuithome.service.ifc.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasePlayerService implements PlayerService {

    @Autowired
    PlayerDao mPlayerDao;

    public Player getPlayer(String playerId) {
        return mPlayerDao.findItemByPlayerId(playerId);
    }
}
