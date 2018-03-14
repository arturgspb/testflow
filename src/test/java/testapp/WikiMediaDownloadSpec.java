package testapp;

import org.junit.Test;
import pageobject.WikiPage;
import ru.realweb.meta.Base;

import java.io.File;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WikiMediaDownloadSpec extends Base {

    @Test
    public void wikiInlineSelectors() throws Exception {
        open("https://ru.wikipedia.org/wiki/%D0%98%D0%B3%D1%80%D0%B0_%D0%BF%D1%80%D0%B5%D1%81%D1%82%D0%BE%D0%BB%D0%BE%D0%B2_(%D1%82%D0%B5%D0%BB%D0%B5%D1%81%D0%B5%D1%80%D0%B8%D0%B0%D0%BB)#/media/File:Peter_Dinklage_2012_cropped.jpg");
        $(".mw-mmv-download-button").click();
        File file = $(".mw-mmv-download-go-button").should(visible).download();
        System.out.println("file = " + file.getAbsolutePath());
    }

    @Test
    public void wikiPageObject() throws Exception {
        WikiPage page = new WikiPage();
        page.openWiki("Игра_престолов_(телесериал)#/media/File:Peter_Dinklage_2012_cropped.jpg");
        page.downloadBtn().click();
        File file = page.downloadGoBtn().should(visible).download();
        System.out.println("file = " + file.getAbsolutePath());
    }
}
