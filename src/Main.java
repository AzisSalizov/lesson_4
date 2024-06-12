import java.util.Random;

public class Main {
    public static int bossHealth = 770;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {290, 260, 250, 230, 550, 215, 400, 101};
    public static int[] heroesDamage = {35, 25, 15, 0, 8, 12, 0, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Witcher", "Thor"};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        showStatics();
        while (!isGameOver()) {
            round();
        }
    }

    public static void setHeroesHealth() {
        int witcherIndex = -1;
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesAttackType[i].equals("Witcher")) {
                witcherIndex = i;
                break;
            }
        }
        int medicIndex = -1;
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesAttackType[i].equals("Medic")) {
                medicIndex = i;
                break;
            }
        }

        if (medicIndex != -1 && heroesHealth[medicIndex] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (i != medicIndex && heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                    Random random = new Random();
                    int medicHealed = random.nextInt(50) + 1;
                    heroesHealth[i] += medicHealed;
                    System.out.println("Medic healed " + heroesAttackType[i] + " for " + medicHealed + " health!!!");
                    break;
                }
            }
        }

        if (witcherIndex != -1 && heroesHealth[witcherIndex] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] <= 0) {
                    heroesHealth[i] = heroesHealth[witcherIndex];
                    heroesHealth[witcherIndex] = 0;
                    System.out.println("Witcher given his life " + heroesAttackType[i]);
                    break;
                }
            }
        }

    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }

        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }


    public static void round() {
        roundNumber++;
        chooseBossDefence();
        if (!bossDefence.equals("Thor")) {
            bossAttacks();
        }
        setHeroesHealth();
        heroesAttacks();
        System.out.println("----------------------------------");
        showStatics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex;
        do {
            randomIndex = random.nextInt(heroesAttackType.length);
        } while (heroesAttackType[randomIndex].equals("Thor"));
        bossDefence = heroesAttackType[randomIndex];
    }

    ;

    public static void heroesAttacks() {
        Random random = new Random();
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefence == heroesAttackType[i]) {
                    int coeff = random.nextInt(9) + 2;
                    damage = damage * coeff;
                    System.out.println("Critical Damage " + heroesAttackType[i] + " " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= damage;
                }
                if (heroesAttackType[i].equals("Thor") && random.nextBoolean()) {
                    System.out.println("Thor stuned is BOSS!!!");
                }
            }
        }
    }

    public static void bossAttacks() {
        int luckyIndex = -1;
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesAttackType[i].equals("Lucky")) {
                luckyIndex = i;
                break;
            }
        }

        int golemIndex = -1;
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesAttackType[i].equals("Golem")) {
                golemIndex = i;
                break;
            }
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                Random random = new Random();
                boolean isDodged = random.nextInt(100) < 20;
                if (i != luckyIndex || !isDodged) {
                    if (golemIndex != -1 && heroesHealth[golemIndex] > 0 && i != golemIndex) {
                        int absorbDamage = bossDamage / 5;
                        heroesHealth[golemIndex] -= absorbDamage;
                        System.out.println("Golem absorbed " + absorbDamage + " damage for " + heroesAttackType[i]);
                    }
                    if (heroesHealth[i] - bossDamage < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] -= bossDamage;
                    }
                } else {
                    System.out.println(heroesAttackType[i] + " is dodged BOSS attack!!!");
                }
            }
        }
    }

    public static void showStatics() {
        System.out.println("ROUND NUMBER: " + roundNumber + "-------------");
        System.out.println("Boss Health: " + bossHealth + ". Damage: " + bossDamage + ". Defence: " + (bossDefence == null ? "None" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " Health: " + heroesHealth[i] + ". Damage: " + heroesDamage[i]);
        }
        ;
    }
}