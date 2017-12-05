package com.youscada.youstream;

import com.youscada.youstream.domain.ConfigData;
import com.youscada.youstream.domain.NodePacket;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class YouStreamCore {

    final static Logger logger = Logger.getLogger(YouStreamCore.class);
    public static final String REGISTER_KEWORD = "register";
    public static final String UNREGISTER_KEYWORD = "unregister";

    private Set<NodePacket> nodes = new HashSet<>();

    public void dispatch(ConfigData configData) {
        logger.info(String.format(
                "Dispatching message. Key{%s} %s",
                configData.getKey(),
                configData.getNodePacket().toString()
        ));

        NodePacket nodePacket = configData.getNodePacket();

        if(configData.getKey().equals(REGISTER_KEWORD)) {
            register(nodePacket);
        } else if(configData.getKey().equals(UNREGISTER_KEYWORD)) {
            unregister(nodePacket);
        }
    }

    public void stop(NodePacket nodePacket) {
        updateActive(nodePacket, false);
    }

    public void start(NodePacket nodePacket) {
        updateActive(nodePacket, true);
    }

    private void updateActive(NodePacket nodePacket, boolean active) {
        if (nodes.contains(nodePacket)) {
            logger.info(String.format(
                    "Stopping %s", nodePacket.toString())
            );

            nodes.stream().filter(currentNodePacket -> currentNodePacket.equals(nodePacket)).forEach(currentNodePacket -> {
                currentNodePacket.setActive(active);
            });
        } else {
            logger.warn(String.format(
                    "Trying to start/stop not registered node %s", nodePacket.toString()
            ));
        }
    }

    private void register(NodePacket nodePacket) {
        logger.info(
                String.format("Registering or updating %s", nodePacket.toString())
        );

        if(nodes.contains(nodePacket)) {
            /* Remove in case of update of
            * information. We use the same message
            * for registration and update to keep the
            * protocol simple
            */
            nodes.remove(nodePacket);
        }
        nodes.add(nodePacket);
    }

    public void unregister(NodePacket nodePacket) {
        logger.info(
                String.format("Removing node %s", nodePacket.toString())
        );
        nodes.remove(nodePacket);
    }

    public Set<NodePacket> getNodes() {
        return nodes;
    }

    public void toggle(NodePacket nodePacket) {
        nodes.stream().filter(currentNodePacket -> currentNodePacket.equals(nodePacket)).forEach(currentNodePacket -> {
            if (currentNodePacket.isActive()) {
                stop(currentNodePacket);
            } else {
                start(currentNodePacket);
            }
        });
    }
}
