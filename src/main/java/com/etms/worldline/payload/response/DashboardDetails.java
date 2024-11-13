package com.etms.worldline.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class DashboardDetails {
    private int totalEmployees;
    private int totalProjects;
    private int totalManagers;
    private int totalSkills;
    private List<Object[]> topSkillsUtilisedByUser;
    private List<Object[]> top4skillslackedbyUser;
    private List<Object[]> skillsfromMosttoLeast;
    private List<Userdetails> recentlyAddedUsers;
    private List<Projectdetails> recentlyAddedProjects;
    private List<Object[]> totalmembersinEachProject;
}
