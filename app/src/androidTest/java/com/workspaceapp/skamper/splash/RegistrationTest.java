package com.workspaceapp.skamper.splash;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.workspaceapp.skamper.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegistrationTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void registrationTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.registerFormButton), withText("Sign up"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.choseModeLayout),
                                        1),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.usernameEditText),
                        childAtPosition(
                                allOf(withId(R.id.inputLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("testUser"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.usernameEditText), withText("testUser"),
                        childAtPosition(
                                allOf(withId(R.id.inputLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.emailEditText),
                        childAtPosition(
                                allOf(withId(R.id.inputLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("testUser@skamper.pl"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.emailEditText), withText("testUser@skamper.pl"),
                        childAtPosition(
                                allOf(withId(R.id.inputLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.passwordEditText),
                        childAtPosition(
                                allOf(withId(R.id.inputLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("aaaaAAAA"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.passwordEditText), withText("aaaaAAAA"),
                        childAtPosition(
                                allOf(withId(R.id.inputLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText6.perform(pressImeActionButton());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.rePasswordEditText),
                        childAtPosition(
                                allOf(withId(R.id.inputLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("aaaaAAAA"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.rePasswordEditText), withText("aaaaAAAA"),
                        childAtPosition(
                                allOf(withId(R.id.inputLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatEditText8.perform(pressImeActionButton());

        pressBack();

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.submitRegisterButton), withText("Register"),
                        childAtPosition(
                                allOf(withId(R.id.forgotloginLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
