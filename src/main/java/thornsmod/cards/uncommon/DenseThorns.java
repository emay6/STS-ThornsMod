package thornsmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.cards.BaseCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.modes.ThornsMode;
import thornsmod.powers.AncestralHealingPower;
import thornsmod.powers.DenseThornsPower;
import thornsmod.util.CardStats;

public class DenseThorns extends BaseCard {
    public static final String ID = makeID(DenseThorns.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int MAGIC = 4;
    private static final int MAGIC_UPG = 2;

    public DenseThorns() {
        super(ID, info);
        setMagic(MAGIC, MAGIC_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DenseThornsPower(p, this.magicNumber)));
        addToBot(new ChangeStanceAction(new ThornsMode()));
    }


    @Override
    public AbstractCard makeCopy() {
        return new DenseThorns();
    }

}