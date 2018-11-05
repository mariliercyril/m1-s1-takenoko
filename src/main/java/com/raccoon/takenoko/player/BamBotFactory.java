package com.raccoon.takenoko.player;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class BamBotFactory implements FactoryBean<Player> {

    @Override
    public Player getObject() throws Exception {
        return new BamBot();
    }

    @Override
    public Class<?> getObjectType() {
        return Player.class;
    }


}