package thornsmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.cards.EchoCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.util.CardStats;

public class LastStand extends EchoCard {
    public static final String ID = makeID(LastStand.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.RARE,
            AbstractCard.CardTarget.SELF,
            1
    );
    private static final int MAGIC = 7;
    private static final int UP_MAGIC = 2;

    public LastStand() {
        super(ID, info);

        setExhaust(true);
        setMagic(MAGIC,UP_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, p, magicNumber));

        if (this.cardDoEcho) this.echo(p, m);
    }

    public void triggerOnGlowCheck() {
        AbstractPlayer p = AbstractDungeon.player;
        this.setCardDoEcho(p.currentHealth <= (float)p.maxHealth / 4.0F && p.currentHealth > 0);
        this.setEchoGlow();
    }

    @Override
    public AbstractCard makeCopy() {
        return new LastStand();
    }

}
