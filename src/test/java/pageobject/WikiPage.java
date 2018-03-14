package pageobject;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WikiPage {

    public void openWiki(String path) {
        open("https://ru.wikipedia.org/wiki/" + path);
    }

    public SelenideElement headline() {
        return $(".firstHeading");
    }

    public SelenideElement downloadBtn() {
        return $(".mw-mmv-download-button");
    }

    public SelenideElement downloadGoBtn() {
        return $(".mw-mmv-download-go-button");
    }
}
