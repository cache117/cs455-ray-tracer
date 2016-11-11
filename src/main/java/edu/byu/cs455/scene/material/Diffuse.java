package edu.byu.cs455.scene.material;

import edu.byu.cs455.scene.Scene;
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
    public Color calculateIlluminationModel(Vector normal, boolean isInShadow, Scene scene)
    {
        //c = cr * ca + cr * cl * max(0, n \dot l)) + cl * cp * max(0, e \dot r)^p
        Vector lightSourceColor = getColorVector(scene.getLight().getLightColor()); //cl
        Vector diffuseReflectanceColor = getColorVector(getMaterialColor()); //cr
        Vector ambientColor = getColorVector(scene.getLight().getAmbientLightColor()); //ca
        Vector specularHighlightColor = getColorVector(getSpecularHighlight()); //cp
        Vector directionToLight = scene.getLight().getDirectionToLight(); //l
        Vector reflectionVector = getReflectionVector(normal, directionToLight); //r

        double visibilityTerm = isInShadow ? 1 : 0;
        Vector ambientTerm = getAmbientTerm(diffuseReflectanceColor, ambientColor);
        Vector diffuseTerm = getDiffuseTerm(diffuseReflectanceColor, lightSourceColor, getLambertianComponent(normal, directionToLight)).multiply(visibilityTerm);
        Vector phongTerm = getPhongTerm(lightSourceColor, specularHighlightColor, getPhongComponent(scene.getCameraSettings().getLookFrom(), reflectionVector, getPhongConstant())).multiply(visibilityTerm);
        return addColorTermsTogether(ambientTerm, diffuseTerm, phongTerm);
    }

    private Color addColorTermsTogether(Vector ambientTerm, Vector diffuseTerm, Vector phongTerm)
    {
        return getVectorColor(ambientTerm.add(diffuseTerm).add(phongTerm));
    }

    private Vector getAmbientTerm(Vector diffuseReflectanceColor, Vector ambientColor)
    {
        return diffuseReflectanceColor.multiply(ambientColor);
    }

    private Vector getDiffuseTerm(Vector diffuseReflectanceColor, Vector lightColor, double lambertianComponent)
    {
        return multiplyColorsWithComponentTerm(diffuseReflectanceColor, lightColor, lambertianComponent);
    }

    private Vector getPhongTerm(Vector lightColor, Vector specularColor, double phongComponent)
    {
        return multiplyColorsWithComponentTerm(lightColor, specularColor, phongComponent);
    }

    private Vector multiplyColorsWithComponentTerm(Vector first, Vector second, double component)
    {
        return first.multiply(second).multiply(component);
    }

    private Vector getReflectionVector(Vector normal, Vector directionToLight)
    {
        return normal.multiply(2).multiply(normal.crossProduct(directionToLight)).subtract(directionToLight);
    }

    private double getPhongComponent(Vector eye, Vector reflection, double phongConstant)
    {
        return Math.pow(Math.max(0, eye.dotProduct(reflection)), phongConstant);
    }
}
