package thornsmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.cards.BaseCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.powers.AlchemicalProwessPower;
import thornsmod.util.CardStats;

public class AlchemicalProwess extends BaseCard {
    public static final String ID = makeID(AlchemicalProwess.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );
    private static final int MAGIC = 1;
    private static final int MAGIC_UPG = 1;

    public AlchemicalProwess() {
        super(ID, info);

        setMagic(MAGIC, MAGIC_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AlchemicalProwessPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new AlchemicalProwess();
    }

}
