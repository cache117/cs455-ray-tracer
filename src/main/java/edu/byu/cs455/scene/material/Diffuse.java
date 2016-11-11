package edu.byu.cs455.scene.material;

import edu.byu.cs455.scene.element.Light;
import edu.byu.cs455.scene.element.Vector;

import java.awt.*;

/**
 * Created by cstaheli on 11/9/2016.
 */
public class Diffuse extends Material
{
    private final Color specularHighlight;
    private final int phongConstant;

    public Diffuse(Color materialColor, Color specularHighlight, int phongConstant)
    {
        super(materialColor);
        this.specularHighlight = specularHighlight;
        this.phongConstant = phongConstant;
    }

    public Color getSpecularHighlight()
    {
        return specularHighlight;
    }

    public int getPhongConstant()
    {
        return phongConstant;
    }

    @Override
    public Color calculateIlluminationModel(Vector normal, Light light, Vector eye)
    {
        Vector cl = getColorVector(light.getLightColor());
        Vector cr = getColorVector(getMaterialColor());
        Vector ca = getColorVector(light.getAmbientLightColor());
        Vector cp = getColorVector(getSpecularHighlight());
        Vector l = light.getDirectionToLight();
        Vector r = normal.multiply(2).multiply(normal.crossProduct(l)).subtract(l);
        //c = cr * ca + cr * cl * max(0, n \dot l)) + cl * cp * max(0, e \dot r)^p
        Vector ambientTerm = cr.multiply(ca);
        Vector diffuseTerm = cr.multiply(cl).multiply(getLambertianComponent(normal, l));
        Vector phongTerm = cl.multiply(cp).multiply(getPhongComponent(eye, r, getPhongConstant()));
        return getVectorColor(ambientTerm.add(diffuseTerm).add(phongTerm));
    }

    private double getPhongComponent(Vector eye, Vector reflection, double phongConstant)
    {
        return Math.pow(Math.max(0, eye.dotProduct(reflection)), phongConstant);
    }
}
