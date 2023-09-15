package com.darjow.framework.utility.skills;

public class SkillExperience {

    public static int getExperienceForLevel(int current, int needed) {
        int points = 0;
        int output = 0;
        for (int lvl = current; lvl <= needed; lvl++) {
            points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
            if (lvl >= needed) {
                return output;
            }
            output = (int)Math.floor(points / 4);
        }
        return 0;
    }
}
