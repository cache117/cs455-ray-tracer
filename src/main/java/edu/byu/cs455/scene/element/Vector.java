package edu.byu.cs455.scene.element;

import javafx.geometry.Point3D;

/**
 * Created by cstaheli on 11/6/2016.
 */
public class Vector
{
    private Point3D getVector()
    {
        return vector;
    }

    public double x()
    {
        return vector.getX();
    }

    public double y()
    {
        return vector.getY();
    }

    public double z()
    {
        return vector.getZ();
    }

    private Point3D vector;

    /**
     * Creates a new instance of {@code Vector}.
     *
     * @param x The X component of the {@code Vector}
     * @param y The Y component of the {@code Vector}
     * @param z The Z component of the {@code Vector}
     */
    public Vector(double x, double y, double z)
    {
        vector = new Point3D(x, y, z);
    }

    public Vector(Point3D point3D)
    {
        this.vector = point3D;
    }

    public double distance(double x1, double y1, double z1)
    {
        return vector.distance(x1, y1, z1);
    }

    public Vector add(double x, double y, double z)
    {
        return new Vector(vector.add(x, y, z));
    }

    public Vector add(Vector vec)
    {
        return new Vector(this.vector.add(vec.getVector()));
    }

    public Vector subtract(double x, double y, double z)
    {
        return new Vector(vector.subtract(x, y, z));
    }

    public Vector subtract(Vector vec)
    {
        return new Vector(this.vector.subtract(vec.getVector()));
    }

    public Vector multiply(double factor)
    {
        return new Vector(vector.multiply(factor));
    }

    public Vector normalize()
    {
        return new Vector(vector.normalize());
    }

    public Vector midpoint(double x, double y, double z)
    {
        return new Vector(vector.midpoint(x, y, z));
    }

    public Vector midpoint(Vector vec)
    {
        return new Vector(this.vector.midpoint(vec.getVector()));
    }

    public double angle(double x, double y, double z)
    {
        return vector.angle(x, y, z);
    }

    public double angle(Vector v1, Vector v2)
    {
        return vector.angle(v1.vector, v2.vector);
    }

    public double dotProduct(double x, double y, double z)
    {
        return vector.dotProduct(x, y, z);
    }

    public double dotProduct(Vector vec)
    {
        return vector.dotProduct(vec.getVector());
    }

    public Vector crossProduct(double x, double y, double z)
    {
        return new Vector(vector.crossProduct(x, y, z));
    }

    public Vector crossProduct(Vector vec)
    {
        return new Vector(vector.crossProduct(vec.getVector()));
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector1 = (Vector) o;

        return vector != null ? vector.equals(vector1.vector) : vector1.vector == null;

    }

    @Override
    public int hashCode()
    {
        return vector != null ? vector.hashCode() : 0;
    }

    @Override
    public String toString()
    {
        return "Vector[x = " + vector.getX() + ", y = " + vector.getY() + ", z = " + vector.getZ() + '}';
    }
}
