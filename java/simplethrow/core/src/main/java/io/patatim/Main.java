package io.patatim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // 2. Draw a Filled Circle
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1); // Red color
        // Arguments: centerX, centerY, radius, segments (smoothness)
        shapeRenderer.circle(400, 300, 50, 30);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
