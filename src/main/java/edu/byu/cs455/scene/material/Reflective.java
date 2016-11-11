package edu.byu.cs455.scene.material;

import edu.byu.cs455.scene.element.Light;
import edu.byu.cs455.scene.element.Vector;

import java.awt.*;

/**
 * Created by cstaheli on 11/9/2016.
 */
public class Reflective extends Material
{
    public Reflective(Color materialColor)
    {
        super(materialColor);
    }

    @Override
    public Color calculateIlluminationModel(Vector normal, Light light, Vector eye)
    {
        return getMaterialColor();
    }
}
