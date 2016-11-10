package edu.byu.cs455.scene.material;

import java.awt.*;

/**
 * Created by cstaheli on 11/9/2016.
 */
public abstract class Material
{
    private final Color materialColor;

    public Material(Color materialColor)
    {
        this.materialColor = materialColor;
    }

    public Color getMaterialColor()
    {
        return materialColor;
    }
}
