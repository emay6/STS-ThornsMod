package thornsmod.cards.common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.cards.EchoCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.util.CardStats;

public class Horizon extends EchoCard {
    public static final String ID = makeID(Horizon.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.SELF,
            1
    );
    private static final int MAGIC = 2;

    public Horizon() {
        super(ID, info);

        setInnate(false, true);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, magicNumber));

        if (this.cardDoEcho) this.echo(p, m);
    }

    public void triggerOnGlowCheck() {
        this.setCardDoEcho(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 0);
        this.setEchoGlow();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Horizon();
    }

}