(ns aoc.2020.day1
  (:require [clojure.string :as str]
            [aoc.string-utils :refer [str->ints]]
            [aoc.file-utils :as file-utils]
            [aoc.debug-utils :refer [-log->]]))

(def input (str->ints "1721\n979\n366\n299\n675\n1456"))
(def input (file-utils/read-ints "2020/day1.txt"))

(defn find-a-b [num-set]
  (keep (fn [a]
          (when-let [b (num-set (- 2020 a))]
            [a b])) num-set))

(defn find-a-b-c [num-set]
  (for [a num-set b num-set
        :when (not= a b)
        :let [c (- 2020 a b)]
        :when (num-set c)]
    [a b c]))

(defn solve [finder]
  (->> input
       (apply hash-set)
       finder
       first
       -log->
       (apply *)))


(comment
  (solve find-a-b)
  (solve find-a-b-c)
  ;
  )