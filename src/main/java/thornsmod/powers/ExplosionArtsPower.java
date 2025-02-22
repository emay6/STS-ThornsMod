package thornsmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import thornsmod.ThornsMod;

public class ExplosionArtsPower extends BasePower {
    public static final String POWER_ID = ThornsMod.makeID("ExplosionArtsPower");
    private static final PowerStrings powerStrings;

    public ExplosionArtsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}
