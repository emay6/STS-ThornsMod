package thornsmod.modes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import thornsmod.ThornsMod;
import thornsmod.powers.ProtectiveSpikesPower;

public class ThornsMode extends AbstractStance {
    public static final String MODE_ID = ThornsMod.makeID("ThornsMode");
    private static final StanceStrings modeString;

    public ThornsMode() {
        this.ID = MODE_ID;
        this.name = modeString.NAME;
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = modeString.DESCRIPTION[0];
    }

    // stolen from calm stance, change later
    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.04F;
                AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Calm"));
        }
    }

    public void onEnterStance() {
        CardCrawlGame.sound.play(ThornsMod.makeID("SKILL_ACTIVATE"));

        // also stolen from calm stance
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SKY, true));

        AbstractPlayer player = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, 10));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ProtectiveSpikesPower(player)));
    }

    public void onExitStance() {
        AbstractPlayer player = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(player, player, player.getPower(ThornsMod.makeID("ProtectiveSpikes"))));
    }

    public void onPlayCard(AbstractCard card) {
        if (card.type == AbstractCard.CardType.ATTACK) AbstractDungeon.actionManager.addToTop(new ChangeStanceAction("Neutral"));
        super.onPlayCard(card);
    }

    static {
        modeString = CardCrawlGame.languagePack.getStanceString(MODE_ID);
    }
}
