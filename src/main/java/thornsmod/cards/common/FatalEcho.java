package thornsmod.cards.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.actions.FatalEchoAction;
import thornsmod.cards.EchoCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.util.CardStats;

public class FatalEcho extends EchoCard {
    public static final String ID = makeID(FatalEcho.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            1
    );
    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;

    public FatalEcho() {
        super(ID, info);
        this.isMultiDamage = true;

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FatalEchoAction(p, this));
    }

    public void triggerOnGlowCheck() {
        // cant really easily have a glow check for fatal, so no glow and just do check in action
    }

    @Override
    public AbstractCard makeCopy() {
        return new FatalEcho();
    }

}