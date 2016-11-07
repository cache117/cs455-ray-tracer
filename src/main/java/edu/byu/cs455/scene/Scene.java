package edu.byu.cs455.scene;

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
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by cstaheli on 11/1/2016.
 */
public class Scene
{
    private static final int IMAGE_DIMENSION = 512;

    private final List<Sphere> spheres;
    private final List<Triangle> triangles;
    private final CameraSettings cameraSettings;
    private final Light light;

    public Scene(List<Sphere> spheres, List<Triangle> triangles, CameraSettings cameraSettings, Light light)
    {
        this.spheres = spheres;
        this.triangles = triangles;
        this.cameraSettings = cameraSettings;
        this.light = light;
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

    public Light getLight()
    {
        return light;
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
        BufferedImage image = new BufferedImage(IMAGE_DIMENSION, IMAGE_DIMENSION, BufferedImage.TYPE_INT_RGB);
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
        List<SceneObject> objects = new ArrayList<>();
        objects.addAll(spheres);
        objects.addAll(triangles);
        double closest = Integer.MAX_VALUE;
        Color colorSeen = light.getBackgroundColor();
        for (SceneObject sceneObject : objects)
        {
            int iMin = 0;
            int iMax = IMAGE_DIMENSION - 1;
            double uMax = 1;
            double uMin = -1;
            double uNew = getNewViewportWindowPoint(x, iMin, iMax, uMin, uMax);

            int jMin = 0;
            int jMax = IMAGE_DIMENSION - 1;
            double vMax = 1;
            double vMin = -1;
            double vNew = getNewViewportWindowPoint(y, jMin, jMax, vMin, vMax);

            double wNew = 0;

            Vector n = cameraSettings.getN();
            Vector u = cameraSettings.getU();
            Vector v = cameraSettings.getV();

            Vector worldSpaceOrigin = cameraSettings.getLookAt().add(u.multiply(uNew)).add(v.multiply(vNew)).add(n.multiply(wNew));
            Ray ray = new Ray(worldSpaceOrigin, new Vector(0, 0, 1));
            Vector intersection = sceneObject.getIntersectionVector(ray);
            if (intersection != null)
            {
                if (intersection.z() < closest)
                {
                    closest = intersection.z();
                    colorSeen = sceneObject.getMaterialColor();
                }
            }
        }
        return getRGBArrayOfColor(colorSeen);
    }

    private double getNewViewportWindowPoint(int point, int pointMin, int pointMax, double newPointMin, double newPointMax)
    {
        return (point - pointMin) * ((newPointMin - newPointMax) / (pointMax - pointMin)) + newPointMax;
    }

    private int[] getRGBArrayOfColor(Color color)
    {
        return new int[]{color.getRed(), color.getGreen(), color.getBlue()};
    }

    private int[] getRandomPixelsRGB()
    {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new int[]{random.nextInt(0, 256), random.nextInt(0, 256), random.nextInt(0, 256)};
    }
}
