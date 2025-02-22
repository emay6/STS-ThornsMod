package thornsmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.cards.BaseCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.powers.ExplosionArtsPower;
import thornsmod.powers.FreeSpiritedPower;
import thornsmod.util.CardStats;

public class FreeSpirited extends BaseCard {
    public static final String ID = makeID(FreeSpirited.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;
    private static final int MAGIC_UPG = 1;

    public FreeSpirited() {
        super(ID, info);
        setMagic(MAGIC, MAGIC_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FreeSpiritedPower(p, this.magicNumber)));
    }


    @Override
    public AbstractCard makeCopy() {
        return new FreeSpirited();
    }

}