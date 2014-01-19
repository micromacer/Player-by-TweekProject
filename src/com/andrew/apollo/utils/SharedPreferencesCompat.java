/*
 * Copyright (C) 2010 The Android Open Source Project Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.andrew.apollo.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection utils to call {@link SharedPreferences.Editor} apply() when
 * possible, falling back to commit when apply isn't available.
 */
public final class SharedPreferencesCompat {

    private final static Method mApplyMethod = findApplyMethod();

    /**
     * @return The apply() method from {@link SharedPreferences.Editor}.
     */
    private final static Method findApplyMethod() {
        try {
            final Class<Editor> class1 = SharedPreferences.Editor.class;
            return class1.getMethod("apply");
        } catch (final NoSuchMethodException ignored) {
        }
        return null;
    }

    /**
     * @param editor The {@link SharedPreferences.Editor} to use.
     */
    public static void apply(final SharedPreferences.Editor editor) {
        if (mApplyMethod != null) {
            try {
                mApplyMethod.invoke(editor);
                return;
            } catch (final InvocationTargetException ignored) {
            } catch (final IllegalAccessException ignored) {
            }
        }
        editor.commit();
    }
}
