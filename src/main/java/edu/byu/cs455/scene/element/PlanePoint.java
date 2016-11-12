package edu.byu.cs455.scene.element;

import java.awt.geom.Point2D;

/**
 * Created by cstaheli on 11/9/2016.
 */
public class PlanePoint
{
    private Point2D.Double planePoint;

    public PlanePoint(double u, double v)
    {
        planePoint = new Point2D.Double(u, v);
    }

    public double getU()
    {
        return planePoint.getX();
    }

    public double getV()
    {
        return planePoint.getY();
    }

    public void setU(double u)
    {
        planePoint.x = u;
    }

    public void setV(double v)
    {
        planePoint.y = v;
    }

    public PlanePoint subtract(PlanePoint other)
    {
        return new PlanePoint(this.getU() - other.getU(), this.getV() - other.getV());
    }

    @Override
    public String toString()
    {
        return String.format("PlanePoint{%f, %f}", planePoint.x, planePoint.y);
    }
}
