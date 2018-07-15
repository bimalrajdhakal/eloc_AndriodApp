package com.nexogen.routefinder.intefaces;

import com.nexogen.routefinder.model.Route;

import java.util.List;


public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
