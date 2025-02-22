package thornsmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.actions.EchoEffectAction;
import thornsmod.util.CardStats;

public abstract class EchoCard extends BaseCard {
    public boolean cardDoEcho;

    public EchoCard(String ID, CardStats info) {
        super(ID, info);
        this.cardDoEcho = false;
    }

    public void echo(AbstractPlayer p, AbstractMonster m) {
        AbstractCreature target = null;
        if (this.target == CardTarget.ENEMY || this.target == CardTarget.ALL_ENEMY) {
            target = m;
        } else if (this.target == CardTarget.SELF) {
            target = p;
        }

        boolean targetAll = m == null && target == null;

        // TODO: re-add reverberation functionality when implemented
        if (false/*AbstractDungeon.player.hasPower(ThornsMod.makeID("Reverberation"))*/) {
            AbstractDungeon.actionManager.addToBottom(new EchoEffectAction(target, this, targetAll));
            AbstractDungeon.actionManager.addToBottom(new EchoEffectAction(target, this, targetAll));
        } else {
            AbstractDungeon.actionManager.addToBottom(new EchoEffectAction(target, this, targetAll));
        }
    }

    public void setCardDoEcho(boolean trigger) {
        /*if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && trigger) {
            this.cardDoEcho = true;
        } else {
            this.cardDoEcho = false;
        }*/
        this.cardDoEcho = trigger;
    }

    public void setEchoGlow() {
        this.glowColor = this.cardDoEcho ? AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy() : AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }
}
