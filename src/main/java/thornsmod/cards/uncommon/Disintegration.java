package thornsmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.ThornsMod;
import thornsmod.cards.EchoCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.util.CardStats;

public class Disintegration extends EchoCard {
    public static final String ID = makeID(Disintegration.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    public Disintegration() {
        super(ID, info);

        setDamage(0); // placeholder
        setExhaust(true, false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int actualDamage = 0;
        if (m.hasPower(ThornsMod.makeID("Corrosion")))
            actualDamage = m.getPower(ThornsMod.makeID("Corrosion")).amount;
        addToBot(new DamageAction(m, new DamageInfo(p, actualDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Disintegration();
    }

}
