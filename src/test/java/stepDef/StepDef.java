package stepDef;

import io.cucumber.java.en.Given;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class StepDef {
    @Given("the browser is launched")
    public void theBrowserIsLaunched() {
        theActorInTheSpotlight()
                .abilityTo(BrowseTheWeb.class)
                .getDriver()
                .get("about:blank");
    }
}
