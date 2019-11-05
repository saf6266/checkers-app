package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class PostResignGameRoute implements Route {

    @Override
    public Object handle(Request request, Response response){
        final Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put("isGameOver", true);
        modeOptions.put("gameOverMessage", "The game has been resigned.");
       // m.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        return null;
    }
}
