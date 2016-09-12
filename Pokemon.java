package gui;

import java.util.ArrayList;
import java.util.Formatter;

public class Pokemon {

    private String name;
    private int id;
    private ArrayList<Type> types;
    private boolean fullyEvo;
    private boolean megaEvo;
    private int hp;
    private int atk;
    private int def;
    private int spa;
    private int spd;
    private int spe;
    private int tot;
    private int avg;
    private String serebiiURL;
    private String smogonURL;

    private int generation;


    public enum Type {
        BUG,
        DARK,
        DRAGON,
        ELECTRIC,
        FAIRY,
        FIGHTING,
        FIRE,
        FLYING,
        GHOST,
        GRASS,
        GROUND,
        ICE,
        NORMAL,
        POISON,
        PSYCHIC,
        ROCK,
        STEEL,
        WATER

    }
    
    public Pokemon(String name, int id, ArrayList<Type> types, boolean fE,
                   boolean mE, int hp, int atk, int def, int spa, int spd,
                   int spe, int tot, int avg) {
        this.name = name;
        this.id = id;
        this.types = types;
        this.fullyEvo = fE;
        this.megaEvo = mE;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.spa = spa;
        this.spd = spd;
        this.spe = spe;
        this.tot = tot;
        this.avg = avg;
        if (id < 10) {
            this.serebiiURL = "http://www.serebii.net/pokedex-xy/00" + id + ".shtml";
        } else if (id < 100) {
            this.serebiiURL = "http://www.serebii.net/pokedex-xy/0" + id + ".shtml";
        } else {
            this.serebiiURL = "http://www.serebii.net/pokedex-xy/" + id + ".shtml";
        }
        this.smogonURL = "http://www.smogon.com/dex/xy/pokemon/" + name.toLowerCase() + "/";

        if (id <= 151) {
            generation = 1;
        } else if (id <= 251) {
            generation = 2;
        } else if (id <= 386) {
            generation = 3;
        } else if (id <= 493) {
            generation = 4;
        } else if (id <= 649) {
            generation = 5;
        } else {
            generation = 6;
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public boolean getFullyEvolved() {
        return fullyEvo;
    }

    public boolean getMegaEvolved() {
        return megaEvo;
    }

    public int getHP() {
        return hp;
    }
    public int getATK() {
        return atk;
    }
    public int getDEF() {
        return def;
    }
    public int getSPA() {
        return spa;
    }
    public int getSPD() {
        return spd;
    }
    public int getSPE() {
        return spe;
    }
    public int getTOT() {
        return tot;
    }
    public int getAVG() {
        return avg;
    }

    public String getSerebii() {
        return serebiiURL;
    }
    public String getSmogon() {
        return smogonURL;
    }

    public String getGeneration() {
        return "" + generation;
    }



    public static ArrayList<Type> getAllTypes() {
        ArrayList<Type> t = new ArrayList<>();
        t.add(Type.BUG);
        t.add(Type.DARK);
        t.add(Type.DRAGON);
        t.add(Type.ELECTRIC);
        t.add(Type.FAIRY);
        t.add(Type.FIGHTING);
        t.add(Type.FIRE);
        t.add(Type.FLYING);
        t.add(Type.GHOST);
        t.add(Type.GRASS);
        t.add(Type.GROUND);
        t.add(Type.ICE);
        t.add(Type.NORMAL);
        t.add(Type.POISON);
        t.add(Type.PSYCHIC);
        t.add(Type.ROCK);
        t.add(Type.STEEL);
        t.add(Type.WATER);
        return t;
    }

    public static String typeToString(Type t) {
        switch (t) {
            case BUG:
                return "Bug";
            case DARK:
                return "Dark";
            case DRAGON:
                return "Dragon";
            case ELECTRIC:
                return "Electric";
            case FAIRY:
                return "Fairy";
            case FIGHTING:
                return "Fighting";
            case FIRE:
                return "Fire";
            case FLYING:
                return "Flying";
            case GHOST:
                return "Ghost";
            case GRASS:
                return "Grass";
            case GROUND:
                return "Ground";
            case ICE:
                return "Ice";
            case NORMAL:
                return "Normal";
            case POISON:
                return "Poison";
            case PSYCHIC:
                return "Psychic";
            case ROCK:
                return "Rock";
            case STEEL:
                return "Steel";
            case WATER:
                return "Water";
        }
        return null;

    }

    public static Type stringToType(String s) {
        switch (s) {
            case "":
                return null;
            case "Bug":
                return Type.BUG;
            case "Dark":
                return Type.DARK;
            case "Dragon":
                return Type.DRAGON;
            case "Electric":
                return Type.ELECTRIC;
            case "Fairy":
                return Type.FAIRY;
            case "Fighting":
                return Type.FIGHTING;
            case "Fire":
                return Type.FIRE;
            case "Flying":
                return Type.FLYING;
            case "Ghost":
                return Type.GHOST;
            case "Grass":
                return Type.GRASS;
            case "Ground":
                return Type.GROUND;
            case "Ice":
                return Type.ICE;
            case "Normal":
                return Type.NORMAL;
            case "Poison":
                return Type.POISON;
            case "Psychic":
                return Type.PSYCHIC;
            case "Rock":
                return Type.ROCK;
            case "Steel":
                return Type.STEEL;
            case "Water":
                return Type.WATER;
        }
        return null;
    }

    public static boolean stringToBool(String s) {
        if (s.equals("TRUE")) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        ArrayList<Type> types = getTypes();
        String type1 = typeToString(types.get(0));

        String ret = "";
        Formatter fmt = new Formatter();

        if (types.size() == 1) {
            ret = fmt.format("%-5s\n %20s", getId() + ": " + getName(), "Type: " + type1 + ".").toString();

        } else {
            String type2 = typeToString(types.get(1));
            ret = fmt.format("%-5s\n %30s", getId() + ": " + getName(), "Types: " + type1 + ", " + type2 + ".").toString();
        }

        return ret;
    }
}