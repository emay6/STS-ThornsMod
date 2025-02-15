package thornsmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import thornsmod.EchoTag;
import thornsmod.ThornsMod;

public class SeaOfThornsPower extends BasePower {
    public static final String POWER_ID = ThornsMod.makeID("SeaOfThorns");
    private static final PowerStrings powerStrings;

    public SeaOfThornsPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner, -1);
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}
