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

        Vector directionToLight = scene.getDirectionToLight().normalize(); //l
        Vector reflectionVector = getLightReflectionVector(normal, directionToLight); //r

        double visibilityTerm = isInShadow ? 0 : 1;
        Vector ambientTerm = diffuseReflectanceColor
                .multiply(ambientColor);

        Vector diffuseTerm = diffuseReflectanceColor
                .multiply(lightSourceColor)
                .multiply(getAngleBetween(normal,
                        directionToLight)
                )
                .multiply(visibilityTerm);

        double phongComponent = Math.pow(
                getAngleBetween(
                        scene.getLookFrom(),
                        reflectionVector
                ),
                getPhongConstant());
        Vector phongTerm = lightSourceColor
                .multiply(specularHighlightColor)
                .multiply(phongComponent)
                .multiply(visibilityTerm);

        return getVectorColor(ambientTerm
                .add(diffuseTerm)
                .add(phongTerm));
    }

}
