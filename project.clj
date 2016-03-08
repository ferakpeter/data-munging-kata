(defproject data-munging-kata "0.1.0-SNAPSHOT"
  :description "Coding dojo exercise for data-munging kata."
  :url "http://codekata.com/kata/kata04-data-munging/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :main data-munging-kata.core 
  :aot [data-munging-kata.core]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
