package thornsmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import thornsmod.EchoTag;
import thornsmod.ThornsMod;
import thornsmod.actions.AlchemicalProwessAction;

public class CalmingWavesPower extends BasePower {
    public static final String POWER_ID = ThornsMod.makeID("CalmingWavesPower");
    private static final PowerStrings powerStrings;

    public CalmingWavesPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if (usedCard.hasTag(EchoTag.ECHOED_CARD)) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.amount));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}
