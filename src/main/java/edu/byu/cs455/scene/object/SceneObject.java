package edu.byu.cs455.scene.object;

import edu.byu.cs455.scene.element.Light;
import edu.byu.cs455.scene.element.Ray;
import edu.byu.cs455.scene.element.Vector;
import edu.byu.cs455.scene.material.Material;

import java.awt.*;

/**
 * Created by cstaheli on 11/1/2016.
 */
public abstract class SceneObject
{
    private final Material material;

    public SceneObject(Material material)
    {
        this.material = material;
    }

    public Color getMaterialColor()
    {
        return material.getMaterialColor();
    }

    public Color getMaterialColor(Vector intersectionPoint, Light light)
    {
        Vector cl = getColorVector(light.getLightColor());
        Vector cr = getColorVector(getMaterialColor());
        Vector ca = getColorVector(light.getAmbientLightColor());
        //Vector cp = getColorVector(
        double lambertianComponent = getLambertianComponent(intersectionPoint, light);
        //c = cr * ca + cr * cl * max(0, n \dot l)) + cl * cp * max(0, e \dot r)^p
        Vector ambientTerm = cr.multiply(ca);
        Vector diffuseTerm = cr.multiply(cl).multiply(lambertianComponent);
        //Vector phongTerm =
        return getVectorColor(ambientTerm.add(diffuseTerm));
    }

    private double getLambertianComponent(Vector intersectionPoint, Light light)
    {
        Vector lightDirection = light.getDirectionToLight();
        Vector normal = getNormalAtIntersection(intersectionPoint);
        double angleBetweenLightAndNormal = lightDirection.dotProduct(normal);
        return Math.max(0, angleBetweenLightAndNormal);
    }

    private Vector getColorVector(Color color)
    {
        float[] rgb = new float[3];
        rgb = color.getRGBColorComponents(rgb);
        return getColorVector(rgb);
    }

    private Vector getColorVector(float[] rgb)
    {
        return new Vector(rgb[0], rgb[1], rgb[2]);
    }

    private Color getVectorColor(Vector color)
    {
        float r = clipToFloatColorSpace(color.x());
        float g = clipToFloatColorSpace(color.y());
        float b = clipToFloatColorSpace(color.z());
        return new Color(r, g, b);
    }

    private float clipToFloatColorSpace(double colorComponent)
    {
        if (colorComponent < 0.0f)
            return 0.0f;
        else if (colorComponent > 1.0f)
            return 1.0f;
        else
            return (float) colorComponent;
    }

    public abstract Vector getIntersectionVector(Ray ray);

    public abstract Vector getNormalAtIntersection(Vector intersection);
}
