package edu.byu.cs455;

import edu.byu.cs455.scene.CameraSettings;
import edu.byu.cs455.scene.Light;
import edu.byu.cs455.scene.Scene;
import edu.byu.cs455.scene.Vector;
import edu.byu.cs455.scene.object.Material;
import edu.byu.cs455.scene.object.Sphere;
import edu.byu.cs455.scene.object.Triangle;

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
        List<Sphere> spheres = new ArrayList<>();
        List<Triangle> triangles = new ArrayList<>();
        spheres.add(new Sphere(new Vector(0.35f, 0.0f, -0.1f), 0.05f, Material.DIFFUSE, new Color(1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f), 4));
        spheres.add(new Sphere(new Vector(0.2f, 0.0f, -0.1f), 0.75f, Material.DIFFUSE, new Color(1.0f, 0.0f, 0.0f), new Color(0.5f, 1.0f, 0.5f), 32));
        spheres.add(new Sphere(new Vector(-0.6f, 0.0f, 0.0f), 0.3f, Material.DIFFUSE, new Color(0.0f, 1.0f, 0.0f),  new Color(0.5f, 1.0f, 0.5f), 32));
        //triangles.add(new Triangle(new Vector(0.3f, -0.3f, -0.4f), new Vector(0.0f, 0.3f, -0.1f), new Vector(-0.3f, -0.3f, 0.2f), Material.DIFFUSE, new Color(1.0f, 1.0f, 1.0f), new Color(0.0f, 0.0f, 1.0f), 32));
        //triangles.add(new Triangle(new Vector(-0.2f, 0.1f, 0.1f), new Vector(-0.2f, -0.5f, 0.2f), new Vector(-0.2f, 0.1f, -0.3f), Material.DIFFUSE, new Color(1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 0.0f), 4));
        CameraSettings cameraSettings = new CameraSettings(new Vector(0.0f, 0.0f, 0.0f), new Vector(0.0f, 0.0f, 1.0f), new Vector(0.0f, 1.0f, 0.0f), 28);
        Light light = new Light(new Vector(1.0f, 0.0f, 0.0f), new Color(1.0f, 1.0f, 1.0f), new Color(0.1f, 0.1f, 0.1f), new Color(0.2f, 0.2f, 0.2f));

//        spheres.add(new Sphere(new Vector(0.0f, 0.3f, 0.0f), 0.2f, Material.REFLECTIVE, new Color(1.0f, 1.0f, 1.0f)));
//        triangles.add(new Triangle(new Vector(0.3f, -0.3f, -0.4f), new Vector(0.0f, 0.3f, -0.1f), new Vector(-0.3f, -0.3f, 0.2f), Material.DIFFUSE, new Color(0.0f, 0.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f), 32));
//        triangles.add(new Triangle(new Vector(-0.2f, 0.1f, 0.1f), new Vector(-0.2f, -0.5f, 0.2f), new Vector(-0.2f, 0.1f, -0.3f), Material.DIFFUSE, new Color(1.0f, 1.0f, 0.0f), new Color(1.0f, 1.0f, 1.0f), 4));
//        CameraSettings cameraSettings = new CameraSettings(new Vector(0.0f, 0.0f, 0.0f), new Vector(0.0f, 0.0f, 1.2f), new Vector(0.0f, 1.0f, 0.0f), 55);
//        Light light = new Light(new Vector(0.0f, 1.0f, 0.0f), new Color(1.0f, 1.0f, 1.0f), new Color(0.0f, 0.0f, 0.0f), new Color(0.2f, 0.2f, 0.2f));
        Scene scene = new Scene(spheres, triangles, cameraSettings, light);
        scene.rayTraceToFile(args[0]);
    }
}
