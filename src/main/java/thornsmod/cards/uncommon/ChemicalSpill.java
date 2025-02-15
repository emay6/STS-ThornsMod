package thornsmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import thornsmod.cards.BaseCard;
import thornsmod.character.ThornsCharacter;
import thornsmod.powers.CorrosionPower;
import thornsmod.util.CardStats;

import java.util.Iterator;

public class ChemicalSpill extends BaseCard {
    public static final String ID = makeID(ChemicalSpill.class.getSimpleName());

    // basic card info
    private static final CardStats info = new CardStats(
            ThornsCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            2
    );
    private static final int MAGIC = 10;
    private static final int UPG_MAGIC = 14;


    public ChemicalSpill() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Lose 2 dex
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, -2), -2));
        // Apply corrosion to all enemies
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var3.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var3.next();
            addToBot(new ApplyPowerAction(mo, p, new CorrosionPower(mo, p, magicNumber), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ChemicalSpill();
    }

}
