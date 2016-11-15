package edu.byu.cs455.scene.object;

import edu.byu.cs455.scene.element.Ray;
import edu.byu.cs455.scene.element.Vector;
import edu.byu.cs455.scene.material.Material;

/**
 * Created by cstaheli on 11/1/2016.
 * See <a href ="http://geomalgorithms.com/a06-_intersect-2.html#intersect3D_RayTriangle()">GeoMal Algorithms</a>
 * for information about triangle-ray intersection algorithm.
 */
public class Triangle extends SceneObject
{
    private final Vector a, b, c;

    public Triangle(Vector a, Vector b, Vector c, Material material)
    {
        super(material);
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

    public Vector getU()
    {
        return getA().subtract(getB());
    }

    public Vector getV()
    {
        return getC().subtract(getB());
    }

    @Override
    public Vector getIntersectionVector(Ray ray)
    {
        Vector planeIntersectionVector = getPlaneIntersectionVector(ray);
        if (planeIntersectionVector != null && isIntersectionVectorInsideTriangle(planeIntersectionVector))
        {
            return planeIntersectionVector;
        }
        else
        {
            return null;
        }
    }

    private Vector getPlaneIntersectionVector(Ray ray)
    {
        double epsilon = 0.00000001;
        Vector w0 = ray.getOrigin().subtract(getB());
        double numerator = -(getPlaneNormal().dotProduct(w0));
        double denominator = getPlaneNormal().dotProduct(ray.getDirection());
        //ray is parallel to triangle plane
        if (Math.abs(denominator) < epsilon)
        {
            //ray lies in triangle plane
            if (numerator == 0)
            {
                return null;
            }
            //ray is disjoint from plane
            else
            {
                return null;
            }
        }
        double intersectionDistance = numerator / denominator;

        //intersectionDistance < 0 means the "intersection" is behind the ray (pointing away from plane), so not a real intersection
        return (intersectionDistance >= 0) ? ray.getLocationWithMagnitude(intersectionDistance) : null;
    }

    private boolean isIntersectionVectorInsideTriangle(Vector planeIntersectionVector)
    {
        //Get edges of triangle
        Vector u = getU();
        Vector v = getV();

        //Pre-compute unique five dot-products
        double uu = u.dotProduct(u);
        double uv = u.dotProduct(v);
        double vv = v.dotProduct(v);
        Vector w = planeIntersectionVector.subtract(getB());
        double wu = w.dotProduct(u);
        double wv = w.dotProduct(v);
        double denominator = (uv * uv) - (uu * vv);

        //get and test parametric coordinates
        double s = ((uv * wv) - (vv * wu)) / denominator;
        if (s < 0 || s > 1)
        {
            return false;
        }
        double t = ((uv * wu) - (uu * wv)) / denominator;
        if (t < 0 || (s + t) > 1)
        {
            return false;
        }

        return true;
    }

    @Override
    public Vector getNormalAtIntersection(Vector intersection)
    {
        return getPlaneNormal();
    }

    private Vector getPlaneNormal()
    {
        Vector v1 = getU();
        Vector v2 = getV();
        return v1.crossProduct(v2).normalize();
    }

    @Override
    public String toString()
    {
        return "Triangle{" +
                "a=" + getA() +
                ", b=" + getB() +
                ", c=" + getC() +
                '}';
    }
}
