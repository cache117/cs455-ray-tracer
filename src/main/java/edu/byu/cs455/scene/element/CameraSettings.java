package edu.byu.cs455.scene.element;

/**
 * Created by cstaheli on 11/2/2016.
 */
public class CameraSettings
{
    private final Vector lookAt, lookFrom, lookUp;
    private final double fieldOfView;

    public CameraSettings(Vector lookAt, Vector lookFrom, Vector lookUp, double fieldOfView)
    {
        this.lookAt = lookAt;
        this.lookFrom = lookFrom;
        this.lookUp = lookUp;
        this.fieldOfView = fieldOfView;
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

    public double getFieldOfView()
    {
        return fieldOfView;
    }

    public double getViewPortSize()
    {
        double distance = lookFrom.subtract(lookAt).z();
        return Math.tan(Math.toRadians(fieldOfView)) * distance;
    }
}
