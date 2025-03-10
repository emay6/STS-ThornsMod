package thornsmod.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.actions.IntegratedStrategyAction;
import thornsmod.cards.BaseCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.util.CardStats;

public class IntegratedStrategy extends BaseCard {
    public static final String ID = makeID(IntegratedStrategy.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );
    private static final int DAMAGE = 12;
    private static final int DAMAGE_UPG = 4;

    public IntegratedStrategy() {
        super(ID, info);

        setDamage(DAMAGE, DAMAGE_UPG);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new IntegratedStrategyAction(m, p, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new IntegratedStrategy();
    }

}
