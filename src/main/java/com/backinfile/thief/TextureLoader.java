package com.backinfile.thief;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.utils.GdxRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
public class TextureLoader {
	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	public static final Logger logger = LogManager.getLogger(TextureLoader.class.getName());

	public static Texture getTexture(final String textureString) {
		if (textures.get(textureString) == null) {
			try {
				loadTexture(textureString);
			} catch (GdxRuntimeException e) {
				logger.error("Could not find texture: " + textureString);
				return getTexture(ModInfo.ModName + "/images/ui/missing_texture.png");
			}
		}
		return textures.get(textureString);
	}

	private static void loadTexture(final String textureString) throws GdxRuntimeException {
		logger.info("DefaultMod | Loading Texture: " + textureString);
		Texture texture = new Texture(textureString);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		textures.put(textureString, texture);
	}
}
