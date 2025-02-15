package thornsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.EchoTag;
import thornsmod.ThornsMod;
import thornsmod.cards.EchoCard;

public class EchoEffectAction extends AbstractGameAction {
    private EchoCard card;
    private boolean targetAll;

    public EchoEffectAction(AbstractCreature target, EchoCard card, boolean targetAll) {
        this.target = target;
        this.card = card;
        this.actionType = ActionType.SPECIAL;
        this.targetAll = targetAll;
    }

    public void update() {
        if (this.card.hasTag(EchoTag.ECHOED_CARD)) {
            this.isDone = true;
            return;
        }

        if (targetAll || (target != null && !card.purgeOnUse && !target.isDeadOrEscaped() && !target.halfDead)) {

            if (targetAll) {
                if (AbstractDungeon.getCurrRoom().monsters.haveMonstersEscaped() || AbstractDungeon.getCurrRoom().monsters.areMonstersDead()) {
                    this.isDone = true;
                    return;
                }
            }

            AbstractCard echoCard = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(echoCard);
            echoCard.current_x = card.current_x;
            echoCard.current_y = card.current_y;
            echoCard.target_x = (float) Settings.WIDTH / 2.0f - 300.0f * Settings.scale;
            echoCard.target_y = (float) Settings.HEIGHT / 2.0f;
            // use for echo-related effects
            echoCard.tags.add(EchoTag.ECHOED_CARD);
            echoCard.calculateCardDamage((AbstractMonster) target);

            echoCard.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(echoCard, (AbstractMonster) target, card.energyOnUse, true, true), true);
            AbstractDungeon.actionManager.addToBottom(new SFXAction(ThornsMod.makeID("ECHO_ACTIVATE")));
        }

        this.isDone = true;
    }
}
