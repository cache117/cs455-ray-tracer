package edu.byu.cs455.scene.material;

import edu.byu.cs455.scene.Scene;
import edu.byu.cs455.scene.element.Ray;
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
    public Color calculateIlluminationModel(Vector normal, boolean isInShadow, Scene scene, Ray ray, Vector intersectionPoint)
    {
        if (isInShadow)
        {
            return getColorInShadow(scene);
        }
        else
        {
            Vector directionToLight = scene.getDirectionToLight().normalize();
            Vector reflectionVector = normal.multiply(2).multiply(directionToLight.dotProduct(normal)).subtract(directionToLight)
                    .normalize();
            Ray reflectionRay = Ray.translateRayByEpsilon(new Ray(intersectionPoint, reflectionVector));
            return scene.getRayColor(reflectionRay);
        }
    }

}
