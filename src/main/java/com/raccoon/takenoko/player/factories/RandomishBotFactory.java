package com.raccoon.takenoko.player.factories;

import com.raccoon.takenoko.player.Player;
import com.raccoon.takenoko.player.RandomTerBot;
import com.raccoon.takenoko.player.RandomishCleverishBot;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class RandomishBotFactory implements FactoryBean<Player> {

    @Override
    public Player getObject() throws Exception {
        return new RandomishCleverishBot();
    }

    @Override
    public Class<?> getObjectType() {
        return RandomishCleverishBot.class;
    }
}
