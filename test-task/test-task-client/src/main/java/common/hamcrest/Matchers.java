package common.hamcrest;


import common.hamcrest.matchers.*;
import org.hamcrest.Matcher;

public class Matchers {

    private Matchers() {
    }

    public static <T> IsNotMatcher<T> not(Matcher<T> matcher) {
        return new IsNotMatcher<>(matcher);
    }

    public static IsDisplayedMatcher displayed() {
        return new IsDisplayedMatcher();
    }

    public static IsEnabledMatcher enabled() {
        return new IsEnabledMatcher();
    }

    public static StringEqualMatcher equalTo(String text) {
        return new StringEqualMatcher(text);
    }

    public static StringEqualValMatcher equalValTo(String text) {
        return new StringEqualValMatcher(text);
    }

    public static TextMatcher text(String text) {
        return new TextMatcher(equalTo(text));
    }

    public static StringContainsMatcher containsString(String text) {
        return new StringContainsMatcher(text);
    }

    public static StringContainsAttributeMatcher containsString(String attribute, String text) {
        return new StringContainsAttributeMatcher(attribute, text);
    }

    public static IsEmptyMatcher isEmpty() {
        return new IsEmptyMatcher();
    }

    public static LengthMatcher equalsLength(int length) {
        return new LengthMatcher(length);
    }

    public static IsSelectedMatcher selected() {
        return new IsSelectedMatcher();
    }

    public static RegExpMatcher regexpMatches(String pattern) {
        return new RegExpMatcher(pattern);
    }

    public static StringEqualIgnoreCaseMatcher equalIgnoreCase(String text) {
        return new StringEqualIgnoreCaseMatcher(text);
    }
}
