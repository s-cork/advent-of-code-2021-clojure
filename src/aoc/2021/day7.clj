(ns aoc.2021.day7
  (:require [aoc.string-utils :refer [str->ints]]
            [aoc.file-utils :as file-utils]
            [aoc.math-utils :refer [abs, tri-num]]))

;;(def positions (str->ints "16,1,2,0,4,2,7,1,2,14"))         ;; 37 168
(def positions (file-utils/read-ints "2021/day7.txt"))

(defn distance [start finish]
  (abs (- start finish)))

(defn reduce-to-fuel [diff-fn positions]
  (reduce (fn [fuel i]
            (->> (map (partial diff-fn i) positions)
                 (apply +)
                 (assoc fuel i)))
          {}
          (range (first positions) (last positions))))

(defn solve [diff-fn]
  (->> positions
       sort
       (reduce-to-fuel diff-fn)
       (sort-by val)
       first
       (zipmap [:pos :fuel])))

(defn task1 [] (solve distance))
(defn task2 [] (solve (comp tri-num distance)))
