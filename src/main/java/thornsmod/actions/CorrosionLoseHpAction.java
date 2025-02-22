package thornsmod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import thornsmod.ThornsMod;

// basically just copied from equivalent poison action
public class CorrosionLoseHpAction extends AbstractGameAction {
    // max action duration...?
    private static final float DURATION = 0.33f;

    public CorrosionLoseHpAction(AbstractCreature target, AbstractCreature source, int amount, AttackEffect effect) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = DURATION;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            this.isDone = true;
        } else {
            if (this.duration == 0.33F && this.target.currentHealth > 0) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            }

            this.tickDuration();
            if (this.isDone) {
                if (this.target.currentHealth > 0) {
                    this.target.tint.color = Color.GOLD.cpy();
                    this.target.tint.changeColor(Color.WHITE.cpy());
                    this.target.damage(new DamageInfo(this.source, this.amount, DamageInfo.DamageType.HP_LOSS));
                }

                AbstractPower p = this.target.getPower(ThornsMod.makeID("Corrosion"));
                if (p != null) {
                    p.amount /= 2;
                    if (p.amount == 0) {
                        this.target.powers.remove(p);
                        if (AbstractDungeon.player.hasPower(ThornsMod.makeID("ExplosionArtsPower"))) {
                            this.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, AbstractDungeon.player.getPower(ThornsMod.makeID("ExplosionArtsPower")).amount, DamageInfo.DamageType.THORNS, AttackEffect.FIRE));
                        }
                    } else {
                        p.updateDescription();
                    }
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }

                // progress action duration or something? idk
                this.addToTop(new WaitAction(0.1f));
            }
        }
    }
}
