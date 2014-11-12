package com.massivedisaster.android.bundler;

import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Created by Tiago Ribeiro on 11/11/2014.
 */
public final class Bundler {

    private static final String PARAM_key = "%s.%s";
    private static final String PARAM_INSTANTIATED = "INSTANTIATED";
    private static final String PARAM_COUNTER = "COUNTER";
    private static final String DEFAULT_key = "key";

    /**
     * Creates a bundle and stores an object into it
     * @param instance Object to be saved
     * @return the bundle in which the object has been saved
     */
    public static Bundle toBundle(Object instance){
        return toBundle(DEFAULT_key, instance);
    }

    /**
     * Creates a bundle and stores an object into it identified with the given key
     * @param key String to identify the object in the given bundle
     * @param instance Object to be saved
     * @return The bundle in which the object has been saved
     */
    public static Bundle toBundle(String key, Object instance){
        return toBundle(null, key, instance);
    }

    /**
     * Save an object into a given bundle identified by the given key
     * @param bundle Bundle where the object will be saved, creates one if null is given
     * @param key String to identify the object in the given bundle
     * @param instance Object to be saved
     * @return The bundle in which the object has been saved
     */
    public static Bundle toBundle(Bundle bundle, String key, Object instance){

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
                        bundle.putIntArray(getKey(key, field.getName()), (int[]) field.get(instance));
                    }else{
                        bundle.putInt(getKey(key, field.getName()), (Integer) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == double.class || field.getGenericType() == Double.class || field.getType().getComponentType() == double.class || field.getType().getComponentType() == Double.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putDoubleArray(getKey(key, field.getName()), (double[]) field.get(instance));
                    }else{
                        bundle.putDouble(getKey(key, field.getName()), (Double) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == long.class || field.getGenericType() == Long.class || field.getType().getComponentType() == long.class || field.getType().getComponentType() == Long.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putLongArray(getKey(key, field.getName()), (long[]) field.get(instance));
                    }else{
                        bundle.putLong(getKey(key, field.getName()), (Long) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == short.class || field.getGenericType() == Short.class || field.getType().getComponentType() == short.class || field.getType().getComponentType() == Short.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putShortArray(getKey(key, field.getName()), (short[]) field.get(instance));
                    }else{
                        bundle.putShort(getKey(key, field.getName()), (Short) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == char.class || field.getGenericType() == Character.class || field.getType().getComponentType() == char.class || field.getType().getComponentType() == Character.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putCharArray(getKey(key, field.getName()), (char[]) field.get(instance));
                    }else{
                        bundle.putChar(getKey(key, field.getName()), (Character) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == float.class || field.getGenericType() == Float.class|| field.getType().getComponentType() == char.class || field.getType().getComponentType() == Character.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putFloatArray(getKey(key, field.getName()), (float[]) field.get(instance));
                    }else{
                        bundle.putFloat(getKey(key, field.getName()), (Float) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == boolean.class || field.getGenericType() == Boolean.class || field.getType().getComponentType() == boolean.class || field.getType().getComponentType() == Boolean.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putBooleanArray(getKey(key, field.getName()), (boolean[]) field.get(instance));
                    }else{
                        bundle.putBoolean(getKey(key, field.getName()), (Boolean) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == String.class || field.getType().getComponentType() == String.class) {
                try {
                    if(field.getType().isArray()){
                        bundle.putStringArray(getKey(key, field.getName()), (String[]) field.get(instance));
                    }else{
                        bundle.putString(getKey(key, field.getName()), (String) field.get(instance));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else{
                try {
                    boolean instantiated = field.get(instance) != null;
                    bundle.putBoolean(getKey(key, getKey(field.getName(), PARAM_INSTANTIATED)), instantiated);
                    if(instantiated){
                        if(field.getType().isArray()) {

                            int size = field.getType().isArray() ? Array.getLength(field.get(instance)) : 1;
                            bundle.putInt(getKey(key, getKey(field.getName(), PARAM_COUNTER)), size);

                            for (int counter = 0; counter < size; counter++) {
                                Object aux = Array.get(field.get(instance), counter);

                                if(aux != null){
                                    bundle.putBoolean(getKey(key, getKey(String.format("%s[%d]", field.getName(), counter), PARAM_INSTANTIATED)), true);
                                    toBundle(bundle, getKey(key, String.format("%s[%d]", field.getName(), counter)), aux);
                                }else{
                                    bundle.putBoolean(getKey(key, getKey(String.format("%s[%d]", field.getName(), counter), PARAM_INSTANTIATED)), false);
                                }

                            }
                        }else {
                            toBundle(bundle, getKey(key, field.getName()), field.get(instance));
                        }
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not retrieve value from %s.%s", instance.getClass().getName(), field.getName()));
                }
            }
        }

        return bundle;
    }

    /**
     * Loads an object from a given bundle identified by the given key
     * @param bundle Bundle from where the object will be loaded
     * @param key String that identifies the object to be fetched
     * @param classType Type of the object to be loaded
     * @param <T> =classType
     * @return Object of type 'classType' loaded from the given bundle.
     */
    public static <T> T fromBundle(Bundle bundle, String key, Class<T> classType){

        if(bundle == null)
            return null;

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
                        field.set(instance, bundle.getIntArray(getKey(key, field.getName())));
                    }else {
                        field.set(instance, bundle.getInt(getKey(key, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == double.class || field.getGenericType() == Double.class || field.getType().getComponentType() == double.class || field.getType().getComponentType() == Double.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getDoubleArray(getKey(key, field.getName())));
                    }else {
                        field.set(instance, bundle.getDouble(getKey(key, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == long.class || field.getGenericType() == Long.class || field.getType().getComponentType() == long.class || field.getType().getComponentType() == Long.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getLongArray(getKey(key, field.getName())));
                    }else {
                        field.set(instance, bundle.getLong(getKey(key, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == short.class || field.getGenericType() == Short.class || field.getType().getComponentType() == short.class || field.getType().getComponentType() == Short.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getShortArray(getKey(key, field.getName())));
                    }else {
                        field.set(instance, bundle.getShort(getKey(key, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == char.class || field.getGenericType() == Character.class || field.getType().getComponentType() == char.class || field.getType().getComponentType() == Character.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getCharArray(getKey(key, field.getName())));
                    }else {
                        field.set(instance, bundle.getChar(getKey(key, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == float.class || field.getGenericType() == Float.class|| field.getType().getComponentType() == char.class || field.getType().getComponentType() == Character.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getFloatArray(getKey(key, field.getName())));
                    }else {
                        field.set(instance, bundle.getFloat(getKey(key, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == boolean.class || field.getGenericType() == Boolean.class || field.getType().getComponentType() == boolean.class || field.getType().getComponentType() == Boolean.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getBooleanArray(getKey(key, field.getName())));
                    }else {
                        field.set(instance, bundle.getBoolean(getKey(key, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else if (field.getGenericType() == String.class || field.getType().getComponentType() == String.class) {
                try {
                    if(field.getType().isArray()){
                        field.set(instance, bundle.getStringArray(getKey(key, field.getName())));
                    }else {
                        field.set(instance, bundle.getString(getKey(key, field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }else{
                try {
                    if(bundle.getBoolean(getKey(key, getKey(field.getName(), PARAM_INSTANTIATED)), false)){

                        if(field.getType().isArray()){
                            int size = bundle.getInt(getKey(key, getKey(field.getName(), PARAM_COUNTER)));

                            Object aux = Array.newInstance(field.getType().getComponentType(), size);
                            field.set(instance, aux);
                            for(int counter = 0; counter < size; counter ++){
                                if(bundle.getBoolean(getKey(key, getKey(String.format("%s[%d]", field.getName(), counter), PARAM_INSTANTIATED)))){
                                    Array.set(aux, counter, fromBundle(bundle, getKey(key, String.format("%s[%d]", field.getName(), counter)), field.getType().getComponentType()));
                                }else{
                                    Array.set(aux, counter, null);
                                }

                            }
                        }else{
                            field.set(instance, fromBundle(bundle, getKey(key, field.getName()), field.getType()));
                        }
                    }
                } catch (IllegalAccessException e) {
                    Log.e(Bundler.class.getName(), String.format("Could not put value into %s.%s", instance.getClass().getName(), field.getName()));
                }
            }
        }

        return instance;
    }

    private static String getKey(String key, String fieldName){
        return String.format(PARAM_key, key, fieldName);
    }
}