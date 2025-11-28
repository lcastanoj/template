package stepDef.hooks;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

import static net.serenitybdd.screenplay.actors.OnStage.*;

public class Hooks {
    @Before
    public void setUp() {
        setTheStage(new OnlineCast());

        WebDriver customDriver = DriverFactory.getDriver();
        theActorCalled("user").whoCan(BrowseTheWeb.with(customDriver));
    }
}
