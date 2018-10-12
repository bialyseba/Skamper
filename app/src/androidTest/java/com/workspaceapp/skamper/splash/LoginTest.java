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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void loginTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.emailEditText),
                        childAtPosition(
                                allOf(withId(R.id.inputLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("t"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.emailEditText), withText("t"),
                        childAtPosition(
                                allOf(withId(R.id.inputLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.emailEditText), withText("t"),
                        childAtPosition(
                                allOf(withId(R.id.inputLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("uamtest@uam.pl"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.emailEditText), withText("uamtest@uam.pl"),
                        childAtPosition(
                                allOf(withId(R.id.inputLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.passwordEditText),
                        childAtPosition(
                                allOf(withId(R.id.inputLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("aaaaAAAA"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.submitLoginButton), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.forgotloginLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.submitLoginButton), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.forgotloginLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                1),
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
