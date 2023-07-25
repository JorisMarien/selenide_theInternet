package org.internet;

import com.codeborne.selenide.*;
import com.codeborne.selenide.commands.SelectOptionByTextOrIndex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;

import java.awt.*;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TheInternet {
    @Before
    public void setup() {
        Configuration.baseUrl = "https://the-internet.herokuapp.com";
    }

    @After
    public void teardown() {
        Selenide.closeWindow();
    }

    @Test
    public void login() {
        open("/login");

        $(Selectors.byId("username")).setValue("tomsmith");
        $(Selectors.byId("password")).setValue("SuperSecretPassword!");
        $(Selectors.byClassName("fa-sign-in")).click();

        String loginSuccessText = $(Selectors.byId("flash")).getText();
        loginSuccessText = loginSuccessText.substring(0, loginSuccessText.length() - 2);
        assertEquals("You logged into a secure area!", loginSuccessText);
        $(Selectors.byClassName("icon-signout")).click();
    }

    @Test
    public void ABTest() {
        open("/abtest");
        String title = $(Selectors.byCssSelector("h3")).getText();
        assertEquals("A/B Test Variation 1", title);
    }

    @Test
    public void AddRemoveTest() {
        open("/add_remove_elements/");
        $(Selectors.byCssSelector("button[onclick*='addElement()']")).click();
        $(Selectors.byCssSelector("button[onclick*='deleteElement()']")).click();
        try {
            $(Selectors.byCssSelector("button[onclick*='deleteElement()']"));
        } catch (org.openqa.selenium.NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    public void checkboxTest() {
        open("/checkboxes");
        SelenideElement checkBox = $(Selectors.byCssSelector("input"));
        checkBox.click();
        assertEquals("true", checkBox.getAttribute("checked"));

    }

    @Test
    public void keyPressesTest() {
        open("/key_presses");
        $(Selectors.byId("target")).pressEscape();

        String pressedKey = $(Selectors.byId("result")).getText();
        assertEquals("You entered: ESCAPE", pressedKey);
    }

    @Test
    public void dropdown() {
        open("/dropdown");
        String option = "Option 1";
        $(Selectors.byId("dropdown")).selectOption(1);
        String selectedOption = $(Selectors.byId("dropdown")).getSelectedOptionText();
        assertEquals(selectedOption, option);
    }
    @Test
    public void hoverTest(){
        open("/hovers");
        $(Selectors.byClassName("figure")).hover();
        String subtitle = $(Selectors.byCssSelector("h5")).getText();
        assertEquals("name: user1", subtitle);
    }
    @Test
    public void sliderTest(){
        open("/horizontal_slider");
        SelenideElement slider = $(Selectors.byCssSelector("input"));
        slider.click();
        slider.press(Keys.ARROW_RIGHT);


    }
    @Test
    public void dragAndDrop() {
        open("/drag_and_drop");
        SelenideElement from = $(Selectors.byCssSelector("header:contains(A)"));
        SelenideElement to = $(Selectors.byCssSelector("header:contains(B)"));
        from.dragAndDrop((DragAndDropOptions) to);
    }
}
