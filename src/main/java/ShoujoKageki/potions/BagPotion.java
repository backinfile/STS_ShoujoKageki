package ShoujoKageki.potions;

import ShoujoKageki.ModInfo;
import ShoujoKageki.Res;
import ShoujoKageki.actions.bag.SelectDrawPileToBagAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BagPotion extends BasePotion {
    public static final Logger logger = LogManager.getLogger(BagPotion.class.getName());

    public static final String POTION_ID = ModInfo.makeID(BagPotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BagPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.EYE, PotionEffect.NONE,
                Res.KarenRenderColor.cpy(), Res.KarenRenderColor.cpy(), (Color) null);
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        addToBot(new SelectDrawPileToBagAction(potency, true));
    }

    @Override
    public void initializeData() {
        isThrown = false;
        targetRequired = false;
        potency = getPotency();

        if (this.potency == 1) {
            this.description = potionStrings.DESCRIPTIONS[0];
        } else {
            this.description = potionStrings.DESCRIPTIONS[1] + this.potency + potionStrings.DESCRIPTIONS[2];
        }

        initializeDescription();
    }

    @Override
    public int getPotency(int level) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BagPotion();
    }

}
