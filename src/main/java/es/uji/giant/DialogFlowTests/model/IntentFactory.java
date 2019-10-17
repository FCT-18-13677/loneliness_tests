package es.uji.giant.DialogFlowTests.model;

import es.uji.giant.DialogFlowTests.utils.Constants;

public class IntentFactory {
    private SexIntent sexIntent;
    private AgeIntent ageIntent;
    private AloneIntent aloneIntent;
    private Jong1Intent jong1Intent;
    private Jong2Intent jong2Intent;

    public IntentFactory () {
        sexIntent = new SexIntent();
        ageIntent = new AgeIntent();
        aloneIntent = new AloneIntent();
        jong1Intent = new Jong1Intent();
        jong2Intent = new Jong2Intent();
    }

    public Intent getIntent(String activeIntent) {
        switch (activeIntent) {
            case Constants.SEX_INTENT:      return sexIntent;
            case Constants.AGE_INTENT:      return ageIntent;
            case Constants.ALONE_INTENT:    return aloneIntent;
            case Constants.JONG1_INTENT:    return jong1Intent;
            case Constants.JONG2_INTENT:    return jong2Intent;

            default: return null;
        }
    }
}
