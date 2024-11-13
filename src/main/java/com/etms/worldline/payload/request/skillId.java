package com.etms.worldline.payload.request;

import com.etms.worldline.model.Quiz;

import java.util.List;
import java.util.Set;

public class skillId {
    private String emp_group;
    private Set<Long> skillIds;
    private String[] skill;

    public String getSkills1() {
        return skills1;
    }

    public void setSkills(String skills) {
        this.skills1 = skills;
    }

    private String skills1;



    private List<Quiz> quizzes;
    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public String[] getSkill() {
        return skill;
    }

    public void setSkill(String[] skill) {
        this.skill = skill;
    }

    public String getEmp_group() {
        return emp_group;
    }

    public void setEmp_group(String emp_group) {
        this.emp_group = emp_group;
    }

    public Set<Long> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(Set<Long> skillIds) {
        this.skillIds = skillIds;
    }
}
