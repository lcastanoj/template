package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    public static WebDriver getDriver() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Crear un perfil temporal completamente limpio
        String tempProfile = System.getProperty("java.io.tmpdir")
                + "/chromeProfile_" + System.currentTimeMillis();

        options.addArguments("--user-data-dir=" + tempProfile);

        // Incógnito para aislar aún más el password manager
        options.addArguments("--incognito");

        // Headless para CI
        boolean isCI = System.getenv("CI") != null;
        if (isCI) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");

        // Minimiza cualquier intento de Chrome por manejar contraseñas
        options.addArguments("--disable-features=PasswordLeakDetection");
        options.addArguments("--disable-features=PasswordManagerOnboarding");
        options.addArguments("--disable-features=PasswordManagerExtension");
        options.addArguments("--password-store=basic");

        return new ChromeDriver(options);
    }
}
