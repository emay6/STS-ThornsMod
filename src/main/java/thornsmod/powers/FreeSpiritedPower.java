package thornsmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import thornsmod.ThornsMod;

public class FreeSpiritedPower extends BasePower {
    public static final String POWER_ID = ThornsMod.makeID("FreeSpiritedPower");
    private static final PowerStrings powerStrings;

    public FreeSpiritedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
        this.updateDescription();
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
        } else {
            this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[2];
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}
