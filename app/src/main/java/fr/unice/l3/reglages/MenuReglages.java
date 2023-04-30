package fr.unice.l3.reglages;

import java.util.Scanner;

public class MenuReglages {
    private Scanner scanner;

    public MenuReglages() {
        scanner = new Scanner(System.in);
    }

    public String choisirNomJoueur(int joueurNum) {
        System.out.println("Entrez le nom du joueur " + joueurNum + " :");
        return scanner.nextLine();
    }

    public int choisirTypeJoueur(int joueurNum) {
        System.out.println("Choisissez le type du joueur " + joueurNum + " :");
        System.out.println("1. Humain");
        System.out.println("2. IA - JoueurSmart");
        System.out.println("3. IA - JoueurVerySmart");
        int choix = scanner.nextInt();
        scanner.nextLine(); // Consomme le saut de ligne restant
        return choix;
    }
}

