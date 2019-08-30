package com.project.restaurantapp.Adapters;

public class DistanceCalculator {

    /**
     * lat1- latitude 1
     * lat2 — latitude 2
     * lon3 — longitude 1
     * lon4 — longitude 2
     */

    public static Double  getDistance(Double lat1,Double lon1,Double lat2,Double lon2) {

        final int R = 6371; // Radious of the earth
//        Double lat1 = Double.parseDouble(args[0]);
//        Double lon1 = Double.parseDouble(args[1]);
//        Double lat2 = Double.parseDouble(args[2]);
//        Double lon2 = Double.parseDouble(args[3]);
        Double latDistance = toRad(lat2-lat1);
        Double lonDistance = toRad(lon2-lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double distance = R * c;
        return distance;

    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
}
