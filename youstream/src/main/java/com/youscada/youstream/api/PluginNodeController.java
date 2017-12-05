package com.youscada.youstream.api;

import com.youscada.youstream.YouStreamCore;
import com.youscada.youstream.domain.NodePacket;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class PluginNodeController {

    final static Logger logger = Logger.getLogger(PluginNodeController.class);

    @Autowired
    private YouStreamCore youStreamCore;

    @CrossOrigin(origins = "*")
    @GetMapping("")
    public Set<NodePacket> allNodes() {
        return youStreamCore.getNodes();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/toggle")
    public boolean toggle(@ModelAttribute NodePacket nodePacket) {
        logger.info(String.format(
                "Toggling node %s", nodePacket.toString()
        ));
        youStreamCore.toggle(nodePacket);

        return true;
    }

}
