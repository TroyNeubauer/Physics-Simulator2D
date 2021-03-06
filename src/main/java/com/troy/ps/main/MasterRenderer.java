package com.troy.ps.main;

import java.io.*;

import com.troy.ps.renderer.PointRenderer;
import com.troy.ps.world.Light;
import com.troy.ps.world.planet.*;
import com.troyberry.logging.Timer;
import com.troyberry.math.*;
import com.troyberry.opengl.engine.text.*;
import com.troyberry.opengl.loading.texture.*;
import com.troyberry.opengl.util.*;

public class MasterRenderer implements Resource {

	private PointRenderer pointRenderer;
	private PlanetRenderer planetRenderer;

	private Texture planetCube;
	private TextureBuilder builder = new TextureBuilder();

	private GUIText text;

	private TextMaster textMaster;

	private Texture2D fontAtlas;

	private FontType font;

	public MasterRenderer(Window window) {
		Timer t = new Timer();
		try {
			this.fontAtlas = new TextureLocation2D(new File("./res/candara.png")).load().load();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		t.stop();
		System.out.println("font texture load time " + t.getTime());
		t.reset();
		this.font = new FontType(fontAtlas, new File("./res/candara.fnt"), window, 0.5f, 0.1f);
		t.stop();
		System.out.println("font gen time " + t.getTime());

		this.textMaster = new TextMaster();
		builder.repeat();
		pointRenderer = new PointRenderer();
		planetRenderer = new PlanetRenderer();
		GLUtil.checkForErrors("Game Loop");
		try {
			planetCube = new TextureLocation3D(new File("./res/Earth Cube Map.png")).load().load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.text = new GUIText("Test!", 10, font, new Vector2f(0, 0.0f), 1, false, textMaster);
		// text.setColor(1, 0, 0);
		text.enableOutline(new Vector3f(0, 1, 0), 0.5f, 0.4f);
		GLUtil.checkForErrors("Master Renderer init");
		
	}

	public static int get() {
		double b = 0.0;
		
		return (int) b;
	}

	public void render(ICamera camera, Window window, Planet planet, Light light) {
		planetRenderer.render(planet, camera, light, planetCube);
		if (Controls.FORWARD.hasBeenPressed()) {
			text.toggleOutline();
		}
		textMaster.render();
	}

	public void delete() {
		pointRenderer.delete();
		planetRenderer.delete();
		textMaster.delete();
	}

}
