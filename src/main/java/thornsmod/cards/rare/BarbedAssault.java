package thornsmod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import thornsmod.actions.DoubleCorrosionAction;
import thornsmod.cards.BaseCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.util.CardStats;

public class BarbedAssault extends BaseCard {
    public static final String ID = makeID(BarbedAssault.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );
    private static final int DMG = 6;
    private static final int UPG_DMG = 3;

    public BarbedAssault() {
        super(ID, info);

        setDamage(DMG, UPG_DMG);
        setMagic(0);
        this.exhaust = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // remove thorns
        AbstractPower thorns = AbstractDungeon.player.getPower("Thorns");
        if (thorns != null) {
            setMagic(thorns.amount);
        }
        addToBot(new RemoveSpecificPowerAction(p, p, p.getPower("Thorns")));
        // deal damage equal to thorns
        for(int i = 0; i < magicNumber; ++i) {
            this.addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BarbedAssault();
    }

}