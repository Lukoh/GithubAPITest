package com.goforer.mychallenge.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public enum ActivityCaller {
    INSTANCE;

    public static final String EXTRA_SELECTED_ITEM_POSITION = "cupang:selected_item_position";
    public static final String EXTRA_PHOTOS_LIST = "cupang:photos_list";
    public static final String EXTRA_PHOTOS_ITEM_POSITION = "cupang:photos_items_position";

    public static final int FROM_PHOTOS_LIST = 0;
    public static final int SELECTED_ITEM_POSITION = 1000;

    public Intent createIntent(Context context, Class<?> cls, boolean isNewTask) {
        Intent intent = new Intent(context, cls);

        if (isNewTask && !(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return intent;
    }

    private Intent createIntent(String action) {
        Intent intent = new Intent(action);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }
}
