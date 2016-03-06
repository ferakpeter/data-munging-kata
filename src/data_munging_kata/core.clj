(ns data-munging-kata.core
	(:require [clojure.string :as str]
		[clojure.java.io :as io])
  (:gen-class))

; shared logic
(defn read-file [filepath]
	(str/split (slurp filepath) #"\n"))

(defn find-smallest-difference [file extract parse-line calculate-difference result-item]
     ((first
     	(sort-by :diff
     		(map calculate-difference
     			(map parse-line
     				(extract file)))))
     result-item))

(defn calculate-difference [data value1 value2]
	(into data
		{:diff
			(java.lang.Math/abs
				(-
					(read-string (data value2))
					(read-string (data value1))))}))

; weather
(defn parse-weather-line [rawcollumns]
	(zipmap [:day :min :max]
		(take 3 rawcollumns)))

(defn extract-weather-data [rawdata]
	(filter #(and (not= 0 (count %)) (number? (read-string (first %))))
		(map #(next (str/split % #"(\*\s+)|(\s+)"))
		rawdata)))

(defn add-temperature-difference [dayminmax]
	(into dayminmax {:diff (- (read-string (dayminmax :min)) (read-string (dayminmax :max)))}))

; main weather function
; (defn -main [& args]
; 	(println
; 		(find-smallest-difference
; 			(read-file "/Users/fp/code/data-munging-kata/weather.dat")
; 			extract-weather-data
; 			parse-weather-line
; 			add-temperature-difference
; 			:day
; 				)))

; football
(defn parse-football-line [rawcollumns]
	(into
		(zipmap
			[:rank :team]
			(take 2 rawcollumns))
		{:goalsfor
			(first (drop 6 rawcollumns))
		:goalsagainst
		(first (drop 7 rawcollumns))}))

(defn extract-football-data [rawdata]
	(filter
		#(= 9 (count %))
		(map
			#(next (str/split % #"(\-+)|(\s+\-\s+)|(\.\s+)|(\s+)"))
		 	rawdata)))

(defn add-goals-difference [data]
	(calculate-difference data :goalsagainst :goalsfor))

; main football function
; (defn -main [& args]
; 	(println
; 		(find-smallest-difference
; 			(read-file "/Users/fp/code/data-munging-kata/football.dat")
; 			extract-football-data
; 			parse-football-line
; 			add-goals-difference
; 			:team)))

; baseball
(defn extract-baseball-data [rawdata]
	(filter
		#(< 11 (count %))
		(map #(str/split % #"(\s+)")
		 	rawdata)))

(defn parse-baseball-line [rawcollumns]
	(into
		{:team
			(str/join " " (take (- (count rawcollumns) 11) rawcollumns))}
		(zipmap [:wins :losses]
			(take 2 (drop (- (count rawcollumns) 11) rawcollumns)))))

(defn add-win-difference [teamwinslosses]
	(calculate-difference teamwinslosses :wins :losses))

; main baseball function
(defn -main [& args]
	(println
		(find-smallest-difference
			(read-file "/Users/fp/code/data-munging-kata/baseball.dat")
			extract-baseball-data
			parse-baseball-line
			add-win-difference
			:team)))

; football minimal solution
; (defn find-smallest-difference []
; 	(with-open [rdr (io/reader "/Users/fp/code/data-munging-kata/football.dat")]
; 		(let [data
; 			(filter #(= 9 (count %)) (map (fn [line] (next (str/split line #"(\-+)|(\s+\-\s+)|(\.\s+)|(\s+)"))) (line-seq rdr)))]
;     	(println
; 		     ((first (sort-by :diff
; 		     		(map #(into % {:diff (java.lang.Math/abs (- (read-string (% :goalsagainst)) (read-string (% :goalsfor))))})
; 		     			 (map (fn [line]
; 		     			 	(into (zipmap [:rank :team] (take 2 line))
; 		     			 		{:goalsfor (first (drop 6 line))
; 		     			 		:goalsagainst (first (drop 7 line))})
; 		     			 	) data))) ) :team)))))


; weather minimal solution
; (defn find-smallest-difference []
; 	(with-open [rdr (io/reader "/Users/fp/code/data-munging-kata/weather.dat")]
;     	(println
; 		     ((first (sort-by :diff
; 		     		(map #(into % {:diff (- (read-string (% :min)) (read-string (% :max)))})
; 		     			 (filter #(and (not= 0 (count %)) (number? (read-string (% :day))))
; 		     			 (map (fn [line] (zipmap [:day :min :max] (take 3 (next (str/split line #"(\*\s+)|(\s+)")))))
; 		     				(line-seq rdr)))))) :day))))
