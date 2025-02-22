package thornsmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thornsmod.EchoTag;
import thornsmod.cards.EchoCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.util.CardStats;

public class HonedBlade extends EchoCard {
    public static final String ID = makeID(HonedBlade.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );
    private static final int DAMAGE = 8;
    private static final int MAGIC = 3;
    private static final int MAGIC_UPG = 1;

    public HonedBlade() {
        super(ID, info);
        setMagic(MAGIC, MAGIC_UPG);

        this.update();
    }

    private int getNumEchoes() {
        int num = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.hasTag(EchoTag.ECHOED_CARD)) num++;
        }

        return num;
    }

    @Override
    public void update() {
        int numEchoes = getNumEchoes();
        if (numEchoes > 0) {
            this.baseDamage = DAMAGE + (this.magicNumber * getNumEchoes());
            this.upgradedDamage = true;
        } else {
            this.baseDamage = DAMAGE;
            this.upgradedDamage = false;
        }
        super.update();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public AbstractCard makeCopy() {
        return new HonedBlade();
    }

}
