(ns data-munging-kata.core
	(:require [clojure.string :as str]
		[clojure.java.io :as io])
  (:gen-class))

(defn read-file [filepath]
	(str/split (slurp filepath) #"\n"))

(defn parse-weather-line [line]
	(zipmap [:day :min :max]
		(take 3 (next (str/split line #"(\*\s+)|(\s+)")))))

(defn filtermap-weather-data [rawdata]
	(filter #(and (not= 0 (count %)) (number? (read-string (% :day))))
		(map parse-weather-line rawdata)))

(defn filter-for-closest-temperature [data]
	((first
		(sort-by :dist
		(map #(into % {:dist (- (read-string (% :min)) (read-string (% :max)))})
			data)))
	:day))

(defn extract-values [file]
		(let [data (filter #(= 9 (count %)) (map (fn [line] (next (str/split line #"(\-+)|(\s+\-\s+)|(\.\s+)|(\s+)"))) file))]
	    	(println
			     ((first (sort-by :dist
			     		(map #(into % {:dist (java.lang.Math/abs (- (read-string (% :goalsagainst)) (read-string (% :goalsfor))))})
			     			 (map (fn [line] 
			     			 	(into (zipmap [:rank :team] (take 2 line)) 
			     			 		{:goalsfor (first (drop 6 line))
			     			 		:goalsagainst (first (drop 7 line))})
			     			 	) data))) ) :team))))

(defn -main [& args] (extract-values (read-file "/Users/fp/code/data-munging-kata/football.dat")))

; (defn extract-values []
; 	(with-open [rdr (io/reader "/Users/fp/code/data-munging-kata/football.dat")]
; 		(let [data
; 			(filter #(= 9 (count %)) (map (fn [line] (next (str/split line #"(\-+)|(\s+\-\s+)|(\.\s+)|(\s+)"))) (line-seq rdr)))]
;     	(println
; 		     ((first (sort-by :dist
; 		     		(map #(into % {:dist (java.lang.Math/abs (- (read-string (% :goalsagainst)) (read-string (% :goalsfor))))})
; 		     			 (map (fn [line] 
; 		     			 	(into (zipmap [:rank :team] (take 2 line)) 
; 		     			 		{:goalsfor (first (drop 6 line))
; 		     			 		:goalsagainst (first (drop 7 line))})
; 		     			 	) data))) ) :team)))))


; (defn extract-values []
; 	(with-open [rdr (io/reader "/Users/fp/code/data-munging-kata/weather.dat")]
;     	(println
; 		     ((first (sort-by :dist
; 		     		(map #(into % {:dist (- (read-string (% :min)) (read-string (% :max)))})
; 		     			 (filter #(and (not= 0 (count %)) (number? (read-string (% :day))))
; 		     			 (map (fn [line] (zipmap [:day :min :max] (take 3 (next (str/split line #"(\*\s+)|(\s+)")))))
; 		     				(line-seq rdr)))))) :day))))
