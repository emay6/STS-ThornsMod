package thornsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import thornsmod.ThornsMod;
import thornsmod.powers.CorrosionPower;

// basically copied from equivalent poison action
public class DoubleCorrosionAction extends AbstractGameAction {
    public DoubleCorrosionAction(AbstractCreature target, AbstractCreature source) {
        this.target = target;
        this.source = source;
        this.actionType = ActionType.DEBUFF;
        this.attackEffect = AttackEffect.FIRE;
    }

    public void update() {
        if (this.target != null && this.target.hasPower(ThornsMod.makeID("Corrosion"))) {
            this.addToTop(new ApplyPowerAction(this.target, this.source, new CorrosionPower(this.target, this.source, this.target.getPower(ThornsMod.makeID("Corrosion")).amount), this.target.getPower(ThornsMod.makeID("Corrosion")).amount));
        }

        this.isDone = true;
    }
}
