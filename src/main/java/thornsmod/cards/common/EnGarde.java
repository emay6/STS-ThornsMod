package thornsmod.cards.common;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.cards.BaseCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.modes.ThornsMode;
import thornsmod.util.CardStats;

public class EnGarde extends BaseCard {
    public static final String ID = makeID(EnGarde.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            2
    );
    private static final int UPG_COST = 1;

    public EnGarde() {
        super(ID, info);
        setSelfRetain(true);
        setExhaust(true);
        setCostUpgrade(UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(new ThornsMode()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnGarde();
    }

}
