package thornsmod.cards;

import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.EchoTag;
import thornsmod.ThornsMod;
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

        // TODO: re-add reverberation functionality when implemented
        if (false/*AbstractDungeon.player.hasPower(ThornsMod.makeID("Reverberation"))*/) {
            this.doEcho(target);
            this.doEcho(target);
        } else {
            this.doEcho(target);
        }
    }

    private void doEcho(AbstractCreature target) {
        if (!this.purgeOnUse) {
            AbstractMonster m = null;
            if (this.target != null) {
                m = (AbstractMonster) target;
            }

            AbstractCard echoCard = this.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(echoCard);
            echoCard.current_x = this.current_x;
            echoCard.current_y = this.current_y;
            echoCard.target_x = (float) Settings.WIDTH / 2.0f - 300.0f * Settings.scale;
            echoCard.target_y = (float) Settings.HEIGHT / 2.0f;
            // use for echo-related effects
            echoCard.tags.add(EchoTag.ECHOED_CARD);
            if (m != null) {
                echoCard.calculateCardDamage(m);
            }

            echoCard.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(echoCard, m, this.energyOnUse, true, true), true);
            AbstractDungeon.actionManager.addToBottom(new SFXAction(ThornsMod.makeID("ECHO_ACTIVATE")));
        }
    }

    public void setCardDoEcho(boolean trigger) {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && trigger) {
            this.cardDoEcho = true;
        } else {
            this.cardDoEcho = false;
        }
    }

    public void setEchoGlow() {
        this.glowColor = this.cardDoEcho ? AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy() : AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }
}
