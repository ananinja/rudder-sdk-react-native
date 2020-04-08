package com.reactlibrary;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Utility {
    static Map<String, Object> convertReadableMapToMap(ReadableMap readableMap) {
        Map<String, Object> object = new HashMap<>();
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            switch (readableMap.getType(key)) {
                case Null:
                    object.put(key, JSONObject.NULL);
                    break;
                case Boolean:
                    object.put(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    object.put(key, readableMap.getDouble(key));
                    break;
                case String:
                    object.put(key, readableMap.getString(key));
                    break;
                case Map:
                    object.put(key, convertReadableMapToMap(readableMap.getMap(key)));
                    break;
                case Array:
                    object.put(key, convertReadableArrayToList(readableMap.getArray(key)));
                    break;
            }
        }
        return object;
    }

    private static List<Object> convertReadableArrayToList(ReadableArray readableArray) {
        ArrayList<Object> array = new ArrayList<>();
        for (int i = 0; i < readableArray.size(); i++) {
            switch (readableArray.getType(i)) {
                case Null:
                    break;
                case Boolean:
                    array.add(readableArray.getBoolean(i));
                    break;
                case Number:
                    array.add(readableArray.getDouble(i));
                    break;
                case String:
                    array.add(readableArray.getString(i));
                    break;
                case Map:
                    array.add(convertReadableMapToMap(readableArray.getMap(i)));
                    break;
                case Array:
                    array.add(convertReadableArrayToList(readableArray.getArray(i)));
                    break;
            }
        }
        return array;
    }
}
