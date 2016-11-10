package edu.byu.cs455.scene.element;

/**
 * Created by cstaheli on 11/2/2016.
 */
public class CameraSettings
{
    private final Vector lookAt, lookFrom, lookUp;
    private final Vector n, u, v;
    private final float fieldOfView;

    public CameraSettings(Vector lookAt, Vector lookFrom, Vector lookUp, float fieldOfView)
    {
        this.lookAt = lookAt;
        this.lookFrom = lookFrom;
        this.lookUp = lookUp;
        this.fieldOfView = fieldOfView;
        this.n = this.lookFrom.subtract(this.lookAt);
        this.u = this.lookUp.crossProduct(this.n);
        this.v = this.n.crossProduct(this.u);
    }

    public Vector getLookAt()
    {
        return lookAt;
    }

    public Vector getLookFrom()
    {
        return lookFrom;
    }

    public Vector getLookUp()
    {
        return lookUp;
    }

    public float getFieldOfView()
    {
        return fieldOfView;
    }

    public Vector getN()
    {
        return n;
    }

    public Vector getU()
    {
        return u;
    }

    public Vector getV()
    {
        return v;
    }

    public double getViewPortSize()
    {
        double distance = n.z();
        return Math.tan(Math.toRadians(fieldOfView / 2)) * distance;
    }
}
