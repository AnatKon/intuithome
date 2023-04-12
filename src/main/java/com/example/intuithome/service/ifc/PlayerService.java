package com.example.intuithome.service.ifc;

import com.example.intuithome.domain.Player;
import org.springframework.stereotype.Service;

@Service
public interface PlayerService {

    Player getPlayer(String id);
}
