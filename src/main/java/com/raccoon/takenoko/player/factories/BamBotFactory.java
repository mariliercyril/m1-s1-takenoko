package com.raccoon.takenoko.player.factories;

import com.raccoon.takenoko.player.BamBot;
import com.raccoon.takenoko.player.Player;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class BamBotFactory implements FactoryBean<Player> {

    @Override
    public Player getObject() {
        return new BamBot();
    }

    @Override
    public Class<?> getObjectType() {
        return Player.class;
    }


}