package com.example.practicetwo.util;

import android.content.Context;
import com.example.practicetwo.providers.DatabaseProviderImpl;
import com.example.practicetwo.providers.ExternalStorageProviderImpl;
import com.example.practicetwo.providers.InternalStorageProviderImpl;
import com.example.practicetwo.providers.SharedPreferencesProviderImpl;
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

    public StorageProvider getFactory(Context context) {
        String provider = context.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE).getString(STORAGE_PROVIDER, TAG_INTERNAL);
        switch (provider) {
            case TAG_SHARED:
                return new SharedPreferencesProviderImpl(context);
            case TAG_EXTERNAL:
                return new ExternalStorageProviderImpl(context);
            case TAG_DATABASE:
                return new DatabaseProviderImpl(context);
            default:
                return new InternalStorageProviderImpl(context);
        }
    }
}
