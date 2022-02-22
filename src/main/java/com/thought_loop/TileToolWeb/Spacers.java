package com.thought_loop.TileToolWeb;

import java.util.HashMap;
import java.util.Map;

public class Spacers {


    //returns String common fraction in inches.
    //returns string version if fraction conversion is not standard.
   public static String stdInchSpacers(Double inchDecimal){
       Map<Double,String> spacersInchFraction = new HashMap<>();
       spacersInchFraction.put(0.0625,"1/16in.");
       spacersInchFraction.put(0.125,"1/8in.");
       spacersInchFraction.put(0.1875,"3/16in.");
       spacersInchFraction.put(0.25,"1/4in.");
       spacersInchFraction.put(0.375, "3/8in.");
       spacersInchFraction.put(0.5,"1/2in.");
       spacersInchFraction.put(0.625,"5/8in.");
       spacersInchFraction.put(0.75, "3/4in.");
       spacersInchFraction.put(0.875, "7/8in.");

       if(spacersInchFraction.containsKey(inchDecimal)){
           return spacersInchFraction.get(inchDecimal);
       }
       return inchDecimal + "in.";
    }

}
