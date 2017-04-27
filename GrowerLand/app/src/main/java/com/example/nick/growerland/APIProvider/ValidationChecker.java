package com.example.nick.growerland.APIProvider;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

class ValidationChecker {
    //Classed used to check validation of json elements
    JsonElement checkNumValidation(JsonObject obj, String memberName) {
        JsonElement jsonElement;
        try {
            jsonElement = obj.get(memberName);
        } catch (NullPointerException exc) {
            obj = new JsonObject();
            jsonElement = obj.get(memberName);
        }
        if (jsonElement == null) {
            obj.addProperty(memberName, 0);
            jsonElement = obj.get(memberName);
        }

        return jsonElement;
    }

    JsonElement checkStrValidation(JsonObject obj, String memberName) {
        JsonElement jsonElement;
        try {
            jsonElement = obj.get(memberName);
        } catch (NullPointerException exc) {
            obj = new JsonObject();
            jsonElement = obj.get(memberName);
        }

        if (jsonElement == null) {
            obj.addProperty(memberName, "");
            jsonElement = obj.get(memberName);
        }

        return jsonElement;
    }
}
