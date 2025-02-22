package thornsmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.ThornsMod;
import thornsmod.cards.EchoCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.util.CardStats;

public class Dissolver extends EchoCard {
    public static final String ID = makeID(Dissolver.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );
    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 4;

    public Dissolver() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // disable echo effect if actual target doesn't have block
        if (m.currentBlock <= 0) {
            this.setCardDoEcho(false);
        }

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SMASH));
        if (this.cardDoEcho) this.echo(p, m);
    }

    public void triggerOnGlowCheck() {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && m.currentBlock > 0) {
                this.setCardDoEcho(true);
                this.setEchoGlow();
                break;
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Dissolver();
    }

}
