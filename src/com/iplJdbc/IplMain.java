package com.iplJdbc;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class IplMain {

    public static final String ID = "id";
    public static final String SEASON = "season";
    public static final String CITY = "city";
    public static final String TEAM_1 = "team1";
    public static final String TEAM_2 = "team2";
    public static final String TOSS_WINNER = "toss_winner";
    public static final String WINNER = "winner";
    public static final String WIN_BY_RUNS = "win_by_runs";
    public static final String WIN_BY_WICKETS = "win_by_wickets";
    public static final String PLAYER_OF_MATCH = "player_of_match";
    public static final String VENUE = "venue";
    public static final String TOSS_DECISION = "toss_decision";
    public static final String RESULT = "result";
    public static final String DL_APPLIED = "dl_applied";

    public static final String MATCH_ID = "match_id";
    public static final String INNING = "inning";
    public static final String BATTING_TEAM = "batting_team";
    public static final String BOWLING_TEAM = "bowling_team";
    public static final String OVER = "over";
    public static final String BALL = "ball";
    public static final String BATSMAN = "batsman";
    public static final String NON_STRIKER = "non_striker";
    public static final String BOWLER = "bowler";
    public static final String WIDE_RUNS = "wide_runs";
    public static final String BYE_RUNS = "bye_runs";
    public static final String LEG_BYE_RUNS = "legbye_runs";
    public static final String BATSMAN_RUNS = "batsman_runs";
    public static final String EXTRA_RUNS = "extra_runs";
    public static final String TOTAL_RUNS = "total_runs";

    public static void main(String[] args) {

        List<Match> matchList = getMatchDataFromJDBC();
        List<Delivery> deliveryList = getDeliveriesDataFromJDBC();
        findNumberOfMatchesPlayedPerYear(matchList);
        findNumberOfMatchesWonPerTeamInAllYear(matchList);
        findExtraRunsConcertedTeamIn2016(matchList, deliveryList);
        findMostEconomicalBowlerIn2015(matchList, deliveryList);
        findMostSuccessfulTeamInIpl(matchList);
    }

    public static List<Match> getMatchDataFromJDBC() {
        ArrayList<Match> matchArrayList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql:///IplProject", "root", "Shashi@123");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from matches");

            while (resultSet.next()) {
                Match match = new Match();
                match.setMatchId(resultSet.getString(ID));
                match.setSeason(resultSet.getString(SEASON));
                match.setCity(resultSet.getString(CITY));
                match.setTeam1(resultSet.getString(TEAM_1));
                match.setTeam2(resultSet.getString(TEAM_2));
                match.setTossWinner(resultSet.getString(TOSS_WINNER));
                match.setWinner(resultSet.getString(WINNER));
                match.setWinByRuns(resultSet.getString(WIN_BY_RUNS));
                match.setWinByWickets(resultSet.getString(WIN_BY_WICKETS));
                match.setPlayerOfMatch(resultSet.getString(PLAYER_OF_MATCH));
                match.setVenue(resultSet.getString(VENUE));
                match.setTossDecision(resultSet.getString(TOSS_DECISION));
                match.setResult(resultSet.getString(RESULT));
                match.setDlApplied(resultSet.getString(DL_APPLIED));

                matchArrayList.add(match);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error : " + e);

        }
        return matchArrayList;
    }

    public static List<Delivery> getDeliveriesDataFromJDBC() {
        ArrayList<Delivery> deliveryArrayList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql:///IplProject", "root", "Shashi@123");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from deliveries");

            while (resultSet.next()) {
                Delivery delivery = new Delivery();
                delivery.setDelMatchId(resultSet.getString(MATCH_ID));
                delivery.setInnings(resultSet.getString(INNING));
                delivery.setBattingTeams(resultSet.getString(BATTING_TEAM));
                delivery.setBowlingTeams(resultSet.getString(BOWLING_TEAM));
                delivery.setOver(resultSet.getString(OVER));
                delivery.setBall(resultSet.getString(BALL));
                delivery.setBatsman(resultSet.getString(BATSMAN));
                delivery.setNonStriker(resultSet.getString(NON_STRIKER));
                delivery.setBowler(resultSet.getString(BOWLER));
                delivery.setWideRuns(resultSet.getString(WIDE_RUNS));
                delivery.setByeRuns(resultSet.getString(BYE_RUNS));
                delivery.setLegByeRuns(resultSet.getString(LEG_BYE_RUNS));
                delivery.setBatsManRun(resultSet.getString(BATSMAN_RUNS));
                delivery.setExtraRuns(resultSet.getString(EXTRA_RUNS));
                delivery.setTotalRuns(resultSet.getString(TOTAL_RUNS));

                deliveryArrayList.add(delivery);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
        return deliveryArrayList;
    }

    //question1
    public static void findNumberOfMatchesPlayedPerYear(List<Match> matchList) {
        Map<String, Integer> totalMatchPlayed = new HashMap<>();
        for (Match match : matchList) {
            if (totalMatchPlayed.containsKey(match.getSeason())) {
                int count = totalMatchPlayed.get(match.getSeason());
                totalMatchPlayed.put(match.getSeason(), count + 1);
            } else {
                totalMatchPlayed.put(match.getSeason(), 1);
            }
        }
        System.out.println("1. total number of match played per year");
        System.out.println(totalMatchPlayed);
    }

    //question2
    public static void findNumberOfMatchesWonPerTeamInAllYear(List<Match> matchList) {
        Map<String, Integer> MatchesWonHashMap = new HashMap<>();

        for (Match match : matchList) {
            if (!match.getWinner().isEmpty()) {
                if (MatchesWonHashMap.containsKey(match.getWinner())) {
                    int count = MatchesWonHashMap.get(match.getWinner());
                    MatchesWonHashMap.put(match.getWinner(), count + 1);
                } else {
                    MatchesWonHashMap.put(match.getWinner(), 1);
                }
            }
        }
        System.out.println("2. total number of matches won by each team over all the year");
        System.out.println(MatchesWonHashMap);
    }

    //question3
    public static void findExtraRunsConcertedTeamIn2016(List<Match> matchList, List<Delivery> deliveryList) {
        HashMap<String, Integer> extraRunHm = new HashMap<>();

        ArrayList<String> sameIdMatch = new ArrayList<>();
        for (Match match : matchList) {
            if (match.getSeason().equals("2016")) {
                sameIdMatch.add(match.getMatchId());
            }
        }

        for (Delivery delivery : deliveryList) {
            if (sameIdMatch.contains(delivery.getDelMatchId())) {
                if (extraRunHm.containsKey(delivery.getBowlingTeams())) {
                    int alreadyGivenRuns = extraRunHm.get(delivery.getBowlingTeams());
                    int extraRuns = Integer.parseInt(delivery.getExtraRuns());
                    extraRunHm.put(delivery.getBowlingTeams(), alreadyGivenRuns + extraRuns);
                } else {
                    int extraRuns = Integer.parseInt(delivery.getExtraRuns());
                    extraRunHm.put(delivery.getBowlingTeams(), extraRuns);
                }
            }
        }
        System.out.println("3. Extra runs Conceded in team in 2016");
        System.out.println(extraRunHm);
    }

    //Question4
    public static void findMostEconomicalBowlerIn2015(List<Match> matchArrayList, List<Delivery> deliveryArrayList) {
        HashMap<String, Integer> totalRunsHm = new HashMap<>();
        HashMap<String, Integer> totalBallHm = new HashMap<>();
        TreeMap<Double, String> sortedEconomyMap = new TreeMap<>();
        ArrayList<String> sameMatchId = new ArrayList<>();

        for (Match match : matchArrayList) {
            if (match.getSeason().equals("2015")) {
                sameMatchId.add(match.getMatchId());
            }
        }
        for (Delivery delivery : deliveryArrayList) {
            if (sameMatchId.contains(delivery.getDelMatchId())) {
                if (totalRunsHm.containsKey(delivery.getBowler())) {
                    int count = totalRunsHm.get(delivery.getBowler());
                    totalRunsHm.put(delivery.getBowler(), count + (Integer.parseInt(delivery.getTotalRuns())));
                    totalBallHm.put(delivery.getBowler(), totalBallHm.get(delivery.getBowler()) + 1);
                } else {
                    totalRunsHm.put(delivery.getBowler(), Integer.parseInt(delivery.getTotalRuns()));
                    totalBallHm.put(delivery.getBowler(), 1);
                }
            }
        }

        for (String str : totalRunsHm.keySet()) {
            if (totalBallHm.containsKey(str)) {
                int runs = totalRunsHm.get(str);
                int totalBall = totalBallHm.get(str);
                double economy = (double) (runs * 6) / totalBall;
                sortedEconomyMap.put(economy, str);
            }
        }
        System.out.println("4. Most economical bowler in 2015");
        int index = 0;
        for (Double economy : sortedEconomyMap.keySet()) {
            if (index < 10) {
                System.out.println(sortedEconomyMap.get(economy) + " " + economy);
                index = index + 1;
            }
        }

    }

    //question5
    public static void findMostSuccessfulTeamInIpl(List<Match> matchList) {
        HashMap<String, Integer> mostSuccessTeam = new HashMap<>();
        for (Match match : matchList) {
            if (mostSuccessTeam.containsKey(match.getWinner())) {
                int count = mostSuccessTeam.get(match.getWinner());
                mostSuccessTeam.put(match.getWinner(), count + 1);
            } else {
                mostSuccessTeam.put(match.getWinner(), 1);

            }
        }

        int total = 0;
        for (Match match : matchList) {
            if (match.getTeam1().equals("Mumbai Indians") || match.getTeam2().equals("Mumbai Indians")) {
                total = total + 1;
            }
        }

        int MostWon = Integer.MIN_VALUE;
        String teamName = "";
        for (String key : mostSuccessTeam.keySet()) {
            if (mostSuccessTeam.get(key) > MostWon) {
                MostWon = mostSuccessTeam.get(key);
                teamName = key;
            }
        }
        System.out.println("5. My scenario ");
        System.out.println("Most successful Team Of IPL is " + teamName + " won " + MostWon + "  matches from "
                + total + " total matches played");
    }


}
