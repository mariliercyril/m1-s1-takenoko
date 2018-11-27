package com.raccoon.takenoko.player;

import org.springframework.beans.factory.FactoryBean;

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