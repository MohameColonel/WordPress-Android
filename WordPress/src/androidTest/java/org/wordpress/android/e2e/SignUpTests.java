package org.wordpress.android.e2e;

import android.support.test.rule.ActivityTestRule;

import com.github.tomakehurst.wiremock.stubbing.Scenario;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.wordpress.android.e2e.flows.SignupFlow;
import org.wordpress.android.support.BaseTest;
import org.wordpress.android.ui.accounts.LoginMagicLinkInterceptActivity;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import static org.wordpress.android.BuildConfig.E2E_SIGNUP_DISPLAY_NAME;
import static org.wordpress.android.BuildConfig.E2E_SIGNUP_EMAIL;
import static org.wordpress.android.BuildConfig.E2E_SIGNUP_PASSWORD;
import static org.wordpress.android.BuildConfig.E2E_SIGNUP_USERNAME;

public class SignUpTests extends BaseTest {
    @Rule
    public ActivityTestRule<LoginMagicLinkInterceptActivity> mMagicLinkActivityTestRule =
            new ActivityTestRule<>(LoginMagicLinkInterceptActivity.class);

    @Before
    public void setUp() {
        logoutIfNecessary();

        stubFor(get(urlEqualTo("/rest/v1.1/me/"))
                .inScenario("Sign up")
                .whenScenarioStateIs(Scenario.STARTED));
    }

    @Test
    public void signUpWithEmail() {
        SignupFlow signupFlow = new SignupFlow();
        signupFlow.chooseSignupWithEmail();
        signupFlow.enterEmail(E2E_SIGNUP_EMAIL, mMagicLinkActivityTestRule);
        signupFlow.checkEpilogue(
                E2E_SIGNUP_DISPLAY_NAME,
                E2E_SIGNUP_USERNAME);
        signupFlow.enterPassword(E2E_SIGNUP_PASSWORD);
        signupFlow.confirmSignup();
    }
}