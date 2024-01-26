package ShoujoKageki.potions;

import ShoujoKageki.ModInfo;
import ShoujoKageki.ModManager;
import ShoujoKageki.Res;
import ShoujoKageki.actions.AwakePotionAction;
import ShoujoKageki.actions.GainCardOrIgnoreAction;
import ShoujoKageki.character.KarenCharacter;
import ShoujoKageki.variables.DisposableVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AwakePotion extends BasePotion {
    public static final Logger logger = LogManager.getLogger(AwakePotion.class.getName());

    public static final String POTION_ID = ModInfo.makeID(AwakePotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public AwakePotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.SPHERE, PotionColor.SWIFT);
        this.labOutlineColor = Res.KarenRenderColor.cpy();
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        addToBot(new AwakePotionAction());
    }

    @Override
    public void initializeData() {
        isThrown = false;
        targetRequired = false;
        potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0];
        initializeDescription();
    }

    @Override
    public int getPotency(int level) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new AwakePotion();
    }
}
