(ns data-munging-kata.core-test
  (:require [clojure.test :refer :all]
            [data-munging-kata.core :refer :all]))

; baseball tests
(deftest add-win-difference-test
  (testing "Calculation of the win-loss difference"
    (is
        (=
            (add-win-difference {:team "Testing Munchkins" :wins "2" :losses "0"})
            {:team "Testing Munchkins" :wins "2" :losses "0" :diff 2}))))

(deftest add-win-difference-invalid-input-test
  (testing "Calculation of the win-loss difference with invalid input"
    (is
        (nil?
            (add-win-difference {:losses "0"})))))
