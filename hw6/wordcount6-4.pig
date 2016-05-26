lines = LOAD './web-Google.txt' USING PigStorage ('\t') as (w:chararray);
words = FOREACH lines GENERATE FLATTEN(TOKENIZE(w)) as word;
grouped = GROUP words BY word;
wordcount = FOREACH grouped GENERATE group, COUNT(words);
store wordcount into './6-4';
