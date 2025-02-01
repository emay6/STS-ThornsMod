package thornsmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.character.ThornsCharacter;
import thornsmod.modes.ThornsMode;
import thornsmod.util.CardStats;

public class ThornProtection extends BaseCard {
    public static final String ID = makeID(ThornProtection.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            2
    );
    private static final int UPG_COST = 1;

    public ThornProtection() {
        super(ID, info);

        setCostUpgrade(UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(new ThornsMode()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ThornProtection();
    }

}
