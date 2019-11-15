package es.uji.giant.DialogFlowTests.model;

import es.uji.giant.DialogFlowTests.utils.Constants;

public class IntentFactory {
    private WelcomeIntent welcomeIntent;
    private CodeIntent codeIntent;
    private SexIntent sexIntent;
    private AgeIntent ageIntent;
    private AloneIntent aloneIntent;
    private Jong1Intent jong1Intent;
    private Jong2Intent jong2Intent;
    private Jong3Intent jong3Intent;
    private Jong4Intent jong4Intent;
    private Jong5Intent jong5Intent;
    private Jong6Intent jong6Intent;
    private UCLA1Intent ucla1Intent;
    private UCLA2Intent ucla2Intent;
    private UCLA3Intent ucla3Intent;
    private UserCommentIntent userCommentIntent;

    public IntentFactory () {
        welcomeIntent = new WelcomeIntent();
        codeIntent = new CodeIntent();
        sexIntent = new SexIntent();
        ageIntent = new AgeIntent();
        aloneIntent = new AloneIntent();
        jong1Intent = new Jong1Intent();
        jong2Intent = new Jong2Intent();
        jong3Intent = new Jong3Intent();
        jong4Intent = new Jong4Intent();
        jong5Intent = new Jong5Intent();
        jong6Intent = new Jong6Intent();
        ucla1Intent = new UCLA1Intent();
        ucla2Intent = new UCLA2Intent();
        ucla3Intent = new UCLA3Intent();
        userCommentIntent = new UserCommentIntent();
    }

    public Intent getIntent(String activeIntent) {
        switch (activeIntent) {
            case Constants.WELCOME_INTENT:  return welcomeIntent;
            case Constants.CODE_INTENT:     return codeIntent;
            case Constants.SEX_INTENT:      return sexIntent;
            case Constants.AGE_INTENT:      return ageIntent;
            case Constants.ALONE_INTENT:    return aloneIntent;
            case Constants.JONG1_INTENT:    return jong1Intent;
            case Constants.JONG2_INTENT:    return jong2Intent;
            case Constants.JONG3_INTENT:    return jong3Intent;
            case Constants.JONG4_INTENT:    return jong4Intent;
            case Constants.JONG5_INTENT:    return jong5Intent;
            case Constants.JONG6_INTENT:    return jong6Intent;
            case Constants.UCLA1_INTENT:    return ucla1Intent;
            case Constants.UCLA2_INTENT:    return ucla2Intent;
            case Constants.UCLA3_INTENT:    return ucla3Intent;
            case Constants.USER_COMMENT_INTENT:    return userCommentIntent;

            default: return null;
        }
    }
}
