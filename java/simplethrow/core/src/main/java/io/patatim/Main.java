package io.patatim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1); // Fond bleu foncé
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT);

//        // 2. Draw a Filled Circle
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(1, 0, 0, 1); // Red color
//        // Arguments: centerX, centerY, radius, segments (smoothness)
//        shapeRenderer.circle(400, 300, 50, 30);
//        shapeRenderer.end();


        // Position du cercle de base
        float baseCercleX = 300;
        float baseCercleY = 200;
        float baseRadius = 150;

        // Position du cercle "grignoteur" (décalé vers le haut et la droite)
        float gringeCercleX = 300;
        float gringeCercleY = 200;
        float gringeRadius = 50;

        // --- ÉTAPE 1 : Configurer et dessiner la forme à soustraire dans le Stencil ---
        Gdx.gl.glEnable(GL20.GL_STENCIL_TEST);
        Gdx.gl.glStencilFunc(GL20.GL_ALWAYS, 1, 0xFF);
        Gdx.gl.glStencilOp(GL20.GL_REPLACE, GL20.GL_REPLACE, GL20.GL_REPLACE);

        // Bloquer l'écriture de couleur (on veut que le grignoteur soit invisible)
        Gdx.gl.glColorMask(false, false, false, false);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(gringeCercleX, gringeCercleY, gringeRadius);
        shapeRenderer.end();

        // --- ÉTAPE 2 : Dessiner le cercle de base uniquement là où le Stencil n'est PAS égal à 1 ---
        Gdx.gl.glColorMask(true, true, true, true); // Réactiver la couleur
        Gdx.gl.glStencilFunc(GL20.GL_NOTEQUAL, 1, 0xFF); // Condition de soustraction
        Gdx.gl.glStencilOp(GL20.GL_KEEP, GL20.GL_KEEP, GL20.GL_KEEP);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0.8f, 0, 1); // Cercle de base jaune (style Pac-Man/Lune)
        shapeRenderer.circle(baseCercleX, baseCercleY, baseRadius);
        shapeRenderer.end();

        // Désactiver le stencil pour le reste des rendus du jeu
        Gdx.gl.glDisable(GL20.GL_STENCIL_TEST);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
