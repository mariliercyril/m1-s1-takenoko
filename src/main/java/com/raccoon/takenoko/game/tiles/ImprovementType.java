package com.raccoon.takenoko.game.tiles;

import com.raccoon.takenoko.tool.Constants;

public enum ImprovementType {
    ENCLOSURE, FERTILIZER, WATERSHED;

    public void improve(Tile t) {
        switch(this) {
            case WATERSHED:
                boolean wasIrrigated = t.isIrrigated();
                t.setIrrigated(true);   // First irrigation growth rule
                if (wasIrrigated) {
                    t.increaseBambooSize(Constants.USUAL_BAMBOO_GROWTH);
                }
                break;
            case ENCLOSURE:
                t.setEnclosure(true);
                break;
            case FERTILIZER:
                t.setGrowthFactor(2);
                break;
                default:
                    break;
        }
    }
}
