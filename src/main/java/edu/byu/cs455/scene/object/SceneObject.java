package edu.byu.cs455.scene.object;

import edu.byu.cs455.scene.Ray;
import edu.byu.cs455.scene.Vector;

import java.awt.*;

/**
 * Created by cstaheli on 11/1/2016.
 */
public abstract class SceneObject
{
    private final Material material;
    private final Color materialColor;
    private Color specularHighlightColor;
    private int phongConstant;

    public SceneObject(Material material, Color materialColor, Color specularHighlightColor, int phongConstant)
    {
        this.material = material;
        this.specularHighlightColor = specularHighlightColor;
        this.materialColor = materialColor;
        this.phongConstant = phongConstant;
    }

    public SceneObject(Material material, Color materialColor)
    {
        this(material, materialColor, null, -1);
    }

    public Material getMaterial()
    {
        return material;
    }

    public Color getMaterialColor()
    {
        return materialColor;
    }

    public Color getSpecularHighlightColor()
    {
        return specularHighlightColor;
    }

    public int getPhongConstant()
    {
        return phongConstant;
    }

    public abstract Vector getIntersectionVector(Ray ray);
}
