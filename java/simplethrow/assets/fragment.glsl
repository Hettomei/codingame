#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform float u_time;
uniform vec2 u_gringePos;
uniform float u_gringeRadius;

void main() {
    // 1. Centrer les coordonnées de texture
    vec2 uv = v_texCoords - vec2(0.5);

    // 2. Calcul des distances pour les formes (Base et Grignoteur)
    float distToBase = length(uv);
    vec2 gringeUV = u_gringePos - vec2(0.5);
    float distToGringe = length(uv - gringeUV);

    // On stocke le rayon maximum de notre cercle (0.5 = bords du carré)
    float rayonMax = 0.45; // Réduit légèrement à 0.45 pour laisser de la place au Glow dans le carré

    // Condition de découpe pour le grignoteur uniquement (il coupe aussi la lueur)
    if (distToGringe < u_gringeRadius) {
        discard;
    }

    // 3. CALCUL DE LA LUEUR (GLOW) EXTÉRIEURE
    float intensiteGlow = 0.0;
    if (distToBase > rayonMax) {
        // Formule mathématique d'atténuation : 1 / (distance au carré)
        // Plus on s'éloigne du bord (rayonMax), plus la lueur faiblit vite
        float distanceAuBord = distToBase - rayonMax;
        intensiteGlow = 0.015 / (distanceAuBord + 0.015);

        // Atténuation douce pour que le glow ne touche pas brutalement les bords du carré d'affichage
        intensiteGlow *= smoothstep(0.5, rayonMax, distToBase);
    }

    // 4. EFFET VAGUE D'EAU (Uniquement pour le motif intérieur)
    float amplitude = 0.01;
    float frequenceVague = 15.0;
    float vitesseVague = 3.0;

    vec2 uvDeforme = uv;
    uvDeforme.x += sin(uv.y * frequenceVague + u_time * vitesseVague) * amplitude;
    uvDeforme.y += cos(uv.x * frequenceVague + u_time * vitesseVague) * amplitude;

    // 5. CALCUL DU QUADRILLAGE ANIMÉ
    float frequenceGrille = 50.0;
    float vitesseGrille = 5.0;
    float ligneVert = smoothstep(0.8, 0.9, sin((uvDeforme.x + u_time * 0.05) * frequenceGrille * vitesseGrille));
    float ligneHori = smoothstep(0.8, 0.9, sin((uvDeforme.y + u_time * 0.05) * frequenceGrille * vitesseGrille));

    // 6. CALCUL DES COULEURS ET DU DÉGRADÉ
    float facteurDegrade = uv.y + 0.5;
    vec4 couleurEau = vec4(0.0, 0.6, 0.8, 1.0);  // Bleu Cyan
    vec4 couleurJaune = vec4(1.0, 0.8, 0.0, 1.0); // Jaune
    vec4 couleurCercleBase = mix(couleurEau, couleurJaune, facteurDegrade);

    // 7. COMPOSITION FINALE DES PIXELS
    vec4 couleurFinale;

    if (distToBase <= rayonMax) {
        // À l'intérieur du cercle : afficher le dégradé + quadrillage
        if (ligneVert > 0.5 || ligneHori > 0.5) {
            couleurFinale = vec4(0.1, 0.1, 0.2, 1.0); // Fond sombre
        } else {
            couleurFinale = couleurCercleBase;
        }
    } else {
        // À l'extérieur du cercle : afficher uniquement la lueur (Glow)
        // La lueur hérite dynamiquement de la couleur du dégradé selon sa hauteur
        couleurFinale = couleurCercleBase * intensiteGlow;
    }

    // Sécurité pour le compilateur (u_texture obligatoire pour le SpriteBatch)
    vec4 dummyTexture = texture2D(u_texture, v_texCoords) * 0.0001;
    gl_FragColor = couleurFinale + dummyTexture;
}
