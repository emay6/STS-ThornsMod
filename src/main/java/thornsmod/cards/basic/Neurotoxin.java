package thornsmod.cards.basic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.ThornsMod;
import thornsmod.cards.EchoCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.powers.CorrosionPower;
import thornsmod.util.CardStats;

public class Neurotoxin extends EchoCard {
    public static final String ID = makeID(Neurotoxin.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );
    private static final int DMG = 3;
    private static final int MAGIC = 3;
    private static final int UPG_COST = 1;

    public Neurotoxin() {
        super(ID, info);

        setDamage(DMG);
        setMagic(MAGIC);
        setCostUpgrade(UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(m, p, new CorrosionPower(m, p, this.magicNumber)));

        if (this.cardDoEcho) this.echo(p, m);
    }

    public void triggerOnGlowCheck() {
        this.setCardDoEcho(AbstractDungeon.player.stance.ID.equals(ThornsMod.makeID("ThornsMode")));
        this.setEchoGlow();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Neurotoxin();
    }

}
