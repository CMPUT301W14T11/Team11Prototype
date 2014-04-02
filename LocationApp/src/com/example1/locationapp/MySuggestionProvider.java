package com.example1.locationapp;

import android.content.SearchRecentSuggestionsProvider;

/**
 * This activity is using for searching by date.
 * @author zuo2
 *
 */
public class MySuggestionProvider extends SearchRecentSuggestionsProvider{
	public final static String AUTHORITY = "com.example1.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
        }

}
