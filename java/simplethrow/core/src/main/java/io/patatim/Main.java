package io.patatim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private float gringe2CercleX;
    private float gringe2CercleY;
    private float gringe2Radius = 80;

    private final float LERP_SPEED = 0.1f;

    // Variable pour accumuler le temps et gérer l'animation
    private float offsetAnimation = 0f;
    // Vitesse de défilement du quadrillage (en pixels par seconde)
    private final float VITESSE_ANIMATION = 40f;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        // Initialisation au centre de l'écran pour éviter un saut brusque au démarrage
        gringe2CercleX = Gdx.graphics.getWidth() / 2f;
        gringe2CercleY = Gdx.graphics.getHeight() / 2f;
    }

    @Override
    public void render() {
        // 1. Calculer la position cible (la souris avec l'axe Y inversé)
        float targetX = Gdx.input.getX();
        float targetY = Gdx.graphics.getHeight() - Gdx.input.getY();

        // 2. Appliquer l'effet Lerp pour une transition fluide
        // On multiplie par (Gdx.graphics.getDeltaTime() * 60) pour rendre le mouvement indépendant du framerate
        float frameSpeed = LERP_SPEED * (Gdx.graphics.getDeltaTime() * 60f);
        // Sécurité pour éviter de dépasser 1.0 si le framerate chute
        frameSpeed = MathUtils.clamp(frameSpeed, 0f, 1f);
        offsetAnimation += Gdx.graphics.getDeltaTime() * VITESSE_ANIMATION;


        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1); // Fond bleu foncé
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT);

        // Position du cercle de base
        float baseCercleX = 300;
        float baseCercleY = 200;
        float baseRadius = 150;

        // Position du cercle "grignoteur" (décalé vers le haut et la droite)
        float gringeCercleX = 300;
        float gringeCercleY = 200;
        float gringeRadius = 50;

        gringe2CercleX = MathUtils.lerp(gringe2CercleX, targetX, frameSpeed);
        gringe2CercleY = MathUtils.lerp(gringe2CercleY, targetY, frameSpeed);

        // --- ÉTAPE 1 : Configurer et dessiner la forme à soustraire dans le Stencil ---
        Gdx.gl.glEnable(GL20.GL_STENCIL_TEST);
        Gdx.gl.glStencilFunc(GL20.GL_ALWAYS, 1, 0xFF);
        Gdx.gl.glStencilOp(GL20.GL_REPLACE, GL20.GL_REPLACE, GL20.GL_REPLACE);

        // Bloquer l'écriture de couleur (on veut que le grignoteur soit invisible)
        Gdx.gl.glColorMask(false, false, false, false);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(gringeCercleX, gringeCercleY, gringeRadius);
        shapeRenderer.circle(gringe2CercleX, gringe2CercleY, gringe2Radius);
        shapeRenderer.end();

        // --- ÉTAPE 2 : Dessiner le cercle de base uniquement là où le Stencil n'est PAS égal à 1 ---
        Gdx.gl.glColorMask(true, true, true, true); // Réactiver la couleur
        Gdx.gl.glStencilFunc(GL20.GL_NOTEQUAL, 1, 0xFF); // Condition de soustraction
        Gdx.gl.glStencilOp(GL20.GL_KEEP, GL20.GL_KEEP, GL20.GL_KEEP);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0.8f, 0, 1); // Cercle de base jaune (style Pac-Man/Lune)
        shapeRenderer.circle(baseCercleX, baseCercleY, baseRadius);
        shapeRenderer.end();


        // B. CALCUL ET RENDU DU QUADRILLAGE ANIMÉ (Lignes fines sombres)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.1f, 0.1f, 0.2f, 1); // Même couleur que le fond

        float epaisseurLigne = 4f;
        float tailleCase = 30f; // Espace entre deux lignes
        float pasTotal = epaisseurLigne + tailleCase;

        // On applique le modulo pour que le décalage boucle parfaitement entre 0 et la taille d'une case
        float decalageBoucle = offsetAnimation % pasTotal;

        // Délimitation de la boîte englobante du cercle
        float minX = baseCercleX - baseRadius;
        float maxX = baseCercleX + baseRadius;
        float minY = baseCercleY - baseRadius;
        float maxY = baseCercleY + baseRadius;

        // Boucle 1 : Dessiner les lignes verticales (défilement vers la droite)
        for (float x = minX - pasTotal; x < maxX + pasTotal; x += pasTotal) {
            shapeRenderer.rect(x + decalageBoucle, minY, epaisseurLigne, baseRadius * 2);
        }

        // Boucle 2 : Dessiner les lignes horizontales (défilement vers le haut)
        for (float y = minY - pasTotal; y < maxY + pasTotal; y += pasTotal) {
            shapeRenderer.rect(minX, y + decalageBoucle, baseRadius * 2, epaisseurLigne);
        }

        shapeRenderer.end();
        // Désactiver le stencil pour le reste des rendus du jeu
        Gdx.gl.glDisable(GL20.GL_STENCIL_TEST);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
