import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.List;



public class test {
    public static void main(String[] args) throws IOException {


        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        final String url = "https://www.onlinetrade.ru/";
        String searchUrl = searchPageUrl(webClient, url);

        parsePages(webClient, searchUrl, 2);
    }

    private  static String searchPageUrl(WebClient webClient, String url) throws IOException {
        if (webClient == null)
            return "";
        final HtmlPage page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(2000);

        List<HtmlTextInput> searchInput = page.getByXPath("//input[@class='header__search__inputText js__header__search__inputText']");
        searchInput.get(0).setText("ps5");

        List<HtmlSubmitInput> searchInputList = page.getByXPath("//input[@class='header__search__inputGogogo']");
        HtmlPage searchPage = searchInputList.get(0).click();
        return searchPage.getUrl().toString();
    }

    private static void parsePages (WebClient webClient, String searchUrl, int countPages) throws IOException {
        if (webClient == null)
            return;
        int i = 0;
        String searchNumPage = searchUrl;
        while (i < countPages) {

            HtmlPage searchPage = webClient.getPage(searchNumPage);
            List<HtmlAnchor> items = searchPage.getByXPath("//a[@class='indexGoods__item__name']");
            List<HtmlElement> prices = searchPage.getByXPath("//span[@class='price regular']");
            for (int j = 0; j < items.size(); j++) {
                System.out.println(items.get(j).getTextContent() + "    " + prices.get(j).getTextContent());
            }
            i++;
            searchNumPage = searchUrl + "&page=" + i;
        }
    }
}
