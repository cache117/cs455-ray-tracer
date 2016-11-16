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
        Vector reflectionVector = getReflectionVector(normal, ray.getDirection());
        Ray reflectionRay = Ray.translateRayByEpsilon(new Ray(intersectionPoint, reflectionVector));
        Vector ambientColor = getColorVector(getColorInShadow(scene));

        Vector rayColor = getColorVector(scene.getRayColor(reflectionRay));
        return getVectorColor(rayColor.add(ambientColor));
    }

    private Vector getReflectionVector(Vector normal, Vector originalDirection)
    {
        return originalDirection
                .subtract(normal
                        .multiply(2)
                        .multiply(originalDirection.dotProduct(normal)
                        )
                )
                .normalize();

    }

}
