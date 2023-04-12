package com.example.intuithome.dao.ifc;

import com.example.intuithome.domain.Player;

public interface PlayerDao {

    Player findItemByPlayerId(String playerId);
}
