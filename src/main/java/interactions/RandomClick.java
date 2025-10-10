package interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.core.pages.WebElementFacade;

import java.util.List;
import java.util.Random;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RandomClick implements Interaction {

    private final Target target;
    private final int maxClicks;

    public RandomClick(Target target, int maxClicks) {
        this.target = target;
        this.maxClicks = maxClicks;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Random random = new Random();

        int numberOfClicks = random.nextInt(maxClicks) + 1;
        System.out.println("Number of clicks to perform: " + numberOfClicks);

        for (int i = 0; i < numberOfClicks; i++) {
            List<WebElementFacade> elements = target.resolveAllFor(actor);
            if (!elements.isEmpty()) {
                int randomItem = random.nextInt(elements.size());
                elements.get(randomItem).click();
            } else {
                System.out.println("The list of elements is empty.");
                break;
            }
        }
    }

    public static Performable on(Target target, int maxClicks) {
        return instrumented(RandomClick.class, target, maxClicks);
    }
}