package ShoujoKageki_Karen.potions;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki_Karen.Res;
import ShoujoKageki_Karen.actions.AwakePotionAction;
import ShoujoKageki.base.BasePotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AwakePotion extends BasePotion {
    public static final Logger logger = LogManager.getLogger(AwakePotion.class.getName());

    public static final String POTION_ID = KarenPath.makeID(AwakePotion.class.getSimpleName());
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
