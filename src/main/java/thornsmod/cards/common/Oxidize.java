package thornsmod.cards.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.ThornsMod;
import thornsmod.actions.OxidizeAction;
import thornsmod.cards.EchoCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.util.CardStats;

import java.util.Iterator;

public class Oxidize extends EchoCard {
    public static final String ID = makeID(Oxidize.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.ENEMY,
            1
    );

    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 2;

    public Oxidize() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new OxidizeAction(m, new DamageInfo(p, this.magicNumber)));

        if (this.cardDoEcho) this.echo(p, m);
    }

    public void triggerOnGlowCheck() {
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster)var1.next();
            if (!m.isDeadOrEscaped() && m.hasPower(ThornsMod.makeID("Corrosion"))) {
                this.setCardDoEcho(true);
                this.setEchoGlow();
                break;
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Oxidize();
    }

}
