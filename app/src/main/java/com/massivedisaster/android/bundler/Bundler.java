package com.massivedisaster.android.bundler;
import java.lang.reflect.Field;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by Tiago Ribeiro on 11/11/2014.
 */
public final class Bundler {

    private static final String PARAM_PREFIX = "%s_%s";

    public static Bundle toBundle(Bundle bundle, String prefix, Object instance){

        if(instance == null)
            throw new IllegalArgumentException("Instance cannot be null");

        if(bundle == null)
            bundle = new Bundle();

        for(Field field : instance.getClass().getDeclaredFields()){

            field.setAccessible(true);

            /*Map mapAnnotation = field.getAnnotation(Map.class);

            if(mapAnnotation != null && !mapAnnotation.map())
                continue;
*/
            if(field.getType().isPrimitive()) {

                if (field.getType() == int.class) {
                    try {
                        bundle.putInt(getKey(prefix, field.getName()), (Integer) field.get(instance));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }else if (field.getType() == double.class) {
                    try {
                        bundle.putDouble(getKey(prefix, field.getName()), (Double) field.get(instance));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }else if (field.getType() == long.class) {
                    try {
                        bundle.putLong(getKey(prefix, field.getName()), (Long) field.get(instance));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }else if (field.getType() == short.class) {
                    try {
                        bundle.putShort(getKey(prefix, field.getName()), (Short) field.get(instance));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }else if (field.getType() == char.class) {
                    try {
                        bundle.putChar(getKey(prefix, field.getName()), (Character) field.get(instance));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }else if (field.getType() == float.class) {
                    try {
                        bundle.putFloat(getKey(prefix, field.getName()), (Float) field.get(instance));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }else if (field.getType() == boolean.class) {
                    try {
                        bundle.putBoolean(getKey(prefix, field.getName()), (Boolean) field.get(instance));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }
            }else{
                if (field.getType() == String.class) {
                    try {
                        bundle.putString(getKey(prefix, field.getName()), (String) field.get(instance));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }else{
                    try {
                        toBundle(bundle, getKey(prefix, field.getName()), field.get(instance));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }
            }
        }

        return bundle;
    }

    public static <T> T fromBundle(Bundle bundle, String prefix, Class<T> classType){

        T instance;

        try {
            instance = classType.newInstance();
        }catch (Exception e){
            Log.e(Bundler.class.getName(), String.format("Class %s must have an empty constructor.", classType.getName()));
            return null;
        }

        for(Field field : classType.getDeclaredFields()){

            field.setAccessible(true);

            if(field.getType().isPrimitive()) {
                if (field.getType() == int.class) {
                    try {
                        field.set(instance, bundle.getInt(getKey(prefix, field.getName())));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }else if (field.getType() == double.class) {
                    try {
                        field.set(instance, bundle.getDouble(getKey(prefix, field.getName())));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }else if (field.getType() == long.class) {
                    try {
                        field.set(instance, bundle.getLong(getKey(prefix, field.getName())));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }else if (field.getType() == short.class) {
                    try {
                        field.set(instance, bundle.getShort(getKey(prefix, field.getName())));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }else if (field.getType() == char.class) {
                    try {
                        field.set(instance, bundle.getChar(getKey(prefix, field.getName())));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }else if (field.getType() == float.class) {
                    try {
                        field.set(instance, bundle.getFloat(getKey(prefix, field.getName())));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }else if (field.getType() == boolean.class) {
                    try {
                        field.set(instance, bundle.getBoolean(getKey(prefix, field.getName())));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }
            }else{
                if (field.getType() == String.class) {
                    try {
                        field.set(instance, bundle.getString(getKey(prefix, field.getName())));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }else{
                    try {
                        field.set(instance, fromBundle(bundle, getKey(prefix, field.getName()), field.getType()));
                        toBundle(bundle, getKey(prefix, field.getName()), field.get(instance));
                    } catch (IllegalAccessException e) {
                        Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                    }
                }
            }
        }


        return instance;
    }

    private static String getKey(String prefix, String fieldName){
        return String.format(PARAM_PREFIX, prefix, fieldName);
    }
}