package edu.byu.cs455.scene.material;

import edu.byu.cs455.scene.Scene;
import edu.byu.cs455.scene.element.Ray;
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
    public Color calculateIlluminationModel(Vector normal, boolean isInShadow, Scene scene, Ray ray, Vector intersectionPoint)
    {
        //c = cr * ca + cr * cl * max(0, n \dot l)) + cl * cp * max(0, e \dot r)^p
        Vector lightSourceColor = getColorVector(scene.getLightColor()); //cl
        Vector diffuseReflectanceColor = getColorVector(getMaterialColor()); //cr
        Vector ambientColor = getColorVector(scene.getAmbientLightColor()); //ca
        Vector specularHighlightColor = getColorVector(getSpecularHighlight()); //cp
        Vector directionToLight = scene.getDirectionToLight()
                .normalize(); //l
        Vector reflectionVector = getReflectionVector(normal, directionToLight)
                .normalize(); //r

        //double visibilityTerm = isInShadow ? 1 : 0;
        double visibilityTerm = 1;
        Vector ambientTerm = getAmbientTerm(diffuseReflectanceColor,
                ambientColor);
        Vector diffuseTerm = getDiffuseTerm(diffuseReflectanceColor,
                lightSourceColor,
                getLambertianComponent(normal,
                        directionToLight
                )
        )
                .multiply(visibilityTerm);
        Vector phongTerm = getPhongTerm(lightSourceColor,
                specularHighlightColor,
                getPhongComponent(scene.getLookFrom(),
                        reflectionVector,
                        getPhongConstant()
                )
        )
                .multiply(visibilityTerm);
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
        return normal.multiply(2).multiply(normal.dotProduct(directionToLight)).subtract(directionToLight);
    }

    private double getPhongComponent(Vector eye, Vector reflection, double phongConstant)
    {
        double angleBetweenEyeAndReflection = eye.dotProduct(reflection);
        angleBetweenEyeAndReflection = Math.max(0, angleBetweenEyeAndReflection);
        return Math.pow(angleBetweenEyeAndReflection, phongConstant);
    }

    private double getLambertianComponent(Vector normal, Vector lightDirection)
    {
        double angleBetweenLightAndNormal = lightDirection.dotProduct(normal);
        return Math.max(0, angleBetweenLightAndNormal);
    }
}
