package com.massivedisaster.android.bundler;

import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Created by Tiago Ribeiro on 11/11/2014.
 */
public final class Bundler {

    private static final String PARAM_PREFIX = "%s.%s";
    private static final String PARAM_INSTANTIATED = "INSTANTIATED";
    private static final String PARAM_COUNTER = "COUNTER";

    public static Bundle toBundle(Bundle bundle, String prefix, Object instance){

        if(bundle == null)
            bundle = new Bundle();

        if(instance == null)
            return bundle;



        for(Field field : instance.getClass().getDeclaredFields()){

            field.setAccessible(true);

            /*Map mapAnnotation = field.getAnnotation(Map.class);

            if(mapAnnotation != null && !mapAnnotation.map())
                continue;
*/

            if (field.getGenericType() == int.class  || field.getGenericType() == Integer.class|| field.getType().getComponentType() == int.class || field.getType().getComponentType() == Integer.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putIntArray(getKey(prefix, field.getName()), (int[]) field.get(instance));
                    }else{
                        bundle.putInt(getKey(prefix, field.getName()), (Integer) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == double.class || field.getGenericType() == Double.class || field.getType().getComponentType() == double.class || field.getType().getComponentType() == Double.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putDoubleArray(getKey(prefix, field.getName()), (double[]) field.get(instance));
                    }else{
                        bundle.putDouble(getKey(prefix, field.getName()), (Double) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == long.class || field.getGenericType() == Long.class || field.getType().getComponentType() == long.class || field.getType().getComponentType() == Long.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putLongArray(getKey(prefix, field.getName()), (long[]) field.get(instance));
                    }else{
                        bundle.putLong(getKey(prefix, field.getName()), (Long) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == short.class || field.getGenericType() == Short.class || field.getType().getComponentType() == short.class || field.getType().getComponentType() == Short.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putShortArray(getKey(prefix, field.getName()), (short[]) field.get(instance));
                    }else{
                        bundle.putShort(getKey(prefix, field.getName()), (Short) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == char.class || field.getGenericType() == Character.class || field.getType().getComponentType() == char.class || field.getType().getComponentType() == Character.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putCharArray(getKey(prefix, field.getName()), (char[]) field.get(instance));
                    }else{
                        bundle.putChar(getKey(prefix, field.getName()), (Character) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == float.class || field.getGenericType() == Float.class|| field.getType().getComponentType() == char.class || field.getType().getComponentType() == Character.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putFloatArray(getKey(prefix, field.getName()), (float[]) field.get(instance));
                    }else{
                        bundle.putFloat(getKey(prefix, field.getName()), (Float) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == boolean.class || field.getGenericType() == Boolean.class || field.getType().getComponentType() == boolean.class || field.getType().getComponentType() == Boolean.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putBooleanArray(getKey(prefix, field.getName()), (boolean[]) field.get(instance));
                    }else{
                        bundle.putBoolean(getKey(prefix, field.getName()), (Boolean) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == String.class || field.getType().getComponentType() == String.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putStringArray(getKey(prefix, field.getName()), (String[]) field.get(instance));
                    }else{
                        bundle.putString(getKey(prefix, field.getName()), (String) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else{
                try {
                    boolean instantiated = field.get(instance) != null;
                    bundle.putBoolean(getKey(prefix, getKey(field.getName(), PARAM_INSTANTIATED)), instantiated);
                    if(instantiated){
                        if(field.getType().isArray()) {

                            int size = field.getType().isArray() ? Array.getLength(field.get(instance)) : 1;
                            bundle.putInt(getKey(prefix, getKey(field.getName(), PARAM_COUNTER)), size);

                            for (int counter = 0; counter < size; counter++) {
                                Object aux = Array.get(field.get(instance), counter);

                                if(aux != null){
                                    bundle.putBoolean(getKey(prefix, getKey(String.format("%s[%d]", field.getName(), counter), PARAM_INSTANTIATED)), true);
                                    toBundle(bundle, getKey(prefix, String.format("%s[%d]", field.getName(), counter)), aux);
                                }else{
                                    bundle.putBoolean(getKey(prefix, getKey(String.format("%s[%d]", field.getName(), counter), PARAM_INSTANTIATED)), false);
                                }

                            }
                        }else {
                            toBundle(bundle, getKey(prefix, field.getName()), field.get(instance));
                        }
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
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

            if (field.getGenericType() == int.class  || field.getGenericType() == Integer.class|| field.getType().getComponentType() == int.class || field.getType().getComponentType() == Integer.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getIntArray(getKey(prefix, field.getName())));
                    }else {
                        field.set(instance, bundle.getInt(getKey(prefix, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == double.class || field.getGenericType() == Double.class || field.getType().getComponentType() == double.class || field.getType().getComponentType() == Double.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getDoubleArray(getKey(prefix, field.getName())));
                    }else {
                        field.set(instance, bundle.getDouble(getKey(prefix, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == long.class || field.getGenericType() == Long.class || field.getType().getComponentType() == long.class || field.getType().getComponentType() == Long.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getLongArray(getKey(prefix, field.getName())));
                    }else {
                        field.set(instance, bundle.getLong(getKey(prefix, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == short.class || field.getGenericType() == Short.class || field.getType().getComponentType() == short.class || field.getType().getComponentType() == Short.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getShortArray(getKey(prefix, field.getName())));
                    }else {
                        field.set(instance, bundle.getShort(getKey(prefix, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == char.class || field.getGenericType() == Character.class || field.getType().getComponentType() == char.class || field.getType().getComponentType() == Character.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getCharArray(getKey(prefix, field.getName())));
                    }else {
                        field.set(instance, bundle.getChar(getKey(prefix, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == float.class || field.getGenericType() == Float.class|| field.getType().getComponentType() == char.class || field.getType().getComponentType() == Character.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getFloatArray(getKey(prefix, field.getName())));
                    }else {
                        field.set(instance, bundle.getFloat(getKey(prefix, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == boolean.class || field.getGenericType() == Boolean.class || field.getType().getComponentType() == boolean.class || field.getType().getComponentType() == Boolean.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getBooleanArray(getKey(prefix, field.getName())));
                    }else {
                        field.set(instance, bundle.getBoolean(getKey(prefix, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == String.class || field.getType().getComponentType() == String.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getStringArray(getKey(prefix, field.getName())));
                    }else {
                        field.set(instance, bundle.getString(getKey(prefix, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else{
                try {
                    if(bundle.getBoolean(getKey(prefix, getKey(field.getName(), PARAM_INSTANTIATED)), false)){

                        if(field.getType().isArray()){
                            int size = bundle.getInt(getKey(prefix, getKey(field.getName(), PARAM_COUNTER)));

                            Object aux = Array.newInstance(field.getType().getComponentType(), size);
                            field.set(instance, aux);
                            for(int counter = 0; counter < size; counter ++){
                                if(bundle.getBoolean(getKey(prefix, getKey(String.format("%s[%d]", field.getName(), counter), PARAM_INSTANTIATED)))){
                                    Array.set(aux, counter, fromBundle(bundle, getKey(prefix, String.format("%s[%d]", field.getName(), counter)), field.getType().getComponentType()));
                                }else{
                                    Array.set(aux, counter, null);
                                }

                            }
                        }else{
                            field.set(instance, fromBundle(bundle, getKey(prefix, field.getName()), field.getType()));
                        }
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }
        }

        return instance;
    }

    private static String getKey(String prefix, String fieldName){
        return String.format(PARAM_PREFIX, prefix, fieldName);
    }
}