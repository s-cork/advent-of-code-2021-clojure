(ns aoc.2021.day1
  (:require [aoc.file-utils :as file-utils]
            [aoc.string-utils :refer [str->ints]]))

;;(def input (str->ints "199\n200\n208\n210\n200\n207\n240\n269\n260\n263\n")) ;; task1 7, task2 5
(def input (file-utils/read-ints "2021/day1.txt"))

(defn solver [offset nums]
  (->> (map > (drop offset nums) nums)
       (filter true?)
       count))

(defn task1 [] (solver 1 input))
(defn task2 [] (solver 3 input))
;; oh my, windows are useless since the sums cancel each other out
;; we only need to consider the diff between the first and last
