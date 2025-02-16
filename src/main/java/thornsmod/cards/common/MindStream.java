package thornsmod.cards.common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.cards.BaseCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.util.CardStats;

public class MindStream extends BaseCard {
    public static final String ID = makeID(MindStream.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 1;
    private static final int MAGIC_UPG = 1;

    public MindStream() {
        super(ID, info);

        setMagic(MAGIC, MAGIC_UPG);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(1));
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MindStream();
    }

}