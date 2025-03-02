package thornsmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import thornsmod.ThornsMod;
import thornsmod.actions.CorrosionLoseHpAction;

public class CorrosionPower extends BasePower implements HealthBarRenderPower {
    public static final String POWER_ID = ThornsMod.makeID("Corrosion");
    private static final PowerStrings powerStrings;

    public CorrosionPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.DEBUFF, true, owner, source, amount);
        if (this.amount >= 9999) {
            this.amount = 9999;
        }

        this.updateDescription();
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play(ThornsMod.makeID("POWER_CORROSION"), 0.05f);
    }

    public void updateDescription() {
        if (this.owner != null && !this.owner.isPlayer) {
            this.description = powerStrings.DESCRIPTIONS[1] + this.amount + powerStrings.DESCRIPTIONS[2];
        } else {
            this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[2];
        }
    }

    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            // replace poison attack effect later?
            this.addToBot(new CorrosionLoseHpAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.POISON));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }

    @Override
    public int getHealthBarAmount() {
        return this.amount;
    }

    @Override
    public Color getColor() {
        return Color.GOLD.cpy();
    }
}
