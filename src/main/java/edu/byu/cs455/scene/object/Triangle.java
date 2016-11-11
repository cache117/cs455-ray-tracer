package edu.byu.cs455.scene.object;

import edu.byu.cs455.scene.element.PlanePoint;
import edu.byu.cs455.scene.element.Ray;
import edu.byu.cs455.scene.element.Vector;
import edu.byu.cs455.scene.material.Material;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cstaheli on 11/1/2016.
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

    @Override
    public Color getMaterialColor(Vector intersectionPoint)
    {
        return null;
    }

    @Override
    public Vector getIntersectionVector(Ray ray)
    {
        Vector planeIntersectionVector = getPlaneIntersectionVector(ray, getPlaneNormal());
        if (planeIntersectionVector != null)
        {
            if (isIntersectionVectorInsideTriangle(planeIntersectionVector))
            {
                return planeIntersectionVector;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    private boolean isIntersectionVectorInsideTriangle(Vector planeIntersectionVector)
    {
        List<PlanePoint> vertices = getPlanarVertices(planeIntersectionVector);
        int numCrossings = getNumCrossings(vertices);
        //odd. Means intersection is inside triangle/polygon
        return (numCrossings % 2 != 0);
    }

    private List<PlanePoint> getPlanarVertices(Vector planeIntersectionVector)
    {
        Coordinate largestCoordinate = getLargestMagnitude(getPlaneNormal());
        PlanePoint intersectionPoint = projectOnto2DPlane(largestCoordinate, planeIntersectionVector);
        List<Vector> vectors = new ArrayList<>();
        vectors.add(a);
        vectors.add(b);
        vectors.add(c);
        List<PlanePoint> vertices = new ArrayList<>();
        for (Vector vector : vectors)
        {
            vertices.add(projectOnto2DPlane(largestCoordinate, vector).subtract(intersectionPoint));
        }
        return vertices;
    }

    private int getNumCrossings(List<PlanePoint> vertices)
    {
        int numCrossings = 0;
        int signHolder = determineSignHolder(vertices.get(0));
        int nextSignHolder;
        for (int i = 0; i < vertices.size(); ++i)
        {
            PlanePoint vertex = vertices.get(i);
            PlanePoint nextVertex = vertices.get((i + 1) % 2);
            nextSignHolder = determineSignHolder(vertex);
            if (signHolder != nextSignHolder)
            {
                if (vertex.getU() > 0 && nextVertex.getU() > 0)
                {
                    ++numCrossings;
                }
                else if (vertex.getU() > 0 || nextVertex.getU() > 0)
                {
                    double uCross = vertex.getU() - (vertex.getV() * (nextVertex.getU() - vertex.getU()) / (nextVertex.getV() - vertex.getV()));
                    if (uCross > 0)
                    {
                        ++numCrossings;
                    }
                }
            }
            signHolder = nextSignHolder;
        }
        return numCrossings;
    }

    private Vector getPlaneIntersectionVector(Ray ray, Vector planeNormal)
    {
        //t = -(p_n \dot r_o + d)/(p_n \dot r_d)
        double denominator = ray.getDirection().dotProduct(planeNormal);
        //(p_n \dot r_d) == 0, parallel. (p_n \dot r_d) > 0 Plane normal pointing away from ray.
        if (denominator >= 0)
        {
            return null;
        }
        double distance = planeNormal.distance(0d, 0d, 0d);
        double intersectionDistance = -(planeNormal.dotProduct(ray.getOrigin()) + distance) / denominator;
        //intersectionDistance <= 0 means the "intersection" is behind the ray, so not a real intersection

        return (intersectionDistance <= 0) ? null : ray.getLocation(intersectionDistance);
    }

    private PlanePoint projectOnto2DPlane(Coordinate strippedCoordinate, Vector vector)
    {
        switch (strippedCoordinate)
        {
            case X:
                return new PlanePoint(vector.y(), vector.z());
            case Y:
                return new PlanePoint(vector.x(), vector.z());
            case Z:
                return new PlanePoint(vector.x(), vector.y());
            default:
                throw new RuntimeException("This is not possible unless additional coordinates have been added");
        }
    }

    private Vector getPlaneNormal()
    {
        Vector v1 = a.subtract(b);
        Vector v2 = c.subtract(b);
        return v1.crossProduct(v2);
    }

    private int determineSignHolder(PlanePoint planePoint)
    {
        if (planePoint.getV() < 0)
            return -1;
        else
            return 1;
    }

    private enum Coordinate
    {
        X, Y, Z
    }

    private Coordinate getLargestMagnitude(Vector vector)
    {
        double largestMagnitude = Math.abs(vector.x());
        Coordinate coordinate = Coordinate.X;
        if (Math.abs(vector.y()) > largestMagnitude)
        {
            largestMagnitude = Math.abs(vector.y());
            coordinate = Coordinate.Y;
        }
        if (Math.abs(vector.z()) > largestMagnitude)
        {
            largestMagnitude = Math.abs(vector.z());
            coordinate = Coordinate.Z;
        }
        return coordinate;
    }

    @Override
    public String toString()
    {
        return "Triangle{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}
