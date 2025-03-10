package thornsmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thornsmod.cards.BaseCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.powers.CorrosionPower;
import thornsmod.util.CardStats;

public class Incapacitate extends BaseCard {
    public static final String ID = makeID(Incapacitate.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );
    private static final int MAGIC = 1;
    private static final int MAGIC_UPG = 1;

    public Incapacitate() {
        super(ID, info);

        setExhaust(true);
        setMagic(MAGIC, MAGIC_UPG);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new CorrosionPower(p, p, 8)));
        addToBot(new ApplyPowerAction(m, p, new StrengthPower(p, -magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Incapacitate();
    }

}
