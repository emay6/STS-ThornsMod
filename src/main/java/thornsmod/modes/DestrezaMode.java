package thornsmod.modes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.DamageAllButOneEnemyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import thornsmod.ThornsMod;
import thornsmod.character.ThornsCharacter;
import thornsmod.powers.ProtectiveSpikesPower;

import java.util.ArrayList;

public class DestrezaMode extends AbstractStance {
    public static final String MODE_ID = ThornsMod.makeID("DestrezaMode");
    private static final StanceStrings modeString;
    public static final float DESTREZA_AOE = 0.25f;

    public DestrezaMode() {
        this.ID = MODE_ID;
        this.name = modeString.NAME;
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = modeString.DESCRIPTION[0];
    }

    // stolen from watcher stance, change later
    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.04F;
                AbstractDungeon.effectsQueue.add(new WrathParticleEffect());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Wrath"));
        }
    }

    public void onEnterStance() {
        CardCrawlGame.sound.play(ThornsMod.makeID("MODE_ENTER_DESTREZA"));

        // also stolen from watcher stance
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.RED, true));

        ThornsCharacter.destrezaActive = true;
        ++ThornsCharacter.timesDestrezaEntered;
    }

    public void onExitStance() {
        ThornsCharacter.destrezaAttackCounter = 0;
        ThornsCharacter.destrezaActive = false;
    }

    public void atStartOfTurn() {
        if (ThornsCharacter.timesDestrezaEntered <= 1) {
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            damage *= 2.0f;
            // see DestrezaPatch for AoE damage implementation
        }

        return damage;
    }

    static {
        modeString = CardCrawlGame.languagePack.getStanceString(MODE_ID);
    }
}
