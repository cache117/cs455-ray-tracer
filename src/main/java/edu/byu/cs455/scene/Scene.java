package edu.byu.cs455.scene;

import edu.byu.cs455.scene.element.CameraSettings;
import edu.byu.cs455.scene.element.Light;
import edu.byu.cs455.scene.element.Ray;
import edu.byu.cs455.scene.element.Vector;
import edu.byu.cs455.scene.object.SceneObject;
import edu.byu.cs455.scene.object.Sphere;
import edu.byu.cs455.scene.object.Triangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cstaheli on 11/1/2016.
 */
public class Scene
{
    private static final int IMAGE_DIMENSION = 512;
    private static final int IMAGE_HEIGHT = IMAGE_DIMENSION;
    private static final int IMAGE_WIDTH = IMAGE_DIMENSION;

    public static final int MAXIMUM_TRACE_DEPTH = 2;

    private final List<Sphere> spheres;
    private final List<Triangle> triangles;
    private final CameraSettings cameraSettings;
    private final Light light;
    private int tracingDepth;

    public Scene(List<Sphere> spheres, List<Triangle> triangles, CameraSettings cameraSettings, Light light)
    {
        this.spheres = spheres;
        this.triangles = triangles;
        this.cameraSettings = cameraSettings;
        this.light = light;
        tracingDepth = 0;
    }

    public List<Sphere> getSpheres()
    {
        return Collections.unmodifiableList(spheres);
    }

    public List<Triangle> getTriangles()
    {
        return Collections.unmodifiableList(triangles);
    }

    public CameraSettings getCameraSettings()
    {
        return cameraSettings;
    }

    public Vector getLookFrom()
    {
        return cameraSettings.getLookFrom();
    }

    public Light getLight()
    {
        return light;
    }

    public Vector getDirectionToLight()
    {
        return light.getDirectionToLight();
    }

    public Color getLightColor()
    {
        return light.getLightColor();
    }

    public Color getAmbientLightColor()
    {
        return light.getAmbientLightColor();
    }

    public Color getBackgroundColor()
    {
        return light.getBackgroundColor();
    }

    private int getTracingDepth()
    {
        return tracingDepth;
    }

    private void incrementTracingDepth()
    {
        ++tracingDepth;
    }

    private void resetTracingDepth()
    {
        tracingDepth = 0;
    }

    public void rayTraceToFile(String fileName)
    {
        try
        {
            ImageIO.write(rayTraceScene(), "jpg", new File(fileName));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private BufferedImage rayTraceScene()
    {
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
        for (int x = 0; x < raster.getWidth(); ++x)
        {
            for (int y = 0; y < raster.getHeight(); ++y)
            {
                raster.setPixel(x, y, rayTracePixel(x, y));
            }
        }
        return image;
    }

    private int[] rayTracePixel(int x, int y)
    {
        resetTracingDepth();
        Vector worldSpaceOrigin = getWorldSpaceCoordinate(x, y);
        Ray ray = getRay(worldSpaceOrigin);
        Color colorSeen = getRayColor(ray);
        return getRGBArrayOfColor(colorSeen);
    }

    private Vector getWorldSpaceCoordinate(int i, int j)
    {
        double viewportSize = cameraSettings.getViewPortSize();
        double iStep = (viewportSize * 2) / (double) IMAGE_WIDTH;
        double jStep = (viewportSize * 2) / (double) IMAGE_HEIGHT;
        double u = i * iStep + (iStep / 2) - viewportSize;
        double v = j * jStep + (jStep / 2) - viewportSize;
        double w = 0;
        return new Vector(u, v, w);
    }

    public Color getRayColor(Ray ray)
    {
        incrementTracingDepth();

        double closest = Integer.MAX_VALUE;
        Vector actualIntersection = null;
        SceneObject actualSceneObject = null;
        for (SceneObject sceneObject : getSceneObjects())
        {
            Vector intersection = sceneObject.getIntersectionVector(ray);
            if (intersection != null)
            {
                if (intersection.z() < closest)
                {
                    closest = intersection.z();
                    actualIntersection = intersection;
                    actualSceneObject = sceneObject;
                }
            }
        }
        if (actualIntersection != null)
        {
            return actualSceneObject.calculateIlluminationModel(actualIntersection,
                    isInShadow(actualIntersection),
                    this,
                    ray,
                    getTracingDepth());
        }
        else
        {
            return getBackgroundColor();
        }
    }

    private boolean isInShadow(Vector pointToCheck)
    {
        Ray ray = getShadowRay(pointToCheck);
        for (SceneObject sceneObject : getSceneObjects())
        {
            Vector intersection = sceneObject.getIntersectionVector(ray);
            if (intersection != null)
            {
                return true;
            }
        }
        return false;
    }

    private Ray getShadowRay(Vector pointToCheck)
    {
        double epsilon = 0.00001;
        Vector direction = getRayDirection(getDirectionToLight(), pointToCheck);
        return Ray.translateRayByEpsilon(new Ray(pointToCheck, direction));
    }

    private Vector getRayDirection(Vector point, Vector origin)
    {
        return point.subtract(origin).normalize();
    }

    private List<SceneObject> getSceneObjects()
    {
        List<SceneObject> objects = new ArrayList<>();
        objects.addAll(spheres);
        objects.addAll(triangles);
        return objects;
    }

    private Ray getRay(Vector worldSpaceCoordinate)
    {
        Vector eye = cameraSettings.getLookFrom();
        Vector direction = getRayDirection(worldSpaceCoordinate, eye);
        return new Ray(eye, direction);
    }

    private int[] getRGBArrayOfColor(Color color)
    {
        return new int[]{color.getRed(), color.getGreen(), color.getBlue()};
    }
}
