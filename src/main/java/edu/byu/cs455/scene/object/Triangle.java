package edu.byu.cs455.scene.object;

import edu.byu.cs455.scene.element.PlanePoint;
import edu.byu.cs455.scene.element.Ray;
import edu.byu.cs455.scene.element.Vector;
import edu.byu.cs455.scene.material.Material;

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

    public Vector getEdge1()
    {
        return getA().subtract(getB());
    }

    public Vector getEdge2()
    {
        return getC().subtract(getB());
    }

    @Override
    public Vector getIntersectionVector(Ray ray)
    {
        Vector planeIntersectionVector = getPlaneIntersectionVector(ray);
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

    private double getPlaneDistance()
    {
        return getB().dotProduct(getPlaneNormal());
        //return d;
    }

    @Override
    public Vector getNormalAtIntersection(Vector intersection)
    {
        return getPlaneNormal();
    }

    private boolean isIntersectionVectorInsideTriangle(Vector planeIntersectionVector)
    {
        Vector u = getEdge1();
        Vector v = getEdge2();
        double uu = u.dotProduct(u);
        double uv = u.dotProduct(v);
        double vv = v.dotProduct(v);
        Vector w = planeIntersectionVector.subtract(getB());
        double wu = w.dotProduct(u);
        double wv = w.dotProduct(v);
        double denominator = (uv * uv) - (uu * vv);

        //parametric coordinates
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

        /*List<PlanePoint> vertices = getPlanarVertices(planeIntersectionVector);
        int numCrossings = getNumCrossings(vertices);
        //odd. Means intersection is inside triangle/polygon
        return (numCrossings % 2 != 0);*/
    }

    private List<PlanePoint> getPlanarVertices(Vector planeIntersectionVector)
    {
        Coordinate largestCoordinate = getLargestMagnitude(getPlaneNormal());
        PlanePoint intersectionPoint = projectOnto2DPlane(largestCoordinate, planeIntersectionVector);
        List<Vector> vectors = getTriangleVertices();
        List<PlanePoint> vertices = new ArrayList<>();
        for (Vector vector : vectors)
        {
            vertices.add(projectOnto2DPlane(largestCoordinate, vector).subtract(intersectionPoint));
        }
        return vertices;
    }

    private List<Vector> getTriangleVertices()
    {
        List<Vector> vectors = new ArrayList<>();
        vectors.add(getA());
        vectors.add(getB());
        vectors.add(getC());
        return vectors;
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

    private Vector getPlaneIntersectionVector(Ray ray)
    {
        //t = -(p_n \dot r_o + d)/(p_n \dot r_d)
        double distanceDenominator = getPlaneNormal().dotProduct(ray.getDirection());
        //(p_n \dot r_d) == 0, parallel. (p_n \dot r_d) > 0 Plane normal pointing away from ray.
        if (distanceDenominator <= 0)
        {
            return null;
        }
        //double vo = -(getPlaneNormal().dotProduct(ray.getOrigin()) + getPlaneDistance());
        double distanceNumerator = -(getPlaneNormal().dotProduct(ray.getOrigin().subtract(getB())));
        if (distanceNumerator == 0)
        {
            return null;
        }
        double intersectionDistance = distanceNumerator / distanceDenominator;

        //intersectionDistance < 0 means the "intersection" is behind the ray, so not a real intersection
        return intersectionDistance >= 0 ? ray.getLocation(intersectionDistance) : null;
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
        Vector v1 = getEdge1();
        Vector v2 = getEdge2();
        return v1.crossProduct(v2).normalize();
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
                "a=" + getA() +
                ", b=" + getB() +
                ", c=" + getC() +
                '}';
    }
}
