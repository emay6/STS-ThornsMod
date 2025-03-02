package thornsmod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.cards.EchoCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.util.CardStats;

public class CleanCut extends EchoCard {
    public static final String ID = makeID(CleanCut.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );
    private static final int DMG = 6;
    private static final int UPG_DMG = 1;
    private static final int MAG = 3;
    private static final int UPG_MAG = 1;

    public CleanCut() {
        super(ID, info);

        setDamage(DMG, UPG_DMG);
        setMagic(MAG, UPG_MAG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }

        if (this.cardDoEcho) this.echo(p, m);
    }

    public void triggerOnGlowCheck () {
        boolean echo = true;

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == CardType.ATTACK && c != this) {
                echo = false;
                break;
            }
        }

        if (echo) {
            this.setCardDoEcho(true);
            this.setEchoGlow();
        } else {
            this.setCardDoEcho(false);
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CleanCut();
    }

}
