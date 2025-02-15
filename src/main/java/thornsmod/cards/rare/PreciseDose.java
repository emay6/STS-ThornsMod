package thornsmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.cards.BaseCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.powers.CorrosionPower;
import thornsmod.util.CardStats;

public class PreciseDose extends BaseCard {
    public static final String ID = makeID(PreciseDose.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.RARE,
            AbstractCard.CardTarget.SELF,
            0
    );
    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = -2;
    public PreciseDose() {
        super(ID, info);

        setMagic(MAGIC, UPGRADE_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new ApplyPowerAction(p, p, new CorrosionPower(p, p, magicNumber)));
        addToBot(new GainEnergyAction(2));
        addToBot(new DrawCardAction(p, 2));
    }

    @Override
    public AbstractCard makeCopy() {
        return new PreciseDose();
    }

}
