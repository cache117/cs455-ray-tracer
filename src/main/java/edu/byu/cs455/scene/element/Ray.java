package edu.byu.cs455.scene.element;

/**
 * Created by cstaheli on 11/6/2016.
 */
public class Ray
{
    private final Vector origin;
    private final Vector direction;

    public Ray(Vector origin, Vector direction)
    {
        this.origin = origin;
        this.direction = direction.normalize();
    }

    public Vector getOrigin()
    {
        return origin;
    }

    public Vector getDirection()
    {
        return direction;
    }

    public Vector getLocationWithMagnitude(double magnitude)
    {
        return origin.add(direction.multiply(magnitude));
    }

    @Override
    public String toString()
    {
        return String.format("Ray{origin=%s, direction=%s}", origin, direction);
    }

    public static Ray translateRayByEpsilon(Ray ray)
    {
        double epsilon = 0.00001;
        Vector newOrigin = ray.getLocationWithMagnitude(epsilon);
        Vector direction = ray.getDirection();
        return new Ray(newOrigin, direction);
    }
}
