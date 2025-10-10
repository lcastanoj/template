package tasks;

import org.openqa.selenium.safari.SafariDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OpenDriver implements Task {
    private final String url;
    private final String browser;

    public OpenDriver(String url, String browser) {
        this.url = url;
        this.browser = browser;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Configura el WebDriver según el navegador
        WebDriver driver = setupWebDriver(browser);

        // Asigna el WebDriver al actor
        actor.can(BrowseTheWeb.with(driver));

        // Abre la URL en el navegador
        actor.attemptsTo(
                Open.url(url)
        );
    }

    private WebDriver setupWebDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                // Configura Firefox y maximiza la ventana
                WebDriverManager.firefoxdriver().setup(); // Ejemplo de versión específica
                // Usa la configuración predeterminada
                WebDriver firefoxDriver = new FirefoxDriver(new FirefoxOptions());
                firefoxDriver.manage().window().maximize();
                return firefoxDriver;
            case "edge":
                // Configura Edge y maximiza la ventana
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--remote-allow-origins=*");
                WebDriver edgeDriver = new EdgeDriver(edgeOptions);
                edgeDriver.manage().window().maximize();
                return edgeDriver;
            case "safari":
                // Configura Safari (no necesita configuración adicional)
                return new SafariDriver();
            case "chrome":
            default:
                // Configura Chrome por defecto
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
        }
    }

    public static OpenDriver on(String url, String browser) {
        return new OpenDriver(url, browser);
    }
}
