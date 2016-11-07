package edu.byu.cs455.scene.object;

import edu.byu.cs455.scene.Ray;
import edu.byu.cs455.scene.Vector;

import java.awt.*;

/**
 * Created by cstaheli on 11/1/2016.
 */
public class Triangle extends SceneObject
{
    private final Vector a, b, c;

    public Triangle(Vector a, Vector b, Vector c, Material material, Color materialColor, Color specularHighlightColor, int phongConstant)
    {
        super(material, materialColor, specularHighlightColor, phongConstant);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Triangle(Vector a, Vector b, Vector c, Material material, Color materialColor)
    {
        super(material, materialColor);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Vector getA()
    {
        return a;
    }

    public Vector getB()
    {
        return b;
    }

    public Vector getC()
    {
        return c;
    }

    @Override
    public Vector getIntersectionVector(Ray ray)
    {
        return null;
    }
}
