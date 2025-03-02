package thornsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import thornsmod.cards.EchoCard;

import java.util.ArrayList;

public class FatalEchoAction extends AbstractGameAction {
    private EchoCard card;
    private boolean firstFrame;
    private boolean hasEchoed;

    public FatalEchoAction(AbstractCreature source, EchoCard card) {
        this.source = source;
        this.card = card;
        this.actionType = ActionType.DAMAGE;
        this.damageType = DamageInfo.DamageType.NORMAL;
        this.attackEffect = AttackEffect.SLASH_VERTICAL;
        this.firstFrame = true;
        this.hasEchoed = false;
    }

    // rehash of damageallenemies action with echo check for fatal
    public void update() {
        int i;
        ArrayList<AbstractMonster> monsterList = AbstractDungeon.getCurrRoom().monsters.monsters;;
        if (this.firstFrame) {
            boolean sfxPlayed = false;
            for(i = 0; i < monsterList.size(); ++i) {
                if (!(monsterList.get(i)).isDying && monsterList.get(i).currentHealth > 0 && !monsterList.get(i).isEscaping) {
                    if (sfxPlayed) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(monsterList.get(i).hb.cX, monsterList.get(i).hb.cY, this.attackEffect, true));
                    } else {
                        sfxPlayed = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(monsterList.get(i).hb.cX, monsterList.get(i).hb.cY, this.attackEffect));
                    }
                }
            }

            this.firstFrame = false;
        }

        this.tickDuration();

        if (this.isDone) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                p.onDamageAllEnemies(this.card.multiDamage);
            }

            for(i = 0; i < monsterList.size(); ++i) {
                AbstractMonster m = monsterList.get(i);
                if (!m.isDeadOrEscaped()) {
                    m.damage(new DamageInfo(this.source, this.card.multiDamage[i], this.damageType));
                    if (!this.hasEchoed && m.isDying || m.currentHealth <= 0 && !m.halfDead) {
                        this.hasEchoed = true;
                    }
                }
            }

            if (this.hasEchoed) {
                this.card.echo((AbstractPlayer) this.source, null);
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(0.1F));
            }
        }
    }
}
