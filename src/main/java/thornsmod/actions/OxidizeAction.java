package thornsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import thornsmod.ThornsMod;
import thornsmod.powers.CorrosionPower;

public class OxidizeAction extends AbstractGameAction {
    private DamageInfo info;

    public OxidizeAction(AbstractCreature target, DamageInfo info) {
        this.actionType = ActionType.BLOCK;
        this.target = target;
        this.info = info;
    }

    public void update() {
        if (this.target != null && this.target.hasPower(ThornsMod.makeID("Corrosion"))) {

        }

        this.addToTop(new ApplyPowerAction(this.target, this.source, new CorrosionPower(this.target, this.source, this.info.base)));
        this.isDone = true;
    }
}
