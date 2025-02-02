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

public class EchoEffectAction extends AbstractGameAction {
    private AbstractCard card;

    public EchoEffectAction(AbstractCreature target, AbstractCard card) {
        this.target = target;
        this.card = card;

        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (target != null && !card.purgeOnUse && !target.isDying && !target.isDeadOrEscaped() && !target.halfDead) {
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
