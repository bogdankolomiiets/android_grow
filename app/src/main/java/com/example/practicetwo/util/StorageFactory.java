package com.example.practicetwo.util;

import android.content.Context;
import android.view.View;

import com.example.practicetwo.providers.DatabaseProviderImpl;
import com.example.practicetwo.providers.ExternalStorageProvider;
import com.example.practicetwo.providers.InternalStorageProvider;
import com.example.practicetwo.providers.SharedPreferencesProvider;
import com.example.practicetwo.providers.StorageProvider;
import static com.example.practicetwo.util.Constants.SHARE_PREFERENCES_NAME;
import static com.example.practicetwo.util.Constants.STORAGE_PROVIDER;
import static com.example.practicetwo.util.Constants.TAG_DATABASE;
import static com.example.practicetwo.util.Constants.TAG_EXTERNAL;
import static com.example.practicetwo.util.Constants.TAG_INTERNAL;
import static com.example.practicetwo.util.Constants.TAG_SHARED;

public class StorageFactory {
    private static StorageFactory storageFactory;

    private StorageFactory() {
    }

    public static StorageFactory getInstance() {
        if (storageFactory == null) {
            storageFactory = new StorageFactory();
        }
        return storageFactory;
    }

    public StorageProvider getFactory(View view) {
        Context context = view.getContext();
        String provider = context.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE).getString(STORAGE_PROVIDER, TAG_INTERNAL);
        switch (provider) {
            case TAG_SHARED:
                return new SharedPreferencesProvider(view);
            case TAG_EXTERNAL:
                return new ExternalStorageProvider(view);
            case TAG_DATABASE:
                return new DatabaseProviderImpl(view);
            default:
                return new InternalStorageProvider(view);
        }
    }
}
