package com.raccoon.takenoko.player.factories;

import com.raccoon.takenoko.player.PathFinderBot;
import com.raccoon.takenoko.player.Player;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SinglePathBotFactory implements FactoryBean<Player> {

    @Override
    public Player getObject() throws Exception {
        return new PathFinderBot();
    }

    @Override
    public Class<?> getObjectType() {
        return PathFinderBot.class;
    }
}
