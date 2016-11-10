package edu.byu.cs455.scene.element;

import java.awt.*;

/**
 * Created by cstaheli on 11/1/2016.
 */
public class Light
{
    private final Vector directionToLight;
    private final Color lightColor, ambientLightColor, backgroundColor;

    public Light(Vector directionToLight, Color lightColor, Color ambientLightColor, Color backgroundColor)
    {
        this.directionToLight = directionToLight;
        this.lightColor = lightColor;
        this.ambientLightColor = ambientLightColor;
        this.backgroundColor = backgroundColor;
    }

    public Vector getDirectionToLight()
    {
        return directionToLight;
    }

    public Color getLightColor()
    {
        return lightColor;
    }

    public Color getAmbientLightColor()
    {
        return ambientLightColor;
    }

    public Color getBackgroundColor()
    {
        return backgroundColor;
    }
}
