package thornsmod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.cards.EchoCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.util.CardStats;

public class MomentousStrike extends EchoCard {
    public static final String ID = makeID(MomentousStrike.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            2
    );
    private static final int DAMAGE = 10;

    private static final int UPGRADE_DAMAGE = 4;

    public MomentousStrike() {
        super(ID, info);

        setDamage(DAMAGE, UPGRADE_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));

        if (this.cardDoEcho) this.echo(p, m);
    }

    public void triggerOnGlowCheck() {
        this.setCardDoEcho(!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && ((AbstractCard)AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1)).type == CardType.ATTACK);
        this.setEchoGlow();
    }

    @Override
    public AbstractCard makeCopy() {
        return new MomentousStrike();
    }

}
