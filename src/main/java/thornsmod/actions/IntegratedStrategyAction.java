package thornsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IntegratedStrategyAction extends AbstractGameAction {
    private AbstractMonster m;
    private AbstractPlayer p;
    private DamageInfo info;

    public IntegratedStrategyAction(AbstractMonster m, AbstractPlayer p, DamageInfo info) {
        this.m = m;
        this.p = p;
        this.info = info;
    }

    public void update() {
        if (this.m != null && (this.m.intent == AbstractMonster.Intent.ATTACK || this.m.intent == AbstractMonster.Intent.ATTACK_BUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
            this.addToTop(new GainBlockAction(this.p, this.info.base));
        }
        else {
            this.addToTop(new DamageAction(this.m, this.info, AttackEffect.SLASH_HEAVY));
        }
        this.isDone = true;
    }
}
