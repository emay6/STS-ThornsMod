package thornsmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.cards.BaseCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.util.CardStats;

public class Lodestar extends BaseCard {
    public static final String ID = makeID(Lodestar.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );
    private static final int MAGIC = 1;

    public Lodestar() {
        super(ID, info);

        setMagic(MAGIC);
        setExhaust(true, false);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BetterDrawPileToHandAction(magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Lodestar();
    }

}
