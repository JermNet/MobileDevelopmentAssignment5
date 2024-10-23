package com.codelab.basics;

public class DataModel {

    private long id;
    // Despite being either 0 or 1, legendary_col is an Integer since Google was dumb and doesn't have a getBoolean
    // method in their cursor class.
    private Integer dex_col, total_col, hp_col, attack_col, defense_col, specialAttack_col, specialDefense_col,
            speed_col, access_col, generation_col, legendary_col;
    private String name_col, type1_col, type2_col;

    public DataModel() {
        // Fun note: I made this the default since Bulbasaur is the default Pokemon used in the actual games!
        this.setId(0);
        this.setDexNumber(1);
        this.setPokemonName("Bulbasaur");
        this.setType1("Grass");
        this.setType2("Poison");
        this.setTotal(318);
        this.setHP(45);
        this.setAttack(49);
        this.setDefense(49);
        this.setSpecialAttack(65);
        this.setSpecialDefense(65);
        this.setSpeed(45);
        this.setGeneration(1);
        this.setLegendary(0);
        this.setAccess(0);
    }

    public DataModel(long id, Integer dexNumber, String pokemonName, String type1, String type2, Integer total,
                     Integer hp, Integer attack, Integer defense, Integer specialAttack, Integer specialDefense,
                     Integer speed, Integer generation, Integer legendary, Integer access) {
        this.setId(id);
        this.setDexNumber(dexNumber);
        this.setPokemonName(pokemonName);
        this.setType1(type1);
        this.setType2(type2);
        this.setTotal(total);
        this.setHP(hp);
        this.setAttack(attack);
        this.setDefense(defense);
        this.setSpecialAttack(specialAttack);
        this.setSpecialDefense(specialDefense);
        this.setSpeed(speed);
        this.setGeneration(generation);
        this.setLegendary(legendary);
        this.setAccess(access);
    }

    @Override
    public String toString() {
        // Once again, this is because Google is dumb
        boolean legendary;
        legendary = getLegendary() == 1;

        return "DataModel{" +
                "id=" + getId() +
                ", dexNumber=" + getDexNumber() +
                ", pokemonName='" + getPokemonName() + '\'' +
                ", dexNumber=" + getDexNumber() +
                ", type1='" + getType1() + '\'' +
                ", type2='" + getType2() + '\'' +
                ", total=" + getTotal() +
                ", hp=" + getHP() +
                ", attack=" + getAttack() +
                ", defense=" + getDefense() +
                ", specialAttack=" + getSpecialAttack() +
                ", specialDefense=" + getSpecialDefense() +
                ", speed=" + getSpeed() +
                ", generation=" + getGeneration() +
                ", legendary=" + legendary +
                ", access=" + getAccess();
    }

    // Fancier toString() method that returns values in a more human readable format, without some of the data. Two different
    // strings depending on whether the Pokemon has 2 types or not
    public String toFancyString() {
        if (!getType2().equalsIgnoreCase("NONE")) {
            return getPokemonName() + " is the " + getDexNumber() + " Pokemon. It is a " + getType1() + " and " + getType2() +
                    " type Pokemon with a base stat total of " + getTotal() + ". It has been accessed " + getAccess() + " time(s).";
        }
        return getPokemonName() + " is the " + getDexNumber() + " Pokemon. It is a " + getType1() +
                " type Pokemon with a base stat total of " + getTotal() + ". It has been accessed " + getAccess() + " time(s).";

    }

    // Numerous setters and getters, you know the drill
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getDexNumber() {
        return dex_col;
    }

    public void setDexNumber(Integer dexNumber) {
        this.dex_col = dexNumber;
    }

    public String getPokemonName() {
        return name_col;
    }

    public void setPokemonName(String pokemonName) {
        this.name_col = pokemonName;
    }

    public String getType1() {
        return type1_col;
    }

    public void setType1(String type1) {
        this.type1_col = type1;
    }

    public String getType2() {
        return type2_col;
    }

    public void setType2(String type2) {
        this.type2_col = type2;
    }

    public Integer getTotal() {
        return total_col;
    }

    public void setTotal(Integer total) {
        this.total_col = total;
    }

    public Integer getHP() {
        return hp_col;
    }

    public void setHP(Integer hp) {
        this.hp_col = hp;
    }

    public Integer getAttack() {
        return attack_col;
    }

    public void setAttack(Integer attack) {
        this.attack_col = attack;
    }

    public Integer getDefense() {
        return defense_col;
    }

    public void setDefense(Integer defense) {
        this.defense_col = defense;
    }

    public Integer getSpecialAttack() {
        return specialAttack_col;
    }

    public void setSpecialAttack(Integer specialAttack) {
        this.specialAttack_col = specialAttack;
    }

    public Integer getSpecialDefense() {
        return specialDefense_col;
    }

    public void setSpecialDefense(Integer specialDefense) {
        this.specialDefense_col = specialDefense;
    }

    public Integer getSpeed() {
        return speed_col;
    }

    public void setSpeed(Integer speed) {
        this.speed_col = speed;
    }

    public Integer getGeneration() {
        return generation_col;
    }

    public void setGeneration(Integer generation) {
        this.generation_col = generation;
    }

    public Integer getLegendary() {
        return legendary_col;
    }

    public void setLegendary(Integer legendary) {
        this.legendary_col = legendary;
    }

    public Integer getAccess() {
        return access_col;
    }

    public void setAccess(Integer access) {
        this.access_col = access;
    }
}
