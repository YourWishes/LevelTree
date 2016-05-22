package com.domsplace.LevelTree.Bases;

import java.util.ArrayList;
import java.util.List;

public class LevelTreeObjectBase extends LevelTreeBase {
    public static List<LevelTreeObjectBase> objects = new ArrayList<LevelTreeObjectBase>();
    
    public static int pointsCurve = 2000;
    
    public LevelTreeObjectBase() {
        objects.add(this);
    }
}
