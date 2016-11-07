package edu.byu.cs455.scene.object;

import edu.byu.cs455.scene.Ray;
import edu.byu.cs455.scene.Vector;

import java.awt.*;

/**
 * Created by cstaheli on 11/1/2016.
 */
public class Sphere extends SceneObject
{
    private final Vector center;
    private final double radius;

    public Sphere(Vector center, double radius, Material material, Color materialColor, Color specularHighlightColor, int phongConstant)
    {
        super(material, materialColor, specularHighlightColor, phongConstant);
        this.center = center;
        this.radius = radius;
    }

    public Sphere(Vector center, float radius, Material material, Color materialColor)
    {
        super(material, materialColor);
        this.center = center;
        this.radius = radius;
    }

    public Vector getCenter()
    {
        return center;
    }

    public double getRadius()
    {
        return radius;
    }

    @Override
    public Vector getIntersectionVector(Ray ray)
    {
        //double a = 1; //Always 1
        double b = getBFromRay(ray);
        double c = getCFromRay(ray);
        double bestRoot = calculateBestRoot(b, c);
        if (bestRoot == -1)
        {
            return null;
        }
        else
        {
            return ray.getLocation(bestRoot);
        }
    }

    private double getBFromRay(Ray ray)
    {
        //B = 2(xd*xo - xd*xc + yd*yo - yd*yc + zd*zo - zd*zc)
        double xd = ray.getDirection().x();
        double xo = ray.getOrigin().x();
        double xc = this.getCenter().x();

        double yd = ray.getDirection().y();
        double yo = ray.getOrigin().y();
        double yc = this.getCenter().y();

        double zd = ray.getDirection().z();
        double zo = ray.getOrigin().z();
        double zc = this.getCenter().z();

        double xComponent = (xd * xo) - (xd * xc);
        double yComponent = (yd * yo) - (yd * yc);
        double zComponent = (zd * zo) - (zd * zc);

        return 2 * (xComponent + yComponent + zComponent);
    }

    private double getCFromRay(Ray ray)
    {
        //C = xo^2 - 2*xo*xc + xc^2 + yo*2 - 2*yo*yc + yc^2 + zo^2 - 2*zo*zc + zc^2 - r^2
        double xo = ray.getOrigin().x();
        double xc = this.getCenter().x();

        double yo = ray.getOrigin().y();
        double yc = this.getCenter().y();

        double zo = ray.getOrigin().z();
        double zc = this.getCenter().z();

        double r = this.getRadius();

        double xComponent = (xo * xo) - (2 * xo * xc) + (xc * xc);
        double yComponent = (yo * yo) - (2 * yo * yc) + (yc * yc);
        double zComponent = (zo * zo) - (2 * zo * zc) + (zc * zc);

        return xComponent + yComponent + zComponent - (r * r);
    }

    private double calculateBestRoot(double b, double c)
    {
        double discriminant = calculateDiscriminant(b, c);
        //no real roots
        if (discriminant < 0)
        {
            return -1;
        }
        else
        {
            double squareRoot = Math.sqrt(discriminant);
            //1 real root. Use it.
            if (squareRoot == 0)
            {
                double root = (-b / 2);
                if (root == 0)
                {
                    return -1;
                }
                else
                {
                    return root;
                }
            }
            //1 or 2 real roots
            else
            {
                double smallerQuadratic = calculateQuadratic(b, squareRoot, true);
                //smaller intersection behind object. Check next real root.
                if (smallerQuadratic <= 0)
                {
                    double largerQuadratic = calculateQuadratic(b, squareRoot, false);
                    //both intersections behind object. No useful roots.
                    if (largerQuadratic <= 0)
                    {
                        return -1;
                    }
                    else
                    {
                        return largerQuadratic;
                    }
                }
                else
                {
                    return smallerQuadratic;
                }
            }

        }
    }

    private double calculateDiscriminant(double b, double c)
    {
        double a = 1; //Always 1
        return (b * b) - 4 * a * c;
    }

    private double calculateQuadratic(double b, double squareRoot, boolean smallerQuadratic)
    {
        double a = 1; //Always 1
        if (smallerQuadratic)
        {
            return (-b - squareRoot) / (2 * a);
        }
        else
        {
            return (-b + squareRoot) / (2 * a);
        }
    }

}
