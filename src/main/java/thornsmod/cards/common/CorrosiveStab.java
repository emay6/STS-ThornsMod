package thornsmod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.cards.BaseCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.powers.CorrosionPower;
import thornsmod.util.CardStats;

public class CorrosiveStab extends BaseCard {
    public static final String ID = makeID(CorrosiveStab.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public CorrosiveStab() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(m, p, new CorrosionPower(m, p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CorrosiveStab();
    }

}
