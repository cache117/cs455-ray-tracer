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
        this.direction = direction;
    }

    public Vector getOrigin()
    {
        return origin;
    }

    public Vector getDirection()
    {
        return direction;
    }

    public Vector getLocation(double magnitude)
    {
        return origin.add(direction.multiply(magnitude));
    }
}
