package thornsmod.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import thornsmod.ThornsMod;

public class AncestralHealingPower extends BasePower {
    public static final String POWER_ID = ThornsMod.makeID("AncestralHealingPower");
    private static final PowerStrings powerStrings;
    private boolean gainHealingNext;

    public AncestralHealingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);
        this.updateDescription();

        this.gainHealingNext = true;
        // check for cards played this turn before power was played
        if (!AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
                if (c.type == AbstractCard.CardType.ATTACK) {
                    this.gainHealingNext = false;
                    break;
                }
            }
        }
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    public void atStartOfTurn() {
        if (this.gainHealingNext) {
            this.addToBot(new HealAction(this.owner, this.owner, this.amount));
        }

        this.gainHealingNext = true;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.gainHealingNext = false;
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}