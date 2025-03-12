package thornsmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import thornsmod.ThornsMod;

public class RelentlessPower extends BasePower {
    public static final String POWER_ID = ThornsMod.makeID("RelentlessPower");
    private static final PowerStrings powerStrings;

    public RelentlessPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner, -1);
        this.updateDescription();
    }

    public void onGainedBlock(float blockAmount) {
        this.flash();
        this.addToBot(new LoseBlockAction(owner, owner, (int) blockAmount));
    }


    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "RelentlessPower"));
        }

    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}
