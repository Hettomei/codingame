package io.patatim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer; // Requis pour dessiner les bonus simplement
    private Texture whiteTexture;
    private ShaderProgram shader;

    // Variables pour le Lerp du grignoteur
    private Vector2 gringePos = new Vector2();
    private final float LERP_SPEED = 0.1f;
    private float timeElapsed = 0f;

    // Paramètres du cercle de base
    private float baseCercleX, baseCercleY;
    private final float BASE_RADIUS_VISUEL = 820f; // Taille du carré pour le shader (inclut le glow)
    private final float BASE_CIRCLE_RADIUS = 350f; // Le vrai rayon mathématique du cercle jaune

    // Paramètres du grignoteur
    private float gringeRadiusPixels = 60f; // Rayon de départ

    // Gestion des Bonus
    private Array<Vector2> bonusList;
    private final float BONUS_RADIUS = 8f;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        // 1. Texture blanche pour le shader
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        whiteTexture = new Texture(pixmap);
        pixmap.dispose();

        // 2. Configuration du shader
        shader = new ShaderProgram(SpriteBatch.createDefaultShader().getVertexShaderSource(), Gdx.files.internal("fragment.glsl").readString());
        shader.pedantic = false; // Sécurité anti-crash
        batch.setShader(shader);

        gringePos.set(0f, 0f);

        // 3. Générer des bonus aléatoires coincés DANS le cercle jaune
        bonusList = new Array<>();
        baseCercleX = Gdx.graphics.getWidth() / 2f;
        baseCercleY = Gdx.graphics.getHeight() / 2f;

        for (int i = 0; i < 5; i++) {
            addBonus();
        }
    }

    private void addBonus() {
        // Astuce mathématique pour distribuer des points aléatoirement dans un cercle
        float angle = MathUtils.random(0f, MathUtils.PI2);
        float distance = MathUtils.random(0f, BASE_CIRCLE_RADIUS - BONUS_RADIUS);

        float x = baseCercleX + MathUtils.cos(angle) * distance;
        float y = baseCercleY + MathUtils.sin(angle) * distance;
        bonusList.add(new Vector2(x, y));
    }

    @Override
    public void render() {
        timeElapsed += Gdx.graphics.getDeltaTime();

        baseCercleX = Gdx.graphics.getWidth() / 2f;
        baseCercleY = Gdx.graphics.getHeight() / 2f;

        // 1. Mouvement de la souris : ON REMET L'INVERSION ICI pour le monde du jeu (Bas en haut)
        float targetX = Gdx.input.getX();
        float targetY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Le 0 est maintenant bien en bas !

        float frameSpeed = MathUtils.clamp(LERP_SPEED * (Gdx.graphics.getDeltaTime() * 60f), 0f, 1f);
        gringePos.x = MathUtils.lerp(gringePos.x, targetX, frameSpeed);
        gringePos.y = MathUtils.lerp(gringePos.y, targetY, frameSpeed);

        // 2. LA GESTION DES COLLISIONS (Maintenant les coordonnées Y correspondent parfaitement aux points rouges !)
        for (int i = bonusList.size - 1; i >= 0; i--) {
            Vector2 bonus = bonusList.get(i);
            float distanceAuGrignoteur = gringePos.dst(bonus);

            if (distanceAuGrignoteur < (gringeRadiusPixels + BONUS_RADIUS)) {
                bonusList.removeIndex(i);
                gringeRadiusPixels += 3f;
                addBonus();
            }
        }

        // 3. RENDU GRAPHIQUE
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float coinGaucheX = baseCercleX - (BASE_RADIUS_VISUEL / 2f);
        float coinBasY = baseCercleY - (BASE_RADIUS_VISUEL / 2f);

        // 4. CONVERSION POUR LE SHADER : C'est ici qu'on adapte l'axe Y pour le shader
        float localGringeX = (gringePos.x - coinGaucheX) / BASE_RADIUS_VISUEL;
        // On inverse la coordonnée locale pour que le shader dessine au bon endroit
        float localGringeY = 1.0f - ((gringePos.y - coinBasY) / BASE_RADIUS_VISUEL);

        float localGringeRadius = gringeRadiusPixels / BASE_RADIUS_VISUEL;

        batch.begin();
        shader.setUniformf("u_time", timeElapsed);
        shader.setUniformf("u_gringePos", localGringeX, localGringeY);
        shader.setUniformf("u_gringeRadius", localGringeRadius);
        batch.draw(whiteTexture, coinGaucheX, coinBasY, BASE_RADIUS_VISUEL, BASE_RADIUS_VISUEL);
        batch.end();

        // Dessiner les bonus restants
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(1, 0.3f, 0.3f, 1);
        for (Vector2 bonus : bonusList) {
            shapeRenderer.circle(bonus.x, bonus.y, BONUS_RADIUS);
        }
        shapeRenderer.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
        whiteTexture.dispose();
        shader.dispose();
        shapeRenderer.dispose();
    }
}
