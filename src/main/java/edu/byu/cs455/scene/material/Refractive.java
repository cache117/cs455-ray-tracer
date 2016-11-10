package edu.byu.cs455.scene.material;

import java.awt.*;

/**
 * Created by cstaheli on 11/9/2016.
 */
public class Refractive extends Material
{
    private final double indexOfRefraction;

    public Refractive(Color materialColor, double indexOfRefraction)
    {
        super(materialColor);
        this.indexOfRefraction = indexOfRefraction;
    }

    public double getIndexOfRefraction()
    {
        return indexOfRefraction;
    }
}
