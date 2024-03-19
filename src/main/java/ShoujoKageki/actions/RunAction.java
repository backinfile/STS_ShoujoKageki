package ShoujoKageki.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class RunAction extends AbstractGameAction {
    private final Runnable runnable;

    public RunAction(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void update() {
        runnable.run();
        isDone = true;
    }
}
