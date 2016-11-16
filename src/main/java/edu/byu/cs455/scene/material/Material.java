package edu.byu.cs455.scene.material;

import edu.byu.cs455.scene.Scene;
import edu.byu.cs455.scene.element.Ray;
import edu.byu.cs455.scene.element.Vector;

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

    public abstract Color calculateIlluminationModel(Vector normal, boolean isInShadow, Scene scene, Ray ray, Vector intersectionPoint);

    protected Vector getColorVector(Color color)
    {
        float[] rgb = new float[3];
        rgb = color.getRGBColorComponents(rgb);
        return getColorVector(rgb);
    }

    private Vector getColorVector(float[] rgb)
    {
        return new Vector(rgb[0], rgb[1], rgb[2]);
    }

    protected Color getVectorColor(Vector color)
    {
        float r = clipToFloatColorSpace(color.x());
        float g = clipToFloatColorSpace(color.y());
        float b = clipToFloatColorSpace(color.z());
        return new Color(r, g, b);
    }

    private float clipToFloatColorSpace(double colorComponent)
    {
        if (colorComponent < 0.0f)
        {
            return 0.0f;
        }
        else if (colorComponent > 1.0f)
        {
            return 1.0f;
        }
        else
        {
            return (float) colorComponent;
        }
    }

    protected Color getColorInShadow(Scene scene)
    {
        Vector diffuseReflectanceColor = getColorVector(getMaterialColor());
        Vector ambientColor = getColorVector(scene.getAmbientLightColor());
        Vector ambientTerm = diffuseReflectanceColor.multiply(ambientColor);
        return getVectorColor(ambientTerm);
    }

    protected Vector getLightReflectionVector(Vector normal, Vector directionToLight)
    {
        return normal
                .multiply(2)
                .multiply(getAngleBetween(normal, directionToLight))
                .subtract(directionToLight)
                .normalize();
    }

    protected double getAngleBetween(Vector first, Vector second)
    {
        return Math.abs(first.dotProduct(second));
    }
}
