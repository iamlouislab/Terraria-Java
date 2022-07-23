package main;

import affichage.*;


public class Main implements Runnable {
    
    Fenetre fenetre;

    Afficheur afficheur;

    /* Le niveau gérant tout les composants du jeu */
    private Niveau niveau;

    /* Les entrées clavier-souris du joueur */
    Keyboard keyboard;
    Mouse mouse;


    public Main() {

        keyboard = new Keyboard();
        mouse = new Mouse();
        fenetre = new Fenetre();
        afficheur = new Afficheur(fenetre);
        niveau = new Niveau(keyboard, mouse, afficheur);

        /* Le canva reçoit les entrées clavier-souris */
        fenetre.addKeyListener(keyboard);
        fenetre.addMouseListener(mouse);
        fenetre.addMouseMotionListener(mouse);
        fenetre.addMouseWheelListener(mouse);
    }

    /* Lance le thread permettant de démarrer le jeu */
    public synchronized void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    /* La boucle du jeu */
    /* La boucle de jeu met à jour la logique interne du niveau de manière régulière puis l'affiche dans la fenêtre */
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;
        double delta = 0;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (delta >= 1) {
                delta--;
                maj();
            }

            afficher();
        }
    }

    /* Met à jour la logique interne du niveau */
    public void maj() {

        // Mise à jour des entrées clavier
        keyboard.maj();
        mouse.maj();

        // Mise à jour du niveau
        niveau.maj();
    } 

    /* Affiche le niveau dans la fenêtre */
    public void afficher() {
        fenetre.setup();
        afficheur.setUp();

        niveau.afficher();

        afficheur.cleanUp();
        fenetre.afficher();
    }


    /* Lance le jeu */
    public static void main(String[] args) {
        Main game = new Main();
        game.start();
    }
}
