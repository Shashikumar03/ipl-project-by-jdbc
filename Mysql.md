# IplProjectByMysql
  **1.Number of matches played per year of all the year in IPl**.
```
SELECT season,
       Count(season) AS total_match_played
FROM   matches
GROUP  BY season;
```
 **2.Number of matches won of all teams over all the year in ipl.**
```
SELECT winner,
       Count(*) AS Matches_Won
FROM   matches
GROUP  BY winner
ORDER  BY matches_won;
```

**3.For the year 2016 get the extra runs conceded over all the year of IPl.**
```
SELECT bowling_team,
       Sum(extra_runs) AS extraRunsGiven
FROM   deliveries
WHERE  match_id IN (SELECT id
                    FROM   matches
                    WHERE  season = 2016)
GROUP  BY bowling_team
ORDER  BY extrarunsgiven;
```

**4.For the year 2015 get the top economical bowlers.**
```
SELECT bowler,
       ( 6 * ( Sum(total_runs) - Sum(legbye_runs) - Sum(bye_runs) ) ) / Count(
       bowler)
       AS bowler_economy
FROM   deliveries
WHERE  match_id IN(SELECT id
                   FROM   matches
                   WHERE  season = 2015)
GROUP  BY bowler
ORDER  BY bowler_economy; 
```

**5. My scenario -Most successfull team of IPl.**
```
SELECT 'Mumbai Indians' AS Team,
       Count(CASE
               WHEN winner = 'Mumbai Indians' THEN 1
               ELSE NULL
             END),
       Sum(CASE
             WHEN team1 = 'Mumbai Indians'
                   OR team2 = 'Mumbai Indians' THEN 1
             ELSE NULL
           END)
FROM   matches; 
```
