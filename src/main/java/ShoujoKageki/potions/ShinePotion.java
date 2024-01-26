package ShoujoKageki.potions;

import ShoujoKageki.Log;
import ShoujoKageki.ModInfo;
import ShoujoKageki.ModManager;
import ShoujoKageki.Res;
import ShoujoKageki.actions.GainCardOrIgnoreAction;
import ShoujoKageki.variables.DisposableVariable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.SacredBark;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShinePotion extends BasePotion {
    public static final Logger logger = LogManager.getLogger(ShinePotion.class.getName());

    public static final String POTION_ID = ModInfo.makeID(ShinePotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ShinePotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.CARD, PotionColor.SWIFT);
        this.labOutlineColor = Res.KarenRenderColor.cpy();
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        List<AbstractCard> collect = ModManager.allModCards.stream()
                .filter(c -> DisposableVariable.isDisposableCard(c) && !c.hasTag(AbstractCard.CardTags.HEALING))
                .collect(Collectors.toList());
        ArrayList<AbstractCard> toSelect = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            while (true) {
                int rnd = AbstractDungeon.cardRng.random(collect.size() - 1);
                AbstractCard card = collect.get(rnd);
                if (!toSelect.contains(card)) {
                    toSelect.add(card);
                    break;
                }
            }
        }
        addToBot(new GainCardOrIgnoreAction(toSelect, potency > 1, true));
//        Log.logger.info("gold ============== " + Color.GOLD.r + " " + Color.GOLD.g + " " + Color.GOLD.b + " " + Color.GOLD.a);
    }

    @Override
    public void initializeData() {
        isThrown = false;
        targetRequired = false;
        potency = getPotency();

        if (potency == 1) {
            this.description = potionStrings.DESCRIPTIONS[0];
        } else {
            this.description = potionStrings.DESCRIPTIONS[1];
        }

        initializeDescription();
    }

    @Override
    public int getPotency(int level) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ShinePotion();
    }

}
