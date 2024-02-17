package ShoujoKageki_Karen.variables.patch;

import ShoujoKageki.base.BaseRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class DisposableFieldRecordPatch {

    public static final ArrayList<String> disposedCardsCurTurn = new ArrayList<>();

    public static void addDisposedCard(AbstractCard card) {
        if (!disposedCardsCurTurn.contains(card.cardID)) {
            disposedCardsCurTurn.add(card.cardID);
        }

        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic instanceof BaseRelic) ((BaseRelic) relic).triggerOnCardDisposed(card);
        }
    }

    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "applyStartOfCombatLogic"
    )
    public static class ApplyStartOfCombatLogic {
        public static void Prefix(AbstractPlayer __instance) {
            disposedCardsCurTurn.clear();
        }
    }
}
