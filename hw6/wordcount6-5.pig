nodes1 = LOAD './web-Google.txt' USING PigStorage('\t') AS (left1:chararray, right1:chararray);
nodes2 = LOAD './web-Google.txt' USING PigStorage('\t') AS (left2:chararray, right2:chararray);
joining = JOIN nodes1 BY right1 , nodes2 BY left2;
grouped = GROUP joining BY left1;
count = FOREACH grouped GENERATE group, COUNT(joining);
store grouped into './6-5';
