import java.util.Random;

public class Main {
    public static int bossHealth = 1100;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {290, 270, 250, 230, 550, 215 };
    public static int[] heroesDamage = {35, 25, 15, 0, 8, 12};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky" };
    public static int roundNumber = 0;

    public static void main(String[] args) {
        showStatics();
        while (!isGameOver()) {
            round();
        }
    }

    public static void setHeroesHealth() {
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
        bossAttacks();
        setHeroesHealth();
        heroesAttacks();
        System.out.println("----------------------------------");
        showStatics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    ;

    public static void heroesAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2;
                    damage = damage * coeff;
                    System.out.println("Critical Damage " + heroesAttackType[i] + " " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= damage;
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