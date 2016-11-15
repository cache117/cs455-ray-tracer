package edu.byu.cs455;

import edu.byu.cs455.scene.element.CameraSettings;
import edu.byu.cs455.scene.element.Light;
import edu.byu.cs455.scene.Scene;
import edu.byu.cs455.scene.element.Vector;
import edu.byu.cs455.scene.material.Reflective;
import edu.byu.cs455.scene.material.Refractive;
import edu.byu.cs455.scene.object.Sphere;
import edu.byu.cs455.scene.object.Triangle;
import edu.byu.cs455.scene.material.Diffuse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cstaheli on 10/31/2016.
 */
public class Main
{
    public static void main(String... args)
    {
        List<Sphere> spheres;
        List<Triangle> triangles;
        CameraSettings cameraSettings;
        Light light;
        Scene scene;

        spheres = new ArrayList<>();
        triangles = new ArrayList<>();
        spheres.add(new Sphere(new Vector(0.35, 0.0, -0.1), 0.05, new Diffuse(new Color(1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f), 4)));
        spheres.add(new Sphere(new Vector(0.2, 0.0, -0.1), 0.075, new Diffuse(new Color(1.0f, 0.0f, 0.0f), new Color(1.0f, 0.5f, 0.5f), 32)));
        spheres.add(new Sphere(new Vector(-0.6, 0.0, 0.0), 0.3, new Diffuse(new Color(0.0f, 1.0f, 0.0f), new Color(0.5f, 1.0f, 0.5f), 32)));
        triangles.add(new Triangle(new Vector(0.3, -0.3, -0.4), new Vector(0.0, 0.3, -0.1), new Vector(-0.3, -0.3, 0.2), new Diffuse(new Color(1.0f, 1.0f, 1.0f), new Color(0.0f, 0.0f, 1.0f), 32)));
        triangles.add(new Triangle(new Vector(-0.2, 0.1, 0.1), new Vector(-0.2, -0.5, 0.2), new Vector(-0.2, 0.1, -0.3), new Diffuse(new Color(1.0f, 1.0f, 0.0f), new Color(1.0f, 1.0f, 0.0f), 4)));
        cameraSettings = new CameraSettings(new Vector(0.0, 0.0, 0.0), new Vector(0.0, 0.0, 1.0), new Vector(0.0, 1.0, 0.0), 28);
        light = new Light(new Vector(1.0, 0.0, 0.0), new Color(1.0f, 1.0f, 1.0f), new Color(0.1f, 0.1f, 0.1f), new Color(0.2f, 0.2f, 0.2f));
        scene = new Scene(spheres, triangles, cameraSettings, light);
        scene.rayTraceToFile(args[0]);

        spheres = new ArrayList<>();
        triangles = new ArrayList<>();
        spheres.add(new Sphere(new Vector(0.0, 0.3, 0.0), 0.2, new Reflective(new Color(1.0f, 1.0f, 1.0f))));
        triangles.add(new Triangle(new Vector(0.3, -0.3, -0.4), new Vector(0.0, 0.3, -0.1), new Vector(-0.3, -0.3, 0.2), new Diffuse(new Color(0.0f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f), 32)));
        triangles.add(new Triangle(new Vector(-0.2, 0.1, 0.1), new Vector(-0.2, -0.5, 0.2), new Vector(-0.2, 0.1, -0.3), new Diffuse(new Color(1.0f, 1.0f, 0.0f), new Color(1.0f, 1.0f, 1.0f), 4)));
        cameraSettings = new CameraSettings(new Vector(0.0, 0.0, 0.0), new Vector(0.0, 0.0, 1.2), new Vector(0.0, 1.0, 0.0), 55);
        light = new Light(new Vector(0.0, 1.0, 0.0), new Color(1.0f, 1.0f, 1.0f), new Color(0.0f, 0.0f, 0.0f), new Color(0.2f, 0.2f, 0.2f));
        scene = new Scene(spheres, triangles, cameraSettings, light);
        scene.rayTraceToFile(args[1]);

        spheres = new ArrayList<>();
        triangles = new ArrayList<>();
        spheres.add(new Sphere(new Vector(0.0, 0.0, 0.0), 0.2322, new Refractive(new Color(1.0f, 1.0f, 1.0f), 1.333)));
        spheres.add(new Sphere(new Vector(0.25, 0.25, -0.6), 0.1161, new Diffuse(new Color(1.0f, 1.0f, 0.0f), new Color(0.5f, 1.0f, 0.5f), 4)));
        spheres.add(new Sphere(new Vector(-0.25, 0.25, -0.6), 0.1161, new Diffuse(new Color(1.0f, 0.0f, 0.0f), new Color(0.0f, 0.0f, 0.0f), 4)));
        spheres.add(new Sphere(new Vector(-0.25, -0.25, -0.6), 0.1161, new Diffuse(new Color(0.0f, 0.0f, 1.0f), new Color(0.0f, 0.0f, 0.0f), 4)));
        spheres.add(new Sphere(new Vector(0.25, -0.25, -0.6), 0.1161, new Diffuse(new Color(0.0f, 1.0f, 0.0f), new Color(0.0f, 0.0f, 0.0f), 4)));
        cameraSettings = new CameraSettings(new Vector(0.0, 0.0, 0.0), new Vector(0.0, 0.0, 1.2), new Vector(0.0, 1.0, 0.0), 35);
        light = new Light(new Vector(0.0, 0.0, 1.0), new Color(1.0f, 1.0f, 1.0f), new Color(0.0f, 0.0f, 0.0f), new Color(0.2f, 0.2f, 0.2f));
        scene = new Scene(spheres, triangles, cameraSettings, light);
        scene.rayTraceToFile(args[2]);
    }
}
