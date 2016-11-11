package edu.byu.cs455.scene.object;

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

    public abstract Color getMaterialColor(Vector intersectionPoint);

    public abstract Vector getIntersectionVector(Ray ray);

    public abstract Vector getNormalAtIntersection(Vector intersection);
}
