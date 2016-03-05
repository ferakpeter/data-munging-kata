(ns data-munging-kata.core-test
  (:require [clojure.test :refer :all]
            [data-munging-kata.core :refer :all]))

(deftest add-value-test
  (testing "Add value to a tree structure"
    (is 
    	(= 
    		(data-munging-kata.core/<main-function "Sofia") 
    		"female"))))
