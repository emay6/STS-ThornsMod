package thornsmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.cards.BaseCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.util.CardStats;

public class QuickenedPace extends BaseCard {
    public static final String ID = makeID(QuickenedPace.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.RARE,
            AbstractCard.CardTarget.SELF,
            0
    );
    private static final int MAGIC = 1;
    public QuickenedPace() {
        super(ID, info);

        setMagic(MAGIC);
        this.upgInnate = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = 0;

        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
            if (!mon.isDeadOrEscaped()) {
                ++count;
            }
        }

        for(int i = 0; i < count * magicNumber; ++i) {
            this.addToBot(new GainEnergyAction(magicNumber));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new QuickenedPace();
    }

}
