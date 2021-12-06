(ns aoc.2020.day1
  (:require [clojure.string :as str]
            [aoc.string-utils :refer [str->ints]]
            [aoc.file-utils :as file-utils]
            [aoc.debug-utils :as debug]))

(def input (str->ints "1721\n979\n366\n299\n675\n1456"))
(def input (file-utils/read-ints "2020/day1.txt"))

(defn find-result [num-set]
  (keep (fn [n]
          (when (num-set (- 2020 n)) n)) num-set))

(defn do-multiply [n]
  (* (- 2020 n) n))

(comment
  (->> input
       (apply hash-set)
       find-result
       first
       do-multiply))